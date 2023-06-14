package edu.swu.xn.app.helper

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
import edu.swu.xn.app.R

class NotificationHelper(
    _context: Context
) {
    private val context = _context
    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
    private val defaultNotificationID = "normal"
    private val defaultNotificationName = "普通通知"
    private var notificationID = 1

    /**
     * 请求通知权限
     */
    fun requireNotificationPermission() {
        val notificationPermissionGranted =
            NotificationManagerCompat.from(context).areNotificationsEnabled()

        if (!notificationPermissionGranted) {
            // 请求通知权限
            val intent: Intent =
                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            context.startActivity(intent)
        }
    }

    /**
     * 注册一个新的Channel用于通知
     *
     * @param channelID             channel的唯一标识符
     * @param channelName    channel的名称
     * @param channelImportance     channel的重要性，NotificationManager.IMPORTANCE_X
     */
    fun registerChannel(
        channelID: String = defaultNotificationID,
        channelName: String = defaultNotificationName,
        channelImportance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ) {
        val channel = NotificationChannel(channelID, channelName, channelImportance)
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * 发送通知
     *
     * @param contentTitle 通知标题
     * @param contentText 通知内容文本
     * @param smallIcon 小图标
     * @param largeIcon 大图标
     * @param contentIntent 点击意图
     * @param deleteIntent 删除意图
     * @param autoCancel 点击自动取消
     */
    fun launchNotification(
        contentTitle: String = "",
        contentText: String = "",
        contentIntent: PendingIntent? = null,
        deleteIntent: PendingIntent? = null,
        autoCancel: Boolean = true,
        smallIcon: Int = R.drawable.icon,
        largeIcon: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.icon)
    ) {
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