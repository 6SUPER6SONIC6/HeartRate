package com.supersonic.heartrate.screens.result

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supersonic.heartrate.db.HeartRateRepository
import com.supersonic.heartrate.models.HeartRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val heartRateRepository: HeartRateRepository
) : ViewModel() {

    private val hearRateId: Int = checkNotNull(savedStateHandle[ResultScreenDestination.heartRateIdArg])

    var heartRate by mutableStateOf(HeartRate(0,0,"",""))
        private set

    init {
        viewModelScope.launch {
            heartRate = heartRateRepository.getHeartRateStream(hearRateId)
                .filterNotNull()
                .first()
        }
    }

}