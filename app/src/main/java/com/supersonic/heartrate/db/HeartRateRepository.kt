package com.supersonic.heartrate.db

import com.supersonic.heartrate.models.HeartRate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HeartRateRepository @Inject constructor(private val heartRateDao: HeartRateDao) {

    fun getAllHeartRatesStream(): Flow<List<HeartRate>> = heartRateDao.getAllHeartRates()

    fun getHeartRateStream(id: Int): Flow<HeartRate> = heartRateDao.getHearRate(id)

    suspend fun insertHeartRate(heartRate: HeartRate): Long = heartRateDao.insertHeartRate(heartRate)

    suspend fun deleteHeartRate(heartRate: HeartRate) = heartRateDao.deleteHeartRate(heartRate)

    suspend fun deleteAllHeartRates() = heartRateDao.deleteAllHeartRates()
}