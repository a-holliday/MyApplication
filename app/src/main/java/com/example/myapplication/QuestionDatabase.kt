package com.example.myapplication
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Question::class], version = 1)
abstract class QuestionDatabase : RoomDatabase() {
    abstract fun QuestionDAO(): QuestionDAO

    companion object {
        @Volatile
        private var Instance: QuestionDatabase? = null

        fun getDatabase(context: Context): QuestionDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, QuestionDatabase::class.java, "question_database")
                    .build()
                    .also { Instance = it }
            }

        }
    }
}