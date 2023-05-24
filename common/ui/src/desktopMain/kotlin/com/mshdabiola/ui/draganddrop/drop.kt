package com.mshdabiola.ui.draganddrop

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.image.AsyncImage
import com.mshdabiola.ui.image.DesktopImage
import com.mshdabiola.ui.image.loadImageBitmap
import com.mshdabiola.ui.image.loadSvgPainter
import com.mshdabiola.ui.image.loadXmlImageVector
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetDragEvent
import java.awt.dnd.DropTargetDropEvent
import java.awt.dnd.DropTargetEvent
import java.awt.dnd.DropTargetListener
import java.io.File
import javax.swing.JLabel



@Composable
fun MyDrag(modifier: Modifier) {
    var text by remember { mutableStateOf("set icon") }

    var isover by remember { mutableStateOf(false) }
    var path by remember { mutableStateOf<String?>(null) }

    val listener = object : DropTargetListener {
        override fun dragEnter(p0: DropTargetDragEvent?) {
            println("enter")
            text = "enter"
        }

        override fun dragOver(p0: DropTargetDragEvent?) {
            println("Over")
            isover = true
            text = "OVer"
        }

        override fun dropActionChanged(p0: DropTargetDragEvent) {
            println("drag action change")
            text = "drag change"
        }

        override fun dragExit(p0: DropTargetEvent?) {
            println("drag exit ")
            text = "drag exit"
            isover = false
        }

        override fun drop(event: DropTargetDropEvent) {
            event.acceptDrop(DnDConstants.ACTION_COPY)
            val transferable = event.transferable


            transferable.transferDataFlavors
                .filter {

                    it.isFlavorJavaFileListType
                }
                .mapNotNull {

                    transferable.getTransferData(it) as? List<*>

                }
                .flatten()
                .mapNotNull {
                    it as? File
                }
                .forEach {

                    path=it.path

                    println(it.path)
                }
            text = "set image"
            isover = false
            event.dropComplete(true)
        }

    }


    SwingPanel(
        background = Color.Transparent,
        modifier = modifier,
        factory = {
            ComposePanel().apply {
                setSize(200, 200)
                setContent {
                    Card(
                        Modifier.fillMaxSize(),
                        border = if (isover) BorderStroke(
                            width = 4.dp,
                            color = Color.Blue
                        ) else BorderStroke(width = 2.dp, color = Color.Black)
                    ) {
                        if (path!=null){
                          DesktopImage(
                              modifier.fillMaxSize(),
                              path = path!!,
                              contentDescription = ""
                          )
                        }else{
                            Text(text = text)
                        }

                    }

                }
                DropTarget(this, listener)
            }
        })


}


@Composable
fun DragAndDropImage(
    modifier: Modifier,
     path : String,
    onPathChange:(String)->Unit={}
) {


    var isover by remember { mutableStateOf(false) }

    val listener = object : DropTargetListener {
        override fun dragEnter(p0: DropTargetDragEvent?) {
            println("enter")
        }

        override fun dragOver(p0: DropTargetDragEvent?) {
            println("Over")
            isover = true
        }

        override fun dropActionChanged(p0: DropTargetDragEvent) {
            println("drag action change")
        }

        override fun dragExit(p0: DropTargetEvent?) {
            println("drag exit ")
            isover = false
        }

        override fun drop(event: DropTargetDropEvent) {
            event.acceptDrop(DnDConstants.ACTION_COPY)
            val transferable = event.transferable


           val file= transferable.transferDataFlavors
                .filter {

                    it.isFlavorJavaFileListType
                }
                .mapNotNull {

                    transferable.getTransferData(it) as? List<*>

                }
                .flatten()
                .firstNotNullOf {
                    it as? File
                }
           onPathChange(file.path)
            isover = false
            event.dropComplete(true)
        }

    }


    SwingPanel(
        background = Color.Transparent,
        modifier = modifier,
        factory = {
            ComposePanel().apply {
                setSize(200, 200)
                setContent {
                    Card(
                        Modifier.fillMaxSize(),
                        border = if (isover) BorderStroke(
                            width = 4.dp,
                            color = Color.Blue
                        ) else BorderStroke(width = 2.dp, color = Color.Black)
                    ) {
                        Box (Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            if (File(path).exists()){
                                DesktopImage(
                                    modifier.fillMaxSize(),
                                    path = path,
                                    contentDescription = ""
                                )
                            }else{
                                Text(text = "drag image here")
                            }
                        }


                    }

                }
                DropTarget(this, listener)
            }
        })


}

