package com.supersonic.heartrate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supersonic.heartrate.R
import com.supersonic.heartrate.models.HeartRate
import com.supersonic.heartrate.ui.theme.HeartRateTheme
import java.util.Locale

@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    heartRate: HeartRate
) {
    Card(
        modifier = modifier
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFEFEFE)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${heartRate.bpm} " + stringResource(id = R.string.bpm).uppercase(Locale.ROOT),
                modifier = Modifier
                    .weight(3F),
                style = typography.displayMedium,
                color = Color.Black
            )
            
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(.1F)
                    .width(6.dp)
                    .background(
                        color = colorScheme.primary,
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp)
            )

            Column(
                modifier = Modifier
                    .weight(3F)
                    .padding(start = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = heartRate.time,
                    style = typography.displaySmall.copy(fontSize = 24.sp),
                    color = Color.Gray
                )

                Text(
                    text = heartRate.date,
                    style = typography.displaySmall.copy(fontSize = 24.sp),
                    color = Color.Gray
                )
            }
        }
    }
    
}

@Preview
@Composable
private fun HistoryCardPreview() {
    HeartRateTheme {
        HistoryCard(
            heartRate =  HeartRate(0,100, "12:54", "19/06/2024")
        )
    }
}