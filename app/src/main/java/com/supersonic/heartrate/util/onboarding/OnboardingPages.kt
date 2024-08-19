package com.supersonic.heartrate.util.onboarding

import com.supersonic.heartrate.R
import com.supersonic.heartrate.models.OnboardingPage

object OnboardingPages {
    val pagesList = listOf(
        OnboardingPage(
            imageRes = R.drawable.measurement_accuracy,
            titleRes = R.string.onboardingPage1_title,
            bodyRes = R.string.onboardingPage1_body
        ),
        OnboardingPage(
            imageRes = R.drawable.hands_phone,
            titleRes = R.string.onboardingPage2_title,
            bodyRes = R.string.onboardingPage2_body
        ),
        OnboardingPage(
            imageRes = R.drawable.result_card,
            titleRes = R.string.onboardingPage3_title,
            bodyRes = R.string.onboardingPage3_body
        ),
    )
}