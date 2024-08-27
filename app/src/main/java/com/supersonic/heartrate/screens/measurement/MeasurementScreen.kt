package com.supersonic.heartrate.screens.measurement

import android.Manifest
import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.supersonic.heartrate.R
import com.supersonic.heartrate.components.AnimatedLinearProgressIndicator
import com.supersonic.heartrate.components.DropdownList
import com.supersonic.heartrate.components.ScreenTemplate
import com.supersonic.heartrate.models.HeartRate
import com.supersonic.heartrate.navigation.NavigationDestination
import com.supersonic.heartrate.screens.homepage.HomeTopBar
import com.supersonic.heartrate.screens.onboarding.CameraPermissionNotGrantedDialog
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object MeasurementScreenDestination : NavigationDestination {
    override val route = "measurement"
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MeasurementScreen(
    modifier: Modifier = Modifier,
    viewModel: MeasurementViewModel,
    onNavigateToResultHistory: () -> Unit,
    onNavigationToResult: (Int) -> Unit,
    onNavigateBack:() -> Unit
) {

    val scope = rememberCoroutineScope()
    val id = viewModel.insertedHeartRateId

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (!cameraPermissionState.hasPermission){
        CameraPermissionNotGrantedDialog(
            onDismiss = onNavigateBack,
            clickOutsideDismiss = false
        )
    }

    ScreenTemplate(
        topBar = {
            HomeTopBar(
                onNavigateToResultHistory = onNavigateToResultHistory,
                isBackIconEnabled = true,
                onBackClick = onNavigateBack
            )
        },
    ) {
        MeasurementContent(
            modifier = modifier.fillMaxSize(),
            measurementAccuracyMap = viewModel.measurementAccuracyMap,
            onMeasurementFinish = {
                scope.launch {
                    viewModel.onMeasurementFinish(it)
                }
            }
        )
    }

    LaunchedEffect(id) {
        Log.d("insertedHeartRateId", "insertedHeartRateId: $id")
        if(id > 0){
            onNavigationToResult(id)
        }
    }
}

@Composable
fun MeasurementContent(
    modifier: Modifier = Modifier,
    measurementAccuracyMap: Map<Int, Int>,
    onMeasurementFinish: (HeartRate) -> Unit
) {
    var currentHeartRate by remember { mutableIntStateOf(0) }
    var isFingerDetected by remember { mutableStateOf(false) }

    val measurementAccuracyKeysList = measurementAccuracyMap.keys.toList()
    var selectedItem by remember {
        mutableIntStateOf(measurementAccuracyKeysList.first())
    }

    Box(
        modifier = modifier
    ) {
        // Camera
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(64.dp)
                    .clip(CircleShape),
            ) {
                CameraPreview(
                    isFingerDetected = { isFingerDetected = it },
                    onHeartRateCalculated = { currentHeartRate = it }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = if (isFingerDetected)
                        stringResource(R.string.measurement_fingerDetected_text1)
                    else stringResource(R.string.measurement_fingerNotDetected_text1),
                    style = typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = if (isFingerDetected)
                        stringResource(R.string.measurement_fingerDetected_text2)
                    else stringResource(R.string.measurement_fingerNotDetected_text2),
                    style = typography.bodyMedium,
                    color = colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )
            }
        }


        // Heart
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.heart2),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isFingerDetected) "$currentHeartRate" else "--",
                    style = typography.displayLarge,
                    color = colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.bpm),
                    style = typography.displaySmall,
                    color = colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )
            }

        }

        // Progress Bar / Measurement Accuracy Choosing
        val infiniteTransition = rememberInfiniteTransition(label = "InfiniteTransition")
        val progressBarAlpha by infiniteTransition.animateFloat(
            initialValue = 0F,
            targetValue = 1F,
            animationSpec = infiniteRepeatable(tween(1_000), RepeatMode.Reverse),
            label = "alpha"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.Center
        ){
            if (isFingerDetected) {
                measurementAccuracyMap[selectedItem]?.let {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){

                        Box(
                            modifier = Modifier
                                .alpha(progressBarAlpha)
                                .fillMaxWidth(.82F)
                                .height(28.dp)
                                .background(color = colorScheme.primary, CircleShape)
                        )

                        AnimatedLinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth(.8f),
                            animationDuration = it,
                            strokeCap = StrokeCap.Round,
                            trackColor = colorScheme.secondary,
                            onLoadFinish = {
                                onMeasurementFinish(
                                    HeartRate(
                                        bpm = currentHeartRate,
                                        time = getCurrentTime(),
                                        date = getCurrentDate(),
                                        measurementAccuracy = when (selectedItem) {
                                            R.string.measurementAccuracy_low_sec -> R.string.measurementAccuracy_low
                                            R.string.measurementAccuracy_mid_sec -> R.string.measurementAccuracy_mid
                                            R.string.measurementAccuracy_high_sec -> R.string.measurementAccuracy_high

                                            else -> R.string.measurementAccuracy_mid
                                        },
                                    )
                                )
                            }
                        )
                    }
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = stringResource(R.string.measurement_chooseMeasurementAccuracy),
                        style = typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    DropdownList(
                        modifier = Modifier.width(320.dp),
                        itemList = measurementAccuracyKeysList,
                        selectedItemResource = selectedItem,
                        onItemClick = { selectedItem = it }
                    )
                }
            }
        }
    }

}

private fun getCurrentTime(): String{
    val currentDateTime: LocalDateTime = LocalDateTime.now()
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val currentTime: String = currentDateTime.format(timeFormatter)

    return currentTime
}
private fun getCurrentDate(): String{
    val currentDateTime: LocalDateTime = LocalDateTime.now()

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val currentDate: String = currentDateTime.format(dateFormatter)

    return currentDate
}