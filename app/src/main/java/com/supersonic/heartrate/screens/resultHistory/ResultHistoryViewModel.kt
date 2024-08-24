package com.supersonic.heartrate.screens.resultHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supersonic.heartrate.db.HeartRateRepository
import com.supersonic.heartrate.models.HeartRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ResultHistoryViewModel @Inject constructor(
    private val heartRateRepository: HeartRateRepository
): ViewModel() {

    val heartRatesList: StateFlow<List<HeartRate>> =
        heartRateRepository.getAllHeartRatesStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    suspend fun clearHistory(){
        heartRateRepository.deleteAllHeartRates()
    }

    suspend fun deleteItem(heartRate: HeartRate){
        heartRateRepository.deleteHeartRate(heartRate)
    }

}