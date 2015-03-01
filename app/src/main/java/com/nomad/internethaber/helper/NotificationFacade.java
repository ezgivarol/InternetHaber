package com.nomad.internethaber.helper;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.nomad.internethaber.provider.NotificationIdProvider;

public final class NotificationFacade {
    private Context mContext;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    public NotificationFacade(Context context) {
        mContext = context;
        mBuilder = new NotificationCompat.Builder(context);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public NotificationCompat.Builder getBuilder() {
        return mBuilder;
    }

    public void show() {
        int notificationId = NotificationIdProvider.getNotificationId();
        mNotificationManager.notify(notificationId, mBuilder.build());
    }
}