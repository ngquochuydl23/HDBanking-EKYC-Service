package com.socialv2.ewallet.backgroundServices;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;  // Sửa gói này
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.socialv2.ewallet.Application;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.Main1Activity;

public class FcmMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        RemoteMessage.Notification notification = message.getNotification();
        if (notification == null) {
            return;
        }
        String setTitle = notification.getTitle();
        String setMessage = notification.getBody();

        sendNotification(setTitle, setMessage);
    }

    private void sendNotification(String setTitle, String setMessage) {

        Intent intent = new Intent(this, Main1Activity.class);
        PendingIntent pendingIntent;

        // Kiểm tra phiên bản Android và sử dụng cờ tương ứng
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        // Sử dụng androidx.core.app.NotificationCompat
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, Application.CHANNEL_ID)
                .setContentTitle(setTitle)
                .setContentText(setMessage)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Thiết lập độ ưu tiên
                .setAutoCancel(true); // Đóng thông báo khi người dùng nhấn vào

        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Hiển thị thông báo
        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }
}
