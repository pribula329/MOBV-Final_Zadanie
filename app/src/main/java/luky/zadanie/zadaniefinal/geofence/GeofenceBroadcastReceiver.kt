package luky.zadanie.zadaniefinal.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("Som tu")
        val geofencingEvent = intent?.let { GeofencingEvent.fromIntent(it) }
        if (geofencingEvent != null) {
            if (geofencingEvent.hasError()) {
                val errorMessage = GeofenceStatusCodes
                    .getStatusCodeString(geofencingEvent.errorCode)
                return
            }
        } else {
            return
        }

        // Get the transition type.
        val geofenceTransition = geofencingEvent.geofenceTransition
        println(geofenceTransition.toString())
        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            println("---------------------------------------------------Out of circle")
            val triggeringGeofences = geofencingEvent.triggeringGeofences
            triggeringGeofences?.forEach {
                if (it.requestId.compareTo("mygeofence") == 0) {
                    context?.let { context ->
                        val uploadWorkRequest: WorkRequest =
                            OneTimeWorkRequestBuilder<CheckoutWorker>()
                                .build()
                        WorkManager
                            .getInstance(context)
                            .enqueue(uploadWorkRequest)
                    }
                }
            }
        }
    }
}