package com.mshdabiola.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.state.ItemUiState
import kotlinx.collections.immutable.ImmutableList


@Composable
fun ItemUi(items: ImmutableList<ItemUiState>, generalPath: String) {

    items.forEach { item ->
        when (item.type) {
            Type.EQUATION -> {
                EquationUi(Modifier.fillMaxWidth(), equation = item)
            }

            Type.TEXT -> {
                MarkUpText(item.content, style = MaterialTheme.typography.bodyLarge)
            }

            Type.IMAGE -> {
                ImageUi(
                    Modifier.fillMaxWidth().heightIn(60.dp, 200.dp),
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
