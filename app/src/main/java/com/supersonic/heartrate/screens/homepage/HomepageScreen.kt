package com.supersonic.heartrate.screens.homepage

import android.Manifest
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.supersonic.heartrate.R
import com.supersonic.heartrate.components.AnimatedLinearProgressIndicator
import com.supersonic.heartrate.components.DropdownList
import com.supersonic.heartrate.components.ScreenTemplate
import com.supersonic.heartrate.components.TopBar
import com.supersonic.heartrate.models.HeartRate
import com.supersonic.heartrate.navigation.NavigationDestination
import com.supersonic.heartrate.ui.theme.HeartRateTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object HomepageScreenDestination : NavigationDestination {
    override val route = "homepage"
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomepageScreen(
    modifier: Modifier = Modifier,
    viewModel: HomepageViewModel,
    onNavigateToResultHistory: () -> Unit,
    onNavigationToResult: (Int) -> Unit
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val homePageUiState by viewModel.homePageUiState.collectAsState()
    val heartRatesList by viewModel.heartRatesList.collectAsState()
    val scope = rememberCoroutineScope()
    val id = viewModel.insertedHeartRateId

    BackHandler(
        enabled = homePageUiState != HomePageUiState.Home
    ) {
        viewModel.openHomeScreen()
    }

    ScreenTemplate(
        topBar = {
            HomePageTopBar(
                onNavigateToResultHistory = onNavigateToResultHistory,
                isBackIconEnabled = homePageUiState == HomePageUiState.Measurement,
                onBackClick = { viewModel.openHomeScreen() },
            )
        }
    ) {
        when(homePageUiState){
            HomePageUiState.Home -> HomepageScreenContent(
                modifier = modifier
                    .fillMaxSize(),
                isFirstMeasurement = heartRatesList.isEmpty(),
                onMeasurementButtonClick = {
                    if (cameraPermissionState.hasPermission) viewModel.openMeasurementScreen()
                    else cameraPermissionState.launchPermissionRequest()
                }
            )
            HomePageUiState.Measurement -> MeasurementContent(
                modifier = modifier.fillMaxSize(),
                measurementAccuracyMap = viewModel.measurementAccuracyMap,
                onMeasurementFinish = {
                    scope.launch {
                        viewModel.onMeasurementFinish(it)
                    }
                }
            )
        }

    }


    LaunchedEffect(id) {
    Log.d("insertedHeartRateId", "insertedHeartRateId: $id")
    if(id > 0){
        onNavigationToResult(id)
    }
}

}

@Composable
private fun HomePageTopBar(
    modifier: Modifier = Modifier,
    onNavigateToResultHistory: () -> Unit,
    isBackIconEnabled: Boolean = false,
    onBackClick: () -> Unit = {},
) {
    TopBar(
        modifier = modifier,
        isBackIconEnabled = isBackIconEnabled,
        onBackClick = onBackClick,
        actions = {
            Text(
                text = stringResource(R.string.topAppBar_title_history),
                style = typography.titleSmall,
                color = colorScheme.onPrimary
            )
            IconButton(
                onClick = onNavigateToResultHistory,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = colorScheme.onPrimary
                )
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.icon_time_machine),
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun HomepageScreenContent(
    modifier: Modifier = Modifier,
    isFirstMeasurement: Boolean = true,
    onMeasurementButtonClick: () -> Unit
) {
    Box(
        modifier = modifier,
    ) {
        if (isFirstMeasurement){
            Text(
                text = stringResource(R.string.homePage_title1),
                style = typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            )
        }

        Image(
            painter = painterResource(R.drawable.heart),
            modifier = Modifier
                .align(Alignment.Center)
                .size(260.dp),
            contentDescription = null
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ){
            IconButton(
                onClick = onMeasurementButtonClick,
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.button),
                    modifier = Modifier.size(120.dp),
                    contentDescription = null
                )
            }
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
                modifier = Modifier.size(260.dp),
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
                    text = stringResource(R.string.bpm).uppercase(Locale.ROOT),
                    style = typography.displaySmall,
                    color = colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )
            }

        }



//        Spacer(modifier = Modifier.height(180.dp))

        // Progress Bar / Measurement Accuracy Choosing
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.Center
        ){
            if (isFingerDetected) {
                measurementAccuracyMap[selectedItem]?.let {
                    AnimatedLinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth(.8f),
                        animationDuration = it,
                        strokeCap = StrokeCap.Round,
                        trackColor = colorScheme.secondary,
                        onLoadFinish = { onMeasurementFinish(
                            HeartRate(
                                bpm = currentHeartRate,
                                time = getCurrentTime(),
                                date = getCurrentDate(),
                                measurementAccuracy = when(selectedItem){
                                    R.string.measurementAccuracy_low_sec -> R.string.measurementAccuracy_low
                                    R.string.measurementAccuracy_mid_sec -> R.string.measurementAccuracy_mid
                                    R.string.measurementAccuracy_high_sec -> R.string.measurementAccuracy_high

                                    else -> R.string.measurementAccuracy_mid
                                },
                            )
                        ) }
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = stringResource(R.string.ChooseMeasurementAccuracy),
                        style = typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    DropdownList(
                        itemList = measurementAccuracyKeysList,
                        selectedItemResource = selectedItem,
                        onItemClick = { selectedItem = it }
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun HomepageScreenContentPreview() {
    HeartRateTheme {
        Scaffold {
            HomepageScreenContent(modifier = Modifier
                .padding(it)
                .fillMaxSize(),
                onMeasurementButtonClick = {}
                )
        }
    }
}

@Preview
@Composable
private fun MeasurementContentPreview() {
    HeartRateTheme {
        Scaffold {
            MeasurementContent(modifier = Modifier
                .padding(it)
                .fillMaxSize(),
                onMeasurementFinish = {},
                measurementAccuracyMap = mapOf()
            )
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