package com.hfad.notes20

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.*

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {

    companion object {
        var database: NotesDatabase? = null
        val DB_NAME = "notes1.db"
        val LOCK = Object()


        fun getInstance(context: Context): NotesDatabase?{
            if (database == null) {
                synchronized(LOCK) {
                    database = Room.databaseBuilder(context, NotesDatabase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return database
        }
    }

    abstract fun notesDao(): NotesDao
}