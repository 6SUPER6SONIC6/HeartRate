package com.supersonic.heartrate.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScreenTemplate(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    content: @Composable (ColumnScope.() -> Unit)
) {
    Scaffold(
        topBar = topBar
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize(),
            content = content
        )

    }

}