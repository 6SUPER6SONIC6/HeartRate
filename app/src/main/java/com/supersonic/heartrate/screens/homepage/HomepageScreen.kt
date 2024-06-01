package com.supersonic.heartrate.screens.homepage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.supersonic.heartrate.R
import com.supersonic.heartrate.components.AnimatedLinearProgressIndicator
import com.supersonic.heartrate.components.BackgroundedSurface
import com.supersonic.heartrate.components.TopBar
import com.supersonic.heartrate.navigation.NavigationDestination
import com.supersonic.heartrate.ui.theme.HeartRateTheme
import kotlinx.coroutines.launch

object HomepageScreenDestination : NavigationDestination {
    override val route = "homepage"
}

@Composable
fun HomepageScreen(
    modifier: Modifier = Modifier,
    viewModel: HomepageViewModel,
    onNavigateToResultHistory: () -> Unit,
    onNavigationToResult: (Int) -> Unit
) {

    val homePageUiState by viewModel.homePageUiState.collectAsState()
    val scope = rememberCoroutineScope()
    val id by viewModel.insertedHeartRateId.collectAsState()

    when(homePageUiState){
        HomePageUiState.Home -> {
            Scaffold(
                topBar = {
                    HomePageTopBar(onNavigateToResultHistory = onNavigateToResultHistory)
                }
            ){
                HomepageScreenContent(
                    modifier = modifier
                        .padding(it)
                        .fillMaxSize(),
                    onMeasurementButtonClick = { viewModel.openMeasurementScreen() }
                )
            }
        }
        HomePageUiState.Measurement -> {
            MeasurementContent(
                modifier = modifier.fillMaxSize(),
                onCancelMeasurement = { viewModel.openHomeScreen() },
                onMeasurementFinish = {
                    scope.launch {
                        viewModel.onMeasurementFinish(it)
                    }
//                    onNavigationToResult(id)
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
) {
    TopBar(
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
                Icon(painter = painterResource(id = R.drawable.icon_time_machine), contentDescription = null)
            }
        }
    )
}

@Composable
private fun HomepageScreenContent(
    modifier: Modifier = Modifier,
    onMeasurementButtonClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BackgroundedSurface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5F),
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.homePage_title1),
                    style = typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 32.dp)
                )
                Spacer(modifier = Modifier.height(128.dp))
                Image(
                    painter = painterResource(R.drawable.heart),
                    contentDescription = null
                )
            }


        }

        Box(
            modifier = Modifier
                .weight(1F)

        ){
            IconButton(
                onClick = onMeasurementButtonClick,
                modifier = Modifier
                    .size(114.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.button),
                    modifier = Modifier.size(114.dp),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun MeasurementContent(
    modifier: Modifier = Modifier,
    onMeasurementFinish: (Int) -> Unit,
    onCancelMeasurement: () -> Unit
) {
    var currentHeartRate by remember { mutableIntStateOf(0) }
    var isFingerDetected by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        BackgroundedSurface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5F),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    contentAlignment = Alignment.CenterEnd
                ){
                    if (!isFingerDetected) {
                        IconButton(
                            onClick = onCancelMeasurement,
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = Color.Gray
                            ),
                            modifier = Modifier
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = null
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
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

                Spacer(modifier = Modifier.height(48.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.heart2),
                        modifier = Modifier,
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



                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ){
                    if (isFingerDetected) {
                        AnimatedLinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth(.8f),
                            animationDuration = 10_000,
                            strokeCap = StrokeCap.Round,
                            trackColor = colorScheme.secondary,
                            onLoadFinish = { onMeasurementFinish(currentHeartRate) }
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.hands_phone),
                            modifier = Modifier
                                .size(width = 141.dp, height = 174.dp),
                            contentDescription = null
                        )
                    }
                }

            }
        }
        Spacer(modifier = Modifier.weight(1F))
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
                onCancelMeasurement = {},
                onMeasurementFinish = {}
                )
        }
    }
}