package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.LessonTopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonTopicDao {
    @Upsert
    suspend fun upsert(topic: LessonTopicEntity): Long

    @Upsert
    suspend fun upsertAll(topics: List<LessonTopicEntity>)

    @Delete
    suspend fun delete(topic: LessonTopicEntity)

    @Query("DELETE FROM lesson_topics")
    suspend fun deleteAllLessonTopics()

    @Query("SELECT * FROM lesson_topics WHERE topic_id = :topicId")
    fun getLessonTopicById(topicId: Int): Flow<LessonTopicEntity?>

    @Query("SELECT * FROM lesson_topics")
    fun getAllLessonTopics(): Flow<List<LessonTopicEntity>>

    @Query("SELECT * FROM lesson_topics WHERE course_id_fk = :courseId")
    fun getLessonTopicsByCourseId(courseId: Int): Flow<List<LessonTopicEntity>>
}
