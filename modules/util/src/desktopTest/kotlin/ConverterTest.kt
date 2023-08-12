import com.mshdabiola.util.Converter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test


class ConverterTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun text() = runTest {

        val converter = Converter()
        val text = """
                 
                *q*sss
                *o*sss
                *o*sfs
                
                
                *o*ddkd
                *q*What is your name
                *o*abiola
                *o*moshood
                *o*lawal
            """.trimIndent()
        val text2 = """
            
                *q*sss
                *o*sss
                *o*sfs
                
                
                *o*ddkd
                *q*What is your name
                *o*abiola
                *o*moshood
                *o*lawal
            *q* what is your name
            *t* 1
            *a* abiola
        """.trimIndent()

        val text3 = """
            
            *q* what is your name
            *t* 1
            *a* abiola
        """.trimIndent()


        val qu = converter
            .textToQuestion(
                text2, 67, 78, 2
            )
        println(qu)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun text2() = runTest {

        val converter = Converter()

        val qu = converter
            .textToInstruction(
                """
                *aksks
                *sjsj
                *sjsj
            """.trimIndent(), 44
            )
        println(qu)

    }

}