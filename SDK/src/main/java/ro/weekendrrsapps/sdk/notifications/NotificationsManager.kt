package ro.weekendrrsapps.sdk.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import ro.weekendrrsapps.sdk.utils.DateTimeUtils
import timber.log.Timber

object NotificationsManager {

    private const val NOTIFICATION_MESSAGE_CHANNEL_ID = "Coach message"
    private const val INITIAL_START_HOUR_24_FORMAT = 19

    fun createNotification(ctx: Context, title: String, message: String, iconResID: Int, pendingIntent: PendingIntent) {
        Timber.i("Creating notification...")

        val builder = NotificationCompat.Builder(ctx, NOTIFICATION_MESSAGE_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(iconResID)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with (NotificationManagerCompat.from(ctx)) {
            Timber.i("Trying to show notification")
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }

    fun createNotificationChannel(ctx: Context, titleResID: Int, descriptionResID: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = ctx.getString(titleResID)
            val descriptionText = ctx.getString(descriptionResID)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_MESSAGE_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startRecurringNotifications(ctx: Context, workerRequest: WorkRequest, startHour: Int = INITIAL_START_HOUR_24_FORMAT) {
        var hoursDelay = DateTimeUtils.getCurrentHoursDifference(startHour)
        if (hoursDelay <= 0) hoursDelay = 1
        WorkManager.getInstance(ctx).enqueue(workerRequest)
        Timber.i("Started notification worker with delay:$hoursDelay")
    }

    fun resetNotificationWorker(ctx: Context, workerRequest: WorkRequest, tag: String, startHour: Int = INITIAL_START_HOUR_24_FORMAT) {
        Timber.i("Restarting notification worker")
        WorkManager.getInstance(ctx).cancelAllWorkByTag(tag)
        startRecurringNotifications(ctx, workerRequest, startHour)
    }

    data class NotificationMessage(val title: String, val message: String, val iconResID: Int, val pendingIntent: PendingIntent)



}