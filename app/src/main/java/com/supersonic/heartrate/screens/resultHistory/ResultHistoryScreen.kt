package com.supersonic.heartrate.screens.resultHistory

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.supersonic.heartrate.R
import com.supersonic.heartrate.components.HistoryCard
import com.supersonic.heartrate.components.ScreenTemplate
import com.supersonic.heartrate.components.TopBar
import com.supersonic.heartrate.models.HeartRate
import com.supersonic.heartrate.navigation.NavigationDestination
import com.supersonic.heartrate.util.highColor
import com.supersonic.heartrate.util.lowColor
import com.supersonic.heartrate.util.midColor
import kotlinx.coroutines.launch

object ResultHistoryScreenDestination : NavigationDestination {
    override val route = "result_history"
}

@Composable
fun ResultHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: ResultHistoryViewModel,
    onBackClick: () -> Unit
) {
    val heartRatesList by viewModel.heartRatesList.collectAsState()
    val scope = rememberCoroutineScope()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }


    BackHandler {
        onBackClick.invoke()
    }

    if (showDeleteDialog){
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                Button(onClick = { scope.launch {
                    viewModel.clearHistory()
                    showDeleteDialog = false
                } }) {
                    Text(text = "Delete")
                }
            }
        )
    }
    if (showInfoDialog){
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = {
                Text(
                    text = "Що означає колір розділяючої полоси на картці результату?",
                    textAlign = TextAlign.Center,
                    style = typography.titleMedium,
                )
            },
            text = {
                Column {
                    Text(
                        text = "З якою точністю було зроблене вимірювання:",
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Box(modifier = Modifier
                            .size(12.dp)
                            .background(lowColor, CircleShape))
                        Text(
                            text = "–",
                            modifier = Modifier.padding(horizontal = 4.dp),
                            style = typography.bodyMedium
                        )
                        Text(
                            text = "Низька точнісь",
                            style = typography.bodyMedium
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Box(modifier = Modifier
                            .size(12.dp)
                            .background(midColor, CircleShape))
                        Text(
                            text = "–",
                            modifier = Modifier.padding(horizontal = 4.dp),
                            style = typography.bodyMedium
                        )
                        Text(
                            text = "Cередня точнісь",
                            style = typography.bodyMedium
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Box(modifier = Modifier
                            .size(12.dp)
                            .background(highColor, CircleShape))
                        Text(
                            text = "–",
                            modifier = Modifier.padding(horizontal = 4.dp),
                            style = typography.bodyMedium
                        )
                        Text(
                            text = "Висока точнісь",
                            style = typography.bodyMedium
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { scope.launch {
                    showInfoDialog = false
                } }) {
                    Text(text = "Зрозуміло")
                }
            }
        )
    }

    ScreenTemplate(
        topBar = {
            ResultHistoryTopBar(
                onBackClick = onBackClick,
                onClearHistoryClick = { showDeleteDialog = true },
                onInfoClick = { showInfoDialog = true }
            )
        }
    ) {
        ResultHistoryScreenContent(
            modifier = modifier,
            heartRatesList = heartRatesList
        )
    }

}

@Composable
private fun ResultHistoryTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onClearHistoryClick: () -> Unit,
    onInfoClick: () -> Unit
) {
    TopBar(
        modifier = modifier,
        title = stringResource(id = R.string.topAppBar_title_history),
        isBackIconEnabled = true,
        onBackClick = onBackClick,
        actions = {

            IconButton(
                onClick = onInfoClick,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = colorScheme.onPrimary
                )
                ) {
                Icon(imageVector = Icons.Outlined.Info, contentDescription = null)
            }

            IconButton(
                onClick = onClearHistoryClick,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = colorScheme.onPrimary
                )
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
            }
        }
    )
}
@Composable
private fun ResultHistoryScreenContent(
    modifier: Modifier = Modifier,
    heartRatesList: List<HeartRate>
) {
    if (heartRatesList.isNotEmpty()){
        LazyColumn(
            modifier = modifier
        ) {
            items(heartRatesList, key = { it.id }) { heartRate ->
                HistoryCard(
                    heartRate = heartRate,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    } else {
        Text(
            text = stringResource(R.string.history_empty_text),
            style = typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}