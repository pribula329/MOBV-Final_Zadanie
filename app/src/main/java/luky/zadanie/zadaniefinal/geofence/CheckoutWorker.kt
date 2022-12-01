package luky.zadanie.zadaniefinal.geofence

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import luky.zadanie.zadaniefinal.R
import luky.zadanie.zadaniefinal.network.ApiService
import luky.zadanie.zadaniefinal.network.NearPubMessageData

class CheckoutWorker(val appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            1, createNotification()
        )
    }

    override suspend fun doWork(): Result {
        val response =
            ApiService.create(appContext).nearPubCheckInOutService(NearPubMessageData("", "", "", 0.0, 0.0))
        return if (response.isSuccessful) Result.success() else Result.failure()
    }

    private fun createNotification(): Notification {
        println("End")
        val builder =
            NotificationCompat.Builder(appContext, "mobv2022").apply {
                setContentTitle("MOBV 2022")
                setContentText("You are exiting from pub")
                priority = NotificationCompat.PRIORITY_DEFAULT
            }

        return builder.build()
    }


}