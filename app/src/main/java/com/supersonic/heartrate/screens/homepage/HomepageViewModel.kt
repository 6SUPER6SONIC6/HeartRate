package com.supersonic.heartrate.screens.homepage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supersonic.heartrate.R
import com.supersonic.heartrate.db.HeartRateRepository
import com.supersonic.heartrate.models.HeartRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(
    private val heartRateRepository: HeartRateRepository
) : ViewModel(){



    private val _homepageUiState = MutableStateFlow<HomePageUiState>(HomePageUiState.Home)
    val homePageUiState = _homepageUiState.asStateFlow()

    val heartRatesList: StateFlow<List<HeartRate>> =
        heartRateRepository.getAllHeartRatesStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    val measurementAccuracyMap = mapOf(
        R.string.measurementAccuracy_low to 10_000,
        R.string.measurementAccuracy_mid to 20_000,
        R.string.measurementAccuracy_high to 30_000,
    )

    var insertedHeartRateId by mutableIntStateOf(0)
        private set

    fun openMeasurementScreen(){
        _homepageUiState.value = HomePageUiState.Measurement
    }

    fun openHomeScreen(){
        _homepageUiState.value = HomePageUiState.Home
    }

    suspend fun onMeasurementFinish(bpm: Int){
        val currentDateTime: LocalDateTime = LocalDateTime.now()

        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val currentTime: String = currentDateTime.format(timeFormatter)

        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val currentDate: String = currentDateTime.format(dateFormatter)

       val id = saveHeartRate(
            HeartRate(
                bpm = bpm,
                time = currentTime,
                date = currentDate
            )
        )
        insertedHeartRateId = id
    }

    private suspend fun saveHeartRate(heartRate: HeartRate): Int {
         val id = heartRateRepository.insertHeartRate(heartRate).toInt()
        return id
    }

}

sealed class HomePageUiState {
    data object Home: HomePageUiState()
    data object Measurement: HomePageUiState()
}

