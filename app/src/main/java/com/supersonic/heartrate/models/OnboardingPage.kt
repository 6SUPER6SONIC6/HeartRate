package com.supersonic.heartrate.models

data class OnboardingPage(
    val imageRes: Int,
    val titleRes: Int,
    val bodyRes: Int,
    val cameraPermission: Boolean = false
)