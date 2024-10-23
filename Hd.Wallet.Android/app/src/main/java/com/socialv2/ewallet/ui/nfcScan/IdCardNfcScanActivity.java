package com.socialv2.ewallet.ui.nfcScan;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.permissions.Permissions;
import com.socialv2.ewallet.utils.IdCardUtils;
import com.socialv2.ewallet.utils.WindowUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class IdCardNfcScanActivity extends BaseActivity {

    private static final String TAG = IdCardNfcScanActivity.class.getName();

    private NfcAdapter mNFCAdapter;
    private Permissions appPermission;

    public IdCardNfcScanActivity() {
        super(R.layout.activity_id_card_nfc_scan);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNFCAdapter = NfcAdapter.getDefaultAdapter(this);
        initView();

        if (mNFCAdapter == null) {
            Log.i(TAG, "Thiết bị không hỗ trợ NFC");
        } else if (!mNFCAdapter.isEnabled()) {

            Log.i(TAG, "Vui lòng bật NFC trong cài đặt");
        }
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupScanNfc();
    }

    private void setupScanNfc() {
        Intent intent = new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        if (mNFCAdapter != null) {
            mNFCAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNFCAdapter != null) {
            mNFCAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.i(TAG, "onNewIntent()");
//
//        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//        Log.d(TAG, tag.toString());
//        if (tag != null) {
//            handleTag(tag);
//        }

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (rawMessages != null) {
                for (Parcelable message : rawMessages) {
                    NdefMessage ndefMessage = (NdefMessage) message;
                    processNdefMessage(ndefMessage);
                }
            } else {
                Log.d(TAG, "No NDEF messages found in the intent.");
                // Optionally handle raw tag data if needed
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                if (tag != null) {
                    handleTag(tag); // Handle raw tag if needed
                }
            }
        }
    }

    private void processNdefMessage(NdefMessage ndefMessage) {
        for (NdefRecord record : ndefMessage.getRecords()) {
            if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
                String payload = new String(record.getPayload());
                // Assuming the payload is the APDU command, you can process it
                processApduCommand(payload);
            }
        }
    }

    private void processApduCommand(String apdu) {
        // Implement your logic to handle the APDU command
        Log.d("APDU Command", "Received: " + apdu);

        // For example, you can send the command to a smart card reader
        // byte[] response = sendApduToCard(apdu);
    }



    private void handleTag(Tag tag) {
        IsoDep isoDep = IsoDep.get(tag);
        if (isoDep != null) {
            try {
                isoDep.connect();
                // Send command to the card and receive the response
                byte[] command = new byte[]{
                        (byte) 0x00, // CLA
                        (byte) 0x82, // INS
                        (byte) 0x00, // P1
                        (byte) 0x00, // P2
                        (byte) 0x07, // Lc
                        (byte) 0x4D, (byte) 0x79, (byte) 0x41, (byte) 0x70, (byte) 0x70, (byte) 0x00 // Application Name: "MyApp"
                };
                byte[] response = isoDep.transceive(command);
                // Process the response
                String responseString = bytesToHex(response);
                Log.d(TAG, "Response: " + responseString);
                isoDep.close();
            } catch (Exception e) {

                Log.e(TAG, "Error communicating with NFC tag");
                e.printStackTrace();
            }
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }
}