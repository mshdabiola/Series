package com.mshdabiola.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.state.ItemUiState
import kotlinx.collections.immutable.ImmutableList


@Composable
fun ItemUi(items: ImmutableList<ItemUiState>, generalPath: String) {

    items.forEach { item ->
        when (item.type) {
            Type.EQUATION -> {
                EquationUi(equation = item)
            }

            Type.TEXT -> {
                Text(item.content, style = MaterialTheme.typography.bodyLarge)
            }

            Type.IMAGE -> {
                ImageUi(
                    path = "$generalPath/${item.content}",
                    contentDescription = item.content
                )
            }
        }
    }


}


@Composable
internal expect fun EquationUi(
    modifier: Modifier = Modifier,
    equation: ItemUiState
)


@Composable
expect fun DragAndDropImage(
    modifier: Modifier,
    path: String,
    onPathChange: (String) -> Unit = {}
)

@Composable
expect fun ImageUi(
    modifier: Modifier = Modifier,
    path: String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit
)
