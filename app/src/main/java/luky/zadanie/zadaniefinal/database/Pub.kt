package luky.zadanie.zadaniefinal.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pub")
data class Pub (
        @PrimaryKey
        val pubId: String,
        @ColumnInfo(name = "name")
        val pubName: String,
        @ColumnInfo(name = "type")
        val pubType: String,
        @ColumnInfo(name = "lat")
        val lat: Double,
        @ColumnInfo(name = "lon")
        var lon: Double,
        @ColumnInfo(name = "user_count")
        var usersCount: Int) {

}