package com.mshdabiola.ui.image

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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    modifier: Modifier = Modifier,
    currentIndex: Int = 0,
    data: ImmutableList<String> = emptyList<String>().toImmutableList(),
    onDataChange: (Int) -> Unit = {},
    label: String? = null,
    enabled: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = modifier.menuAnchor(),
            readOnly = true,
            value = data.getOrNull(if (currentIndex < 0) 0 else currentIndex) ?: "",
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            label = if (label == null) {
                null
            } else {
                {
                    Text(label)
                }
            },
            enabled = enabled,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            data.forEachIndexed { index, s ->
                DropdownMenuItem(
                    text = { Text(s) },
                    onClick = {
                        onDataChange(index)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
