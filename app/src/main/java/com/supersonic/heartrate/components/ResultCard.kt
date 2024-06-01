package com.supersonic.heartrate.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.supersonic.heartrate.R
import com.supersonic.heartrate.models.HeartRate
import com.supersonic.heartrate.ui.theme.HeartRateTheme

val delayedColor = Color(0xFF40C4FF)
val standardColor = Color(0xFF00C853)
val frequentColor = Color(0xFFFF5252)
@Composable
fun ResultCard(
    modifier: Modifier = Modifier,
    heartRate: HeartRate
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.secondaryContainer
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(
                    modifier = Modifier

                ) {
                    Text(
                        text = stringResource(R.string.resultCard_title),
                        style = typography.bodyLarge,
                    )

                    Text(
                        text = stringResource(identifyResultText(heartRate.bpm)),
                        modifier = Modifier.padding(top = 2.dp),
                        style = typography.titleLarge,
                        color = identifyResultColor(heartRate.bpm)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                      painter = painterResource(id = R.drawable.icon_time_24px),
                      modifier = Modifier
                          .padding(2.dp)
                          .size(18.dp),
                      tint = Color.Gray,
                      contentDescription = null,
                  )
                    Column {
                        Text(
                            text = heartRate.time,
                            style = typography.bodyMedium,
                            color = Color.Gray
                        )

                        Text(
                            text = heartRate.date,
                            style = typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
            // Indicator
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(CircleShape)
                ){
                    Box(
                        modifier = Modifier
                            .weight(1F)
                            .background(delayedColor)
                            .height(24.dp)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1F)
                            .background(standardColor)
                            .height(24.dp)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1F)
                            .background(frequentColor)
                            .height(24.dp)
                    )
                }
                val indicatorOffset by animateDpAsState(
                    targetValue = calculateIndicatorOffset(heartRate.bpm, 340.dp),
                    animationSpec = tween(
                        durationMillis = 1_500
                    ),
                    label = "indicator animation"
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(x = indicatorOffset)
                        .background(Color.White, shape = RoundedCornerShape(4.dp))
                        .size(width = 6.dp, height = 20.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .background(
                                color = colorScheme.primaryContainer,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .width(140.dp)
                    ){
                        Row(
                            modifier = Modifier.padding(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .size(8.dp)
                                    .background(
                                        color = delayedColor,
                                        shape = CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = stringResource(id = R.string.resultCard_delayed),
                                style = typography.bodySmall,
                            )
                        }
                    }

                    Box(modifier = Modifier){
                        Text(
                            text = stringResource(id = R.string.resultCard_delayed_value),
                            style = typography.bodySmall,
                            color = if (identifyResultColor(heartRate.bpm) == delayedColor) Color.Black
                                    else Color.Gray
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .background(
                                color = colorScheme.primaryContainer,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .width(140.dp)
                    ){
                        Row(
                            modifier = Modifier.padding(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Box(modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = standardColor,
                                    shape = CircleShape
                                ))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = stringResource(id = R.string.resultCard_standard),
                                style = typography.bodySmall,
                            )
                        }
                    }

                    Box(modifier = Modifier){
                        Text(
                            text = stringResource(id = R.string.resultCard_standard_value),
                            style = typography.bodySmall,
                            color = if (identifyResultColor(heartRate.bpm) == standardColor) Color.Black
                            else Color.Gray
                        )
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .background(
                                color = colorScheme.primaryContainer,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .width(140.dp)
                    ){
                        Row(
                            modifier = Modifier.padding(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Box(modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = frequentColor,
                                    shape = CircleShape
                                ))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = stringResource(id = R.string.resultCard_frequent),
                                style = typography.bodySmall,
                            )
                        }
                    }

                    Box(modifier = Modifier){
                        Text(
                            text = stringResource(id = R.string.resultCard_frequent_value),
                            style = typography.bodySmall,
                            color = if (identifyResultColor(heartRate.bpm) == frequentColor) Color.Black
                            else Color.Gray
                        )
                    }
                }

            }

        }
    }
}

@Preview
@Composable
private fun ResultCardPreview() {
    HeartRateTheme {
        ResultCard(
            heartRate =  HeartRate(0,50, "12:54", "19/06/2024"),
            modifier = Modifier.padding(30.dp)
        )
    }
}

fun identifyResultText(bpm: Int): Int{
    return when{
        bpm < 60 -> R.string.resultCard_delayed
        bpm in 60..100 -> R.string.resultCard_standard

        else -> R.string.resultCard_frequent
    }
}

fun identifyResultColor(bpm: Int): Color {
    return when {
        bpm < 60 -> delayedColor
        bpm in 60..100 -> standardColor

        else -> frequentColor
    }
}

fun calculateIndicatorOffset(bpm: Int, maxWidth: Dp): Dp {

    val offsetFraction = when {
        bpm < 59 -> bpm / 60f * 0.33f
        bpm in 59..99 -> 0.33f + (bpm - 60) / 60f * 0.33f
        else -> 0.66f + (bpm - 100) / 60f * 0.34f
    }

    val calculatedOffset = maxWidth * offsetFraction

    return if (calculatedOffset > maxWidth) maxWidth else calculatedOffset
}