package com.supersonic.heartrate.screens.measurement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.supersonic.heartrate.R
import com.supersonic.heartrate.db.HeartRateRepository
import com.supersonic.heartrate.models.HeartRate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MeasurementViewModel @Inject constructor(
    private val heartRateRepository: HeartRateRepository
): ViewModel(){

    val measurementAccuracyMap = mapOf(
        R.string.measurementAccuracy_low_sec to 10_000,
        R.string.measurementAccuracy_mid_sec to 20_000,
        R.string.measurementAccuracy_high_sec to 30_000,
    )

    var insertedHeartRateId by mutableIntStateOf(0)
        private set

    suspend fun onMeasurementFinish(heartRate: HeartRate){
        val id = saveHeartRate(heartRate)
        insertedHeartRateId = id
    }

    private suspend fun saveHeartRate(heartRate: HeartRate): Int {
        val id = heartRateRepository.insertHeartRate(heartRate).toInt()
        return id
    }
}