package com.example.myapplication
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface QuestionDAO {
    @Query("SELECT * FROM questions")
   suspend fun getAllQuestions(): List<Question>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestion(question : Question)

    @Delete
    suspend fun deleteQuestion( question : Question)

    @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT 1")
    suspend fun randomQuestion() : Question
}
