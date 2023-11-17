package com.mshdabiola.series

import androidx.compose.runtime.Composable


@Composable
expect fun CommonScreen(
   firstScreen :    @Composable  ()->Unit,
    secondScreen :   @Composable  ()->Unit,
    )