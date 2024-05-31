package com.supersonic.heartrate.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.supersonic.heartrate.ui.theme.HeartRateTheme

@Composable
fun AnimatedLinearProgressIndicator(
    modifier: Modifier = Modifier,
    indicatorProgress: Float = 1F,
    animationDuration: Int = 1_500,
    color: Color = ProgressIndicatorDefaults.linearColor,
    trackColor: Color = ProgressIndicatorDefaults.linearTrackColor,
    strokeCap: StrokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
    onLoadFinish: () -> Unit
) {
    var progress by rememberSaveable { mutableFloatStateOf(0F) }
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = FastOutSlowInEasing
        ),
        finishedListener = { _ -> onLoadFinish.invoke()},
        label = "LinearProgressIndicator animation"
    )
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .border(1.dp, colorScheme.primary, CircleShape),
            progress = { progressAnimation },
            color = color,
            trackColor = trackColor,
            strokeCap = strokeCap
        )
        Text(
            text = "${(progressAnimation * 100).toInt()}%",
            style = typography.bodyMedium,
            color = colorScheme.onPrimary,
            modifier = Modifier
                .padding(vertical = 2.dp)
        )
    }
    LaunchedEffect(Unit) {
        progress = indicatorProgress
    }
}

@Preview
@Composable
private fun AnimatedLinearProgressIndicatorPreview() {
    HeartRateTheme {
        AnimatedLinearProgressIndicator(onLoadFinish = {})
    }


}