package com.supersonic.heartrate.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.supersonic.heartrate.models.HeartRate

@Database(
    entities = [HeartRate::class],
    version = 2
    )
abstract class HearRateDatabase : RoomDatabase() {
    abstract fun heartRateDao(): HeartRateDao

    companion object {
        @Volatile
        private var Instance: HearRateDatabase? = null

        fun getDatabase(context: Context): HearRateDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    HearRateDatabase::class.java,
                    "heartRate_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}