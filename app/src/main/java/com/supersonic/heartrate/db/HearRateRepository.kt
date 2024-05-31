package com.supersonic.heartrate.db

import com.supersonic.heartrate.models.HeartRate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HearRateRepository @Inject constructor(private val heartRateDao: HeartRateDao) {

    suspend fun getAllHeartRatesStream(): Flow<List<HeartRate>> = heartRateDao.getAllHeartRates()

    suspend fun getHeartRateStream(id: Int): Flow<HeartRate> = heartRateDao.getHearRate(id)

    suspend fun insertHeartRate(heartRate: HeartRate) = heartRateDao.insertHeartRate(heartRate)

    suspend fun deleteAllHeartRates() = heartRateDao.deleteAllHeartRates()
}