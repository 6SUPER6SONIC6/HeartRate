package com.supersonic.heartrate.screens.history

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

object HistoryScreenDestination : NavigationDestination {
    override val route = "history"
}

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel,
    onBackClick: () -> Unit
) {
    val heartRatesList by viewModel.heartRatesList.collectAsState()
    val scope = rememberCoroutineScope()

    var showClearHistoryDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }


    BackHandler {
        onBackClick.invoke()
    }

    if (showClearHistoryDialog){
        ClearHistoryDialog(
            onDismiss = { showClearHistoryDialog = false },
            onConfirm = { scope.launch {
                viewModel.clearHistory()
            }})
    }

    if (showInfoDialog){
        InfoDialog(onDismiss = {showInfoDialog = false})
    }

    ScreenTemplate(
        topBar = {
            ResultHistoryTopBar(
                onBackClick = onBackClick,
                onClearHistoryClick = { showClearHistoryDialog = true },
                onInfoClick = { showInfoDialog = true }
            )
        }
    ) {
        ResultHistoryScreenContent(
            modifier = modifier,
            heartRatesList = heartRatesList,
            onItemDelete = {
                scope.launch {
                    viewModel.deleteItem(it)
                }
            }
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
    heartRatesList: List<HeartRate>,
    onItemDelete: (HeartRate) -> Unit
) {
    if (heartRatesList.isNotEmpty()){
        LazyColumn(
            modifier = modifier
        ) {
            items(heartRatesList, key = { it.id }) { heartRate ->
                SwipeToDeleteContainer(
                    item = heartRate,
                    onDelete = {
                        onItemDelete(heartRate)
                    }
                ){
                    HistoryCard(
                        heartRate = it,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

    } else {
        Text(
            text = stringResource(R.string.history_empty_text),
            style = typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun InfoDialog(
    onDismiss: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Outlined.Info,
                tint = colorScheme.primary,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = stringResource(R.string.history_info_dialog_title),
                textAlign = TextAlign.Center,
                style = typography.titleMedium,
            )
        },
        text = {
            Column {
                Text(
                    text = stringResource(R.string.history_info_dialog_body),
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
                        text = stringResource(R.string.history_info_dialog_body_low_accuracy),
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
                        text = stringResource(R.string.history_info_dialog_body_mid_accuracy),
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
                        text = stringResource(R.string.history_info_dialog_body_high_accuracy),
                        style = typography.bodyMedium
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.history_info_dialog_confirmButton))
            }
        }
    )

}

@Composable
fun ClearHistoryDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Outlined.Delete,
                tint = colorScheme.primary,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = stringResource(R.string.history_clearHistory_dialog_title),
                textAlign = TextAlign.Center,
                style = typography.titleMedium,
            )
        },
        text = {
            Text(
                text = stringResource(R.string.history_clearHistory_dialog_body),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm.invoke()
                    onDismiss.invoke()
                },
            ) {
                Text(text = stringResource(R.string.history_clearHistory_dialog_confirmButton))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)
            ) {
                Text(text = stringResource(R.string.history_clearHistory_dialog_dismissButton))
            }
        }
    )
}