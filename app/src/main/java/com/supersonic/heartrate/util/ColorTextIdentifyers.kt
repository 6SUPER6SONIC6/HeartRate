package com.supersonic.heartrate.util

import androidx.compose.ui.graphics.Color
import com.supersonic.heartrate.R


val lowColor = Color(0xFF40C4FF)
val midColor = Color(0xFF00C853)
val highColor = Color(0xFFFF5252)

fun identifyResultText(bpm: Int): Int{
    return when{
        bpm < 60 -> R.string.resultCard_delayed
        bpm in 60..100 -> R.string.resultCard_standard

        else -> R.string.resultCard_frequent
    }
}

fun identifyResultColor(bpm: Int): Color {
    return when {
        bpm < 60 -> lowColor
        bpm in 60..100 -> midColor

        else -> highColor
    }
}

fun identifyAccuracyColor(measurementAccuracy: Int): Color {
    return when(measurementAccuracy) {
        R.string.measurementAccuracy_low, R.string.measurementAccuracy_low_sec  -> lowColor
        R.string.measurementAccuracy_mid, R.string.measurementAccuracy_mid_sec-> midColor
        R.string.measurementAccuracy_high, R.string.measurementAccuracy_high_sec-> highColor

        else -> Color.Red
    }
}