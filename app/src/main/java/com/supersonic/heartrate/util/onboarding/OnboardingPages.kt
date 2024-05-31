package com.supersonic.heartrate.util.onboarding

import com.supersonic.heartrate.R
import com.supersonic.heartrate.models.OnboardingPage

object OnboardingPages {
    val pagesList = listOf(
        OnboardingPage(
            imageRes = R.drawable.onboarding1,
            titleRes = R.string.onboardingPage1_title,
            bodyRes = R.string.onboardingPage1_body
        ),
        OnboardingPage(
            imageRes = R.drawable.onboarding2,
            titleRes = R.string.onboardingPage2_title,
            bodyRes = R.string.onboardingPage2_body
        ),
        OnboardingPage(
            imageRes = R.drawable.onboarding3,
            titleRes = R.string.onboardingPage3_title,
            bodyRes = R.string.onboardingPage3_body
        ),
    )
}