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

    private var notificationChannelID = "default.app.channel.id"
    private const val INITIAL_START_HOUR_24_FORMAT = 19

    fun createNotification(ctx: Context, title: String, message: String, iconResID: Int, pendingIntent: PendingIntent) {
        Timber.i("Creating notification...")

        val builder = NotificationCompat.Builder(ctx, notificationChannelID)
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

    fun createNotificationChannel(ctx: Context, titleResID: Int, descriptionResID: Int, channelID: String) {
        notificationChannelID = channelID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = ctx.getString(titleResID)
            val descriptionText = ctx.getString(descriptionResID)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(notificationChannelID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startRecurringNotifications(ctx: Context, workerRequest: PeriodicWorkRequest, startHour: Int = INITIAL_START_HOUR_24_FORMAT) {
        var hoursDelay = DateTimeUtils.getCurrentHoursDifference(startHour)
        if (hoursDelay <= 0) hoursDelay = 1
        WorkManager.getInstance(ctx).enqueueUniquePeriodicWork(workerRequest.id.toString(), ExistingPeriodicWorkPolicy.KEEP, workerRequest)
        Timber.i("Started notification worker with delay:$hoursDelay")
    }

    data class NotificationMessage(val title: String, val message: String, val iconResID: Int, val pendingIntent: PendingIntent)



}