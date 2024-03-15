package com.mshdabiola.model

private val testingPath = "/Users/user/AndroidStudioProjects/Series/subject"
private val defaultPath = System.getProperty("java.io.tmpdir")
val dataPath = "${System.getProperty("user.home")}/AppData/Local/Series"

//    get() {
//        val homePath=System.getProperty("user.home")
//        return "${System.getProperty("user.home")}/AppData/Local"
// //      return when(osInt){
// //           1->""
// //           2->"/Users/user/Series/data"
// //           else->System.getProperty("java.io.tmpdir")
// //       }
//    }
actual val generalPath: String
    get() = dataPath

// 1 window 2 mac 3 other
// val osInt:Int
//    get() {
//        val os= System.getProperty("os.name")
//        return when{
//            os.startsWith("Windows")->1
//            os.contains("OS X")->2
//            else -> 3
//        }
//    }
