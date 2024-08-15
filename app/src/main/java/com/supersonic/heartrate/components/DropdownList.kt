package com.supersonic.heartrate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.supersonic.heartrate.ui.theme.HeartRateTheme
import com.supersonic.heartrate.util.identifyAccuracyColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownList(
    modifier: Modifier = Modifier,
    itemList: List<Int>,
    selectedItemResource: Int,
    onItemClick:(Int) -> Unit = {},
) {

    var showDropdown by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                modifier = modifier,
                expanded = showDropdown,
                onExpandedChange = { showDropdown = it },
            ) {
                TextField(
                    modifier = modifier.menuAnchor(),
                    value = stringResource(selectedItemResource),
                    textStyle = typography.displaySmall,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    leadingIcon = {
                        Box(modifier = Modifier
                            .size(12.dp)
                            .background(identifyAccuracyColor(selectedItemResource), CircleShape)
                        )
                    },
                    trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDropdown)},
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )

                ExposedDropdownMenu(
                    expanded = showDropdown, onDismissRequest = { showDropdown = false }) {
                    itemList.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = item),
                                    style = typography.displaySmall
                                )
                                   },
                            leadingIcon = {
                                Box(modifier = Modifier
                                    .size(12.dp)
                                    .background(identifyAccuracyColor(item), CircleShape)
                                )
                            },
                            onClick = {
                                onItemClick(item)
                                showDropdown = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )

                    }
                }



                }
    }


@Preview
@Composable
private fun DropdownListPreview() {

    val map = mapOf(
        Pair("Низька", 10),
        Pair("Середня", 20),
        Pair("Висока", 30),
    )

    val keysList = map.keys.toList()

    var selectedItem by remember {
        mutableStateOf(0)
    }

    HeartRateTheme {
        Column {
            DropdownList(
                itemList = listOf(),
                selectedItemResource = selectedItem,
                onItemClick = {selectedItem = it}
            )

        }
    }
}