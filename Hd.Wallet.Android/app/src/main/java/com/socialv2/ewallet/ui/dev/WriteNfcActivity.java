package com.socialv2.ewallet.ui.dev;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.OkDialog;
import com.socialv2.ewallet.utils.NfcUtils;

public class WriteNfcActivity extends AppCompatActivity {

    private static final String TAG = WriteNfcActivity.class.getName();


    private NfcAdapter mNFCAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_write_nfc);
        mNFCAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNFCAdapter == null) {
            Log.i(TAG, "Thiết bị không hỗ trợ NFC");
        } else if (!mNFCAdapter.isEnabled()) {

            Log.i(TAG, "Vui lòng bật NFC trong cài đặt");
        }

        OkDialog.makeDialog(this, "", "").show();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "alo");
        String json = "{ \"id_card\": { \"id\": \"052203016070\", \"full_name\": \"NGUYỄN TRƯƠNG THÀNH DANH\", \"date_of_birth\": \"08/09/2003\", \"sex\": \"Nam\", \"nationality\": \"Việt Nam\", \"place_of_origin\": \"Phước Lộc, Tuy Phước, Bình Định\", \"place_of_residence\": \"Ngọc Thạnh 2 Phước An, Tuy Phước, Bình Định\", \"date_of_expiry\": \"08/09/2028\" }, \"back-url\": \"/uploads/identity-cart/39b4bd2b9541481688f73ceaed621daa.jpg\", \"front-url\": \"/uploads/identity-cart/1b70df8c910640cabce99c880ea1bb61.jpg\", \"type\": \"CCCD\" }";
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        try {

            if (NfcUtils.writeIdCardToTag(tag, json)) {
                Log.d(TAG, "Write NFC Successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}