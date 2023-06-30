package com.mshdabiola.worker.work

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.pm.PackageInfoCompat
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
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.model.data.Subject
import com.mshdabiola.model.data.Topic
import com.mshdabiola.util.ExInPort
import com.mshdabiola.worker.util.prefsName
import com.mshdabiola.worker.util.versionKey
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

internal const val ID = "id"

class SaveWorker(
    private val appContext: Context,
    private val workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {


    private val exInPort by inject<ExInPort>()
    private val questionRepository by inject<IQuestionRepository>()
    private val iExamRepository by inject<IExamRepository>()
    private val iSubjectRepository by inject<ISubjectRepository>()
    private val iInstructionRepository by inject<IInstructionRepository>()
    private val iTopicRepository by inject<ITopicRepository>()
    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.syncForegroundInfo()


    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {




        //delete all data
        launch {
            iSubjectRepository.deleteAll()
            iExamRepository.deleteAll()
            iInstructionRepository.deleteAll()
            iTopicRepository.deleteAll()
            questionRepository.deleteAll()
        }

//
        Timber.e("worker id" + workerParams.inputData.getLong(ID, -1L))

        val manager = appContext.assets!!


//
        val subjects: Deferred<List<Subject>> =
            async { exInPort.import(manager.open("${ExInPort.subject}.ex")) }
        val exams: Deferred<List<Exam>> =
            async { exInPort.import(manager.open("${ExInPort.exam}.ex")) }

        val questions: Deferred<List<QuestionFull>> =
            async { exInPort.import(manager.open("${ExInPort.question}.ex")) }

        val instructions: Deferred<List<Instruction>> =
            async { exInPort.import(manager.open("${ExInPort.instruction}.ex")) }
        val topics: Deferred<List<Topic>> =
            async { exInPort.import(manager.open("${ExInPort.topic}.ex")) }




        iSubjectRepository.insertAll(subjects.await())
        iInstructionRepository.insertAll(instructions.await())
        iTopicRepository.insertAll(topics.await())
        iExamRepository.insertAll(exams.await())
        questionRepository.insertAll(questions.await())

//
//
        val info = appContext.packageManager.getPackageInfo(appContext.packageName, 0)
        val version = PackageInfoCompat.getLongVersionCode(info)
        appContext.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .edit()
            .putLong(versionKey, version)
            .apply()

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

