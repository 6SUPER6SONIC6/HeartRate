package com.supersonic.heartrate.screens.onboarding

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.supersonic.heartrate.R
import com.supersonic.heartrate.models.OnboardingPage
import com.supersonic.heartrate.navigation.NavigationDestination
import com.supersonic.heartrate.ui.theme.HeartRateTheme
import com.supersonic.heartrate.util.onboarding.OnboardingPages
import kotlinx.coroutines.launch

object OnboardingScreenDestination : NavigationDestination {
    override val route = "onboarding"
}
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onOnboardingFinish: () -> Unit
) {
    Scaffold(
        modifier = modifier
    ) {
        OnboardingScreenContent(
            modifier = Modifier
            .padding(it),
            onboardPagesList = OnboardingPages.pagesList,
            onFinish = onOnboardingFinish
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
private fun OnboardingScreenContent(
    modifier: Modifier = Modifier,
    onboardPagesList: List<OnboardingPage> = listOf(),
    onFinish: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var shouldShowRationale by remember { mutableStateOf(false) }

    if (shouldShowRationale){
        AlertDialog(
            onDismissRequest = { shouldShowRationale = false },
            title = {
                Text(
                    text = stringResource(R.string.onboardingScreen_cameraPermission_dialog_title),
                    textAlign = TextAlign.Center,
                    style = typography.titleMedium
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.onboardingScreen_cameraPermission_dialog_body),
                    textAlign = TextAlign.Center,
                    style = typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    })
                    shouldShowRationale = false
                }) {
                    Text(text = stringResource(R.string.onboardingScreen_cameraPermission_dialog_confirmButton))
                }
            },
            dismissButton = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray),
                    onClick = { shouldShowRationale = false }) {
                    Text(text = stringResource(R.string.onboardingScreen_cameraPermission_dialog_dismissButton))
                }
            }
        )
    }
    
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        onboardPagesList.size
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        HorizontalPager(
            modifier = Modifier.weight(5F),
            state = pagerState,
        ) { page ->
            val currentOnboardingPage = onboardPagesList[page]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(currentOnboardingPage.imageRes),
                    modifier = Modifier
                        .size(256.dp),
                    contentDescription = null,
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp)
                ){
                    Text(
                        text = stringResource(currentOnboardingPage.titleRes),
                        style = typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = stringResource(id = currentOnboardingPage.bodyRes),
                        textAlign = TextAlign.Center,
                        style = typography.bodyMedium,
                        modifier = Modifier
                    )

                    if (currentOnboardingPage.cameraPermission){
                        TextButton(
                            modifier = Modifier.padding(top = 16.dp),
                            enabled = !cameraPermissionState.hasPermission,
                            onClick = {
                                if (cameraPermissionState.permissionRequested) shouldShowRationale = true
                                else cameraPermissionState.launchPermissionRequest()
                            }
                        ) {
                            Text(text = stringResource(R.string.onboardingPage2_button_camera))
                        }
                    }
                }

            }

        }
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .height(32.dp),
            ) {
                repeat(onboardPagesList.size) {

                    val width: Dp by animateDpAsState(
                        targetValue = if (pagerState.currentPage == it) 44.dp else 14.dp,
                        animationSpec = tween(
                            durationMillis = 200,
                            easing = EaseInCubic
                        ),
                        label = "OnboardingPages"
                    )

                    if (pagerState.currentPage == it){
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(width = width, height = 14.dp)
                                .background(colorScheme.primary, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    scope.launch {
                                        pagerState.animateScrollToPage(it)
                                    }
                                }
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(width = width, height = 14.dp)
                                .background(Color.Gray, shape = CircleShape)
                                .clickable {
                                    scope.launch {
                                        pagerState.animateScrollToPage(it)
                                    }
                                }
                        )
                    }


                }
            }
            
            Button(
                enabled = when{
                    pagerState.currentPage == onboardPagesList.lastIndex && cameraPermissionState.hasPermission -> true
                    pagerState.currentPage != onboardPagesList.lastIndex -> true
                    else -> false
                },
                onClick = {
                scope.launch{
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
                    if (pagerState.currentPage == onboardPagesList.lastIndex && cameraPermissionState.hasPermission){
                        onFinish.invoke()
                    }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text =
                if (pagerState.currentPage == onboardPagesList.lastIndex)
                    stringResource(id = R.string.onboardingPage_button1)
                    else
                    stringResource(R.string.onboardingPage_button2),
                    style = typography.labelMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenContentPreview() {
    HeartRateTheme {
        Scaffold {
            OnboardingScreenContent(
                modifier = Modifier.padding(it),
                onFinish = {},
                onboardPagesList = OnboardingPages.pagesList
                )
        }
    }
}