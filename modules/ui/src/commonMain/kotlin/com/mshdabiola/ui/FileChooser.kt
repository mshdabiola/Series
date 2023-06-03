package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import java.io.File
import javax.swing.JFileChooser

@Composable
fun DirtoryUi(
    show:Boolean,
    onDismiss : ()->Unit={},
    onFile:(File?)->Unit={}
) {

    LaunchedEffect(show){
        if (show){
            val jFileChooser= JFileChooser()
            jFileChooser.dragEnabled=true
            jFileChooser.fileSelectionMode=JFileChooser.DIRECTORIES_ONLY
           val ret= jFileChooser.showSaveDialog(null)
            if (ret==JFileChooser.APPROVE_OPTION){
                val file=jFileChooser.selectedFile
                onFile(file)
            }
            onDismiss()

        }
    }

}