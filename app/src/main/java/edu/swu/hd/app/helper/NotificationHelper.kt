package edu.swu.hd.app.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import edu.swu.hd.app.R


class NotificationHelper(
    _context: Context
) {
    private val context = _context
    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
    private var isPermitted = false
    private val defaultNotificationID = "normal"
    private val defaultNotificationName = "普通通知"
    private var notificationID = 1

    /**
     * 请求通知权限
     */
    private fun requireNotificationPermission() {
        if (!isPermitted) {
            val notificationPermissionGranted =
                NotificationManagerCompat.from(context).areNotificationsEnabled()

            if (!notificationPermissionGranted) {
                // 请求通知权限
                val intent: Intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                context.startActivity(intent)
            }
            if (NotificationManagerCompat.from(context).areNotificationsEnabled())
                isPermitted = true
        }
    }

    /**
     * 注册一个新的Channel用于通知
     *
     * @param channelID             channel的唯一标识符
     * @param channelName    channel的名称
     * @param channelImportance     channel的重要性，NotificationManager.IMPORTANCE_X
     */
    private fun registerChannel(
        channelID: String = defaultNotificationID,
        channelName: String = defaultNotificationName,
        channelImportance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelID, channelName, channelImportance)
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * 发送通知
     */
    fun launchNotification(
        contentTitle: String = "",
        contentText: String = "",
        smallIcon: Int = R.mipmap.ic_launcher,
        largeIcon: Bitmap? = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher),
        contentIntent: PendingIntent? = null,
        deleteIntent: PendingIntent? = null,
        autoCancel: Boolean = false,
    ) {
        requireNotificationPermission()
        registerChannel()
        val notification = NotificationCompat.Builder(context, defaultNotificationID)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(smallIcon)
            .setLargeIcon(largeIcon)
        if (contentIntent != null) notification.setContentIntent(contentIntent)
        if (deleteIntent != null) notification.setDeleteIntent(deleteIntent)
        if (autoCancel) notification.setAutoCancel(true)

        notificationManager.notify(notificationID++, notification.build())
    }
}