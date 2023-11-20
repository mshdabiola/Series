package com.mshdabiola.series

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonScreen(
    windowSizeClass: WindowSizeClass,
    firstScreen :    @Composable  ()->Unit,
    secondScreen :   @Composable  ()->Unit,
) {
    if (windowSizeClass.widthSizeClass<= WindowWidthSizeClass.Medium){

        BottomSheetScaffold(
            sheetPeekHeight = 46.dp,
            sheetContent = {secondScreen()}
        ){
            firstScreen()
        }

    }else{
        Row(modifier = Modifier.fillMaxSize()) {
            Column (Modifier.weight(0.55f)){
                firstScreen()
            }
            Column (Modifier.weight(0.45f)){
                secondScreen()
            }
        }
    }
}