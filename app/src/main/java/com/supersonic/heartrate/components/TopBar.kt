package com.supersonic.heartrate.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.supersonic.heartrate.R
import com.supersonic.heartrate.ui.theme.HeartRateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    onBackClick: () -> Unit = {},
    isBackIconEnabled: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            if (title != null){
                Text(
                    text = title,
                    style = typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colorScheme.onPrimary
                    )
            }
        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(colorScheme.primary),
        navigationIcon = {
            if (isBackIconEnabled){
                IconButton(
                    onClick = onBackClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null)
                }
            }
        },
        actions = actions
    )
}

@Preview
@Composable
private fun TobBarPreview() {
    HeartRateTheme {
        TopBar(
            title = "Історія",
            isBackIconEnabled = true,
            actions = {
                Text(
                    text = "Історія",
                    style = typography.titleSmall,
                    color = colorScheme.onPrimary
                    )
                IconButton(
                    onClick = {},
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = colorScheme.onPrimary
                    )
                ) {
                    Icon(painter = painterResource(id = R.drawable.icon_time_machine), contentDescription = null)
                }
            }
        )
    }
}