package luky.zadanie.zadaniefinal.database

import android.location.Location
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "near_pub")
class PubNear(
    @PrimaryKey
    val nearId: String,
    @ColumnInfo(name = "near_lat")
    val nearLat: Double,
    @ColumnInfo(name = "near_lon")
    val nearLon: Double,
    @ColumnInfo(name = "near_type")
    val nearType: String,
    @ColumnInfo(name = "near_name")
    val nearName: String = "",
    @ColumnInfo(name = "distance")
    val distance: Double = 0.0
) {


    fun printString(){
        println("NearBar( id=$nearId, lat=$nearLat, lon=$nearLon, type=$nearType, name=$nearName, dist=$distance)")
    }
}