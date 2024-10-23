package com.socialv2.ewallet.utils;

import android.nfc.tech.IsoDep;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;

public class IdCardUtils {
    public static void readIdCardViaNFC(IsoDep isoDep) throws IOException {
        isoDep.connect();

        // Lệnh SELECT AID để chọn ứng dụng trên thẻ CCCD
        byte[] selectAID = new byte[] {
                (byte) 0x00, // CLA
                (byte) 0xA4, // INS (SELECT)
                (byte) 0x04, // P1 (select by AID)
                (byte) 0x00, // P2 (no specific selection)
                (byte) 0x0D, // Lc (length of AID)
                (byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x01, // AID (for ICAO Document)
                // Add any additional bytes if the AID is longer
        };

// Gửi lệnh SELECT AID
        byte[] selectResponse = isoDep.transceive(selectAID);

// Kiểm tra mã trạng thái
        int statusWord = (selectResponse[selectResponse.length - 2] & 0xFF) << 8 | (selectResponse[selectResponse.length - 1] & 0xFF);
        if (statusWord == 0x9000) {
            Log.d("NFC", "AID selected successfully for ICAO document");
            // Tiến hành đọc dữ liệu từ thẻ
        } else {
            Log.d("NFC", "Failed to select AID with status: " + String.format("0x%04X", statusWord));
        }

        isoDep.close();
    }
    private static void extractCCCDData(byte[] dataResponse) {
        if (dataResponse == null || dataResponse.length == 0) {
            Log.e("NFC", "Không có dữ liệu để xử lý.");
            return;
        }

        try {
            int index = 0;
            while (index < dataResponse.length) {
                // Đọc Tag (1 byte)
                byte tag = dataResponse[index++];

                // Đọc độ dài dữ liệu (1 byte)
                int length = dataResponse[index] & 0xFF;

                // Đọc giá trị (Value)
                byte[] value = new byte[length];
                System.arraycopy(dataResponse, index, value, 0, length);
                index += length;

                // Chuyển giá trị từ byte[] sang chuỗi UTF-8
                String data = new String(value, "UTF-8");

                // Hiển thị thông tin dựa trên Tag
                switch (tag) {
                    case 0x01:
                        Log.d("CCCD", "Họ tên: " + data);
                        break;
                    case 0x02:
                        Log.d("CCCD", "Ngày sinh: " + data);
                        break;
                    case 0x03:
                        Log.d("CCCD", "Số CCCD: " + data);
                        break;
                    default:
                        Log.d("CCCD", "Dữ liệu không xác định: " + data);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("NFC", "Lỗi khi xử lý dữ liệu từ thẻ.");
        }
    }
}
