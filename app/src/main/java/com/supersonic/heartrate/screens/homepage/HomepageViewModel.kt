package com.supersonic.heartrate.screens.homepage

import androidx.lifecycle.ViewModel
import com.supersonic.heartrate.db.HeartRateRepository
import com.supersonic.heartrate.models.HeartRate
import com.supersonic.heartrate.util.getCurrentDate
import com.supersonic.heartrate.util.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(
    private val heartRateRepository: HeartRateRepository
) : ViewModel(){

    private val _homepageUiState = MutableStateFlow<HomePageUiState>(HomePageUiState.Home)
    val homePageUiState = _homepageUiState.asStateFlow()

    private val _insertedHeartRateId = MutableStateFlow(0)
    val insertedHeartRateId = _insertedHeartRateId.asStateFlow()
//    var insertedHeartRateId by mutableIntStateOf(0)
//        private set
    fun openMeasurementScreen(){
        _homepageUiState.value = HomePageUiState.Measurement
    }

    fun openHomeScreen(){
        _homepageUiState.value = HomePageUiState.Home
    }

    suspend fun onMeasurementFinish(bpm: Int){
       val id = saveHeartRate(
            HeartRate(
                bpm = bpm,
                time = getCurrentTime(),
                date = getCurrentDate()
            )
        )
        _insertedHeartRateId.value = id
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