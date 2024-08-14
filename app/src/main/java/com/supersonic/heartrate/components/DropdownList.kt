package com.supersonic.heartrate.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.supersonic.heartrate.ui.theme.HeartRateTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownList(
    modifier: Modifier = Modifier,
    itemList: List<Int>,
    selectedItem: String,
    onItemClick:(Int) -> Unit = {},
) {

    var showDropdown by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                modifier = modifier,
                expanded = showDropdown,
                onExpandedChange = { showDropdown = it },
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    value = selectedItem,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDropdown)},
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
//                        focusedContainerColor = colorScheme.secondaryContainer.copy(alpha = .75F),
//                        unfocusedContainerColor = colorScheme.secondaryContainer.copy(alpha = .75F),

                    ),
                )

                ExposedDropdownMenu(
                    expanded = showDropdown, onDismissRequest = { showDropdown = false }) {
                    itemList.forEach {item ->
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = item)) },
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
                selectedItem = selectedItem.toString(),
                onItemClick = {selectedItem = it}
            )

        }
    }
}