package com.supersonic.heartrate.screens.loading

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.supersonic.heartrate.R
import com.supersonic.heartrate.components.AnimatedLinearProgressIndicator
import com.supersonic.heartrate.components.BackgroundedSurface
import com.supersonic.heartrate.navigation.NavigationDestination
import com.supersonic.heartrate.ui.theme.HeartRateTheme

object LoadingScreenDestination : NavigationDestination {
    override val route = "loading"
}
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    onNavigationNext: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
    ) {
        LoadingScreenContent(
            modifier = modifier.padding(it),
            onNavigationNext = onNavigationNext
        )
    }
}

@Composable
fun LoadingScreenContent(
    modifier: Modifier = Modifier,
    onNavigationNext: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BackgroundedSurface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5F),
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.heart),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = typography.displayLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
        AnimatedLinearProgressIndicator(
            animationDuration = 2_000,
            modifier = Modifier
                .fillMaxWidth(.8f)
                .weight(1F),
            strokeCap = StrokeCap.Round,
            trackColor = colorScheme.secondary,
            onLoadFinish = onNavigationNext
        )
    }
}

@Preview(device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420", showBackground = true, showSystemUi = true)
@Composable
private fun LoadingScreenContentPreview() {
    HeartRateTheme {
        LoadingScreenContent(onNavigationNext = {})
    }
}