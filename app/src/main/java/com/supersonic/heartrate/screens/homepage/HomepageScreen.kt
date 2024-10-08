package com.supersonic.heartrate.screens.homepage

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.supersonic.heartrate.R
import com.supersonic.heartrate.components.ScreenTemplate
import com.supersonic.heartrate.components.TopBar
import com.supersonic.heartrate.navigation.NavigationDestination
import com.supersonic.heartrate.ui.theme.HeartRateTheme

object HomepageScreenDestination : NavigationDestination {
    override val route = "homepage"
}

@Composable
fun HomepageScreen(
    modifier: Modifier = Modifier,
    needDisplayOnboarding: Boolean = true,
    onNavigationNext: () -> Unit,
    onNavigateToResultHistory: () -> Unit
) {

    ScreenTemplate(
        topBar = { HomeTopBar( onNavigateToResultHistory = onNavigateToResultHistory ) }
    ) {
        HomepageScreenContent(
            modifier = modifier
                .fillMaxSize(),
            isFirstMeasurement = needDisplayOnboarding,
            onMeasurementButtonClick = onNavigationNext
        )
    }
}

@Composable
fun HomeTopBar(
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

    val infiniteTransition = rememberInfiniteTransition(label = "InfiniteTransition")
    val animatedAlpha by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 1F,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "alpha"
    )

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
                    .padding(top = 24.dp)
            )
        }

        Image(
            painter = painterResource(R.drawable.heart),
            modifier = Modifier
                .align(Alignment.Center),
            contentDescription = null
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ){

            Box(
                modifier = Modifier
                    .alpha(if(isFirstMeasurement) animatedAlpha else 0F)
                    .background(color = colorScheme.secondary, CircleShape)
                    .size(112.dp)
            )

            IconButton(
                onClick = onMeasurementButtonClick,
                modifier = Modifier
                    .size(120.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.button),
                    modifier = Modifier.size(100.dp),
                    contentDescription = null
                )
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