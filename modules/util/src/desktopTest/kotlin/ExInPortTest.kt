import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.Type
import com.mshdabiola.util.ExInPort
import com.mshdabiola.util.FileManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExInPortTest {

    @Test
    fun exportExam() = runTest {
        val exInPort = ExInPort(FileManager())

        exInPort.export(
            listOf(
                Question(
                    id = 4530L,
                    nos = 2099L,
                    examId = 3679L,
                    content = listOf(
                        Item(content = "Kasey", type = Type.TEXT),
                        Item(content = "shbfsfs", type = Type.TEXT),
                        Item(content = "sfshsfsh ssfsj", type = Type.TEXT)
                    ),
                    isTheory = true,
                    answer =null,
                    instructionId = 344,
                    topicId = 44
                ),
                Question(
                    id = 4530L,
                    nos = 2099L,
                    examId = 3679L,
                    content = listOf(
                        Item(content = "Kasey", type = Type.TEXT),
                        Item(content = "shbfsfs", type = Type.TEXT),
                        Item(content = "sfshsfsh ssfsj", type = Type.TEXT)
                    ),
                    isTheory = true,
                    answer = null,
                    instructionId = 344,
                    topicId = 44
                ),

                ), "/Users/user/AndroidStudioProjects/Series", ExInPort.question
        )
    }

    @Test
    fun importExam() = runTest {
        val exInPort = ExInPort(FileManager())
        val list =
            exInPort.import<Option>("/Users/user/AndroidStudioProjects/Series", ExInPort.option)

        println(list)
    }


}