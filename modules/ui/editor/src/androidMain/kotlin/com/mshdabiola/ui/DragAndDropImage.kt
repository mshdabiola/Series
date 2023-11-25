package com.mshdabiola.ui

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
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
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader

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
//    val imageLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia(),
//        onResult = { uri ->
//            uri?.path?.let {
//                val time = System.currentTimeMillis()
//                val extension = getFileMimeType(uri, context)
//                Timber.e("extention $extension")
//                val path2= File.createTempFile("abiola","ima.${extension?:"jpg"}")
//                val outputStream = FileOutputStream(path2)
//
//                context.contentResolver.openInputStream(uri).use {
//                    it?.copyTo(outputStream)
//                    outputStream.close()
//                }
//                onPathChange(path2.path)
//                //saveImage(it, time)
//            }
//        },
//    )
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.path?.let {
                val time = System.currentTimeMillis()
                val extension = getFileMimeType(uri, context)
                Timber.e("extention $extension")
                val path2= File.createTempFile("abiola","ima.${extension?:"jpg"}")
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
               arrayOf("image/*")
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

fun getExtension(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val line = bufferedReader.readLine()
    bufferedReader.close()
    inputStream?.close()
    return line?.substring(line.lastIndexOf(".") + 1)
}

 fun getFileMimeType(uri: Uri,context: Context): String? {
    return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
        val mime = MimeTypeMap.getSingleton()
        mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
    } else {
        MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
    }
}


