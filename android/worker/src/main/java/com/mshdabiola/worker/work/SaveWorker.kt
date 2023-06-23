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
                content = listOf(Item("What is your name")),
                isTheory = false,
                answer = "",
                options = options,
                instruction =Instruction(91, 1, "Title", listOf(Item("Content"))),
                topic = null
            )
        }

        val instructions: List<Instruction> = listOf(
            Instruction(91, 1, "Title", listOf(Item("Content")))
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

