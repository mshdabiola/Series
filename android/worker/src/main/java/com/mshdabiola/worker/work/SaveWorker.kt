package com.mshdabiola.worker.work

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.mshdabiola.data.repository.inter.IExamRepository
import com.mshdabiola.data.repository.inter.IInstructionRepository
import com.mshdabiola.data.repository.inter.IQuestionRepository
import com.mshdabiola.data.repository.inter.ISubjectRepository
import com.mshdabiola.data.repository.inter.ITopicRepository
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.model.data.Subject
import com.mshdabiola.model.data.Topic
import com.mshdabiola.model.data.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import kotlin.random.Random
import kotlin.random.nextLong

internal const val ID = "id"

class SaveWorker(
    private val appContext: Context,
    private val workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    private val questionRepository by inject<IQuestionRepository>()
    private val iExamRepository by inject<IExamRepository>()
    private val iSubjectRepository by inject<ISubjectRepository>()
    private val iInstructionRepository by inject<IInstructionRepository>()
    private val iTopicRepository by inject<ITopicRepository>()
    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.syncForegroundInfo()


    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        var latex = "\\begin{array}{l}"
        latex += "\\u000corall\\varepsilon\\in\\mathbb{R}_+^*\\ \\exists\\eta>0\\ |x-x_0|\\leq\\eta\\Longrightarrow|f(x)-f(x_0)|\\leq\\varepsilon\\\\"
        latex += "\\det\\begin{bmatrix}a_{11}&a_{12}&\\cdots&a_{1n}\\\\a_{21}&\\ddots&&\\vdots\\\\\\vdots&&\\ddots&\\vdots\\\\a_{n1}&\\cdots&\\cdots&a_{nn}\\end{bmatrix}\\overset{\\mathrm{def}}{=}\\sum_{\\sigma\\in\\mathfrak{S}_n}\\varepsilon(\\sigma)\\prod_{k=1}^n a_{k\\sigma(k)}\\\\"
        latex += "\\sideset{_\\alpha^\\beta}{_\\gamma^\\delta}{\\begin{pmatrix}a&b\\\\c&d\\end{pmatrix}}\\\\"
        latex += "\\int_0^\\infty{x^{2n} e^{-a x^2}\\,dx} = \\u000crac{2n-1}{2a} \\int_0^\\infty{x^{2(n-1)} e^{-a x^2}\\,dx} = \\u000crac{(2n-1)!!}{2^{n+1}} \\sqrt{\\u000crac{\\pi}{a^{2n+1}}}\\\\"
        latex += "\\int_a^b{f(x)\\,dx} = (b - a) \\sum\\limits_{n = 1}^\\infty  {\\sum\\limits_{m = 1}^{2^n  - 1} {\\left( { - 1} \\right)^{m + 1} } } 2^{ - n} f(a + m\\left( {b - a} \\right)2^{-n} )\\\\"
        latex += "\\int_{-\\pi}^{\\pi} \\sin(\\alpha x) \\sin^n(\\beta x) dx = \\textstyle{\\left \\{ \\begin{array}{cc} (-1)^{(n+1)/2} (-1)^m \\u000crac{2 \\pi}{2^n} \\binom{n}{m} & n \\mbox{ odd},\\ \\alpha = \\beta (2m-n) \\\\ 0 & \\mbox{otherwise} \\\\ \\end{array} \\right .}\\\\"
        latex += "L = \\int_a^b \\sqrt{ \\left|\\sum_{i,j=1}^ng_{ij}(\\gamma(t))\\left(\\u000crac{d}{dt}x^i\\circ\\gamma(t)\\right)\\left(\\u000crac{d}{dt}x^j\\circ\\gamma(t)\\right)\\right|}\\,dt\\\\"
        latex += "\\begin{array}{rl} s &= \\int_a^b\\left\\|\\u000crac{d}{dt}\\vec{r}\\,(u(t),v(t))\\right\\|\\,dt \\\\ &= \\int_a^b \\sqrt{u'(t)^2\\,\\vec{r}_u\\cdot\\vec{r}_u + 2u'(t)v'(t)\\, \\vec{r}_u\\cdot\\vec{r}_v+ v'(t)^2\\,\\vec{r}_v\\cdot\\vec{r}_v}\\,\\,\\, dt. \\end{array}\\\\"
        latex += "\\end{array}"
//
        Timber.e("worker id" + workerParams.inputData.getLong(ID, -1L))
//
        val subjects: List<Subject> = listOf(
            Subject(1, "English")
        )
        val exams: List<Exam> = listOf(
            Exam(1, 1, 2012)
        )

        val questions: List<QuestionFull> = (1L..90L).map { quID ->
            val ans = Random.nextLong(1..4L)
            val options = (1L..4L).map { index ->
                Option(
                    id = Random.nextLong(),
                    nos = index,
                    questionNos = quID,
                    examId = 1,
                    content = listOf(Item("Option$index")),
                    isAnswer = index == ans
                )
            }
            QuestionFull(
                id = quID,
                nos = quID,
                examId = 1,
                content = listOf(
                    Item("What is your name"),
                    Item(latex, Type.EQUATION),
                    Item("play.png", Type.IMAGE),
                    Item("yyy.svg", Type.IMAGE)
                ),
                isTheory = false,
                answer = "",
                options = options,
                instruction = if (Random.nextBoolean()) Instruction(
                    91,
                    1,
                    "Title",
                    listOf(Item("Content"))
                ) else null,
                topic = null
            )
        }

        val instructions: List<Instruction> = listOf(
            Instruction(
                91, 1, "Title", listOf(
                    Item("What is your name"),
                    Item(latex, Type.EQUATION),
                    Item("play.png", Type.IMAGE),
                    Item("yyy.svg", Type.IMAGE)
                )
            )
        )
        val topics: List<Topic> = listOf()

//        appContext.assets.open("file").use {
//            val string = it.reader()
//                .readText()
//            println(string)
//        }


        iSubjectRepository.insertAll(subjects)
        iInstructionRepository.insertAll(instructions)
        iTopicRepository.insertAll(topics)
        iExamRepository.insertAll(exams)
        questionRepository.insertAll(questions)

//
//
//        val info = appContext.packageManager.getPackageInfo(appContext.packageName, 0)
//        val version = PackageInfoCompat.getLongVersionCode(info)
//        appContext.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
//            .edit()
//            .putLong(versionKey, version)
//            .apply()

        Result.success()
    }


    companion object {
        fun startUpSaveWork(id: Long): OneTimeWorkRequest {
            val data = Data.Builder()
                .putLong(ID, id)
                .build()
            val saverConstraints = Constraints.Builder()
                .build()


            return OneTimeWorkRequestBuilder<SaveWorker>()
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(saverConstraints)
                .setInputData(data)
                .build()
        }
    }
}


private const val NotificationId = 0
private const val NotificationChannelID = "NotificationChannel"

fun Context.syncForegroundInfo() = ForegroundInfo(
    NotificationId,
    saveWorkNotification(),
)

private fun Context.saveWorkNotification(): Notification {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            NotificationChannelID,
            "game saver",
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = "for saving games"
        }
        // Register the channel with the system
        val notificationManager: NotificationManager? =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        notificationManager?.createNotificationChannel(channel)
    }

    return NotificationCompat.Builder(
        this,
        NotificationChannelID,
    )
        .setSmallIcon(
            android.R.drawable.ic_menu_save,
        )
        .setContentTitle("Saving current game")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
}

