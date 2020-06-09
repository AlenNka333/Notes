package com.hfad.notes20

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY date")
    fun getAllNotes():List<Note>

    @Insert
    fun insertNote(note:Note)

    @Delete
    fun deleteNote(note:Note)

    @Query("DELETE FROM notes")
    fun deleteAllNotes()

}