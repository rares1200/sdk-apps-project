package ro.weekendrrsapps.sdk.notifications

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class NotificationWorker (private val activityContext: Context, workerParams: WorkerParameters, val message: NotificationsManager.NotificationMessage?):
    Worker(activityContext, workerParams) {

    override fun doWork(): Result {
        Timber.i("Doing work on notification worker")
        if (message != null) {
            NotificationsManager.createNotification(
                activityContext,
                message.title,
                message.message,
                message.iconResID,
                message.pendingIntent)
        }
        return Result.success()
    }
}