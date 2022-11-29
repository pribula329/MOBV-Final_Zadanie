package luky.zadanie.zadaniefinal.database

import android.content.ClipData
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pub::class, PubDetail::class, PubNear::class], version = 1, exportSchema = false)
abstract class PubRoomDatabase : RoomDatabase() {

    abstract fun pubDao(): PubDao

    companion object {
        @Volatile
        private var INSTANCE: PubRoomDatabase? = null

        fun getDatabase(context: Context): PubRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    PubRoomDatabase::class.java,
                    "pub_database").fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}