package com.supersonic.heartrate.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.supersonic.heartrate.models.HeartRate
import kotlinx.coroutines.flow.Flow

@Dao
interface HeartRateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHeartRate(heartRate: HeartRate)

    @Query("SELECT * from heart_rates WHERE id = :id")
    suspend fun getHearRate(id: Int): Flow<HeartRate>

    @Query("SELECT * from heart_rates")
    suspend fun getAllHeartRates(): Flow<List<HeartRate>>

    @Delete
    suspend fun deleteAllHeartRates()
}