package com.mshdabiola.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun DragAndDropImage(
    modifier: Modifier,
    path: String,
    onPathChange: (String) -> Unit,
) {

    val context= LocalContext.current

    val isover by remember { mutableStateOf(false) }

//    val activityResultLauncher=ActivityResultLauncher?
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.path?.let {
                val time = System.currentTimeMillis()
                val path2= File.createTempFile("abiola","ima.jpg")
                val outputStream = FileOutputStream(path2)

                context.contentResolver.openInputStream(uri).use {
                    it?.copyTo(outputStream)
                    outputStream.close()
                }
                onPathChange(path2.path)
                //saveImage(it, time)
            }
        },
    )



    Card(
        onClick = {
            imageLauncher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
                ),
            )
        },
        Modifier.size(100.dp),
        border = if (isover) BorderStroke(
            width = 4.dp,
            color = Color.Blue
        ) else BorderStroke(width = 2.dp, color = Color.Black)

    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            //  if (File(path).exists()) {
            ImageUi(
                modifier.fillMaxSize(),
                path = path,
                contentDescription = ""
            )
//                            } else {
//                                Text(text = "drag image here")
//                            }
       }


    }


}
