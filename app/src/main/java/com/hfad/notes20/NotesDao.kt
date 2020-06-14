package com.hfad.notes20

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY date ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id=:noteId")
    fun getNote(noteId: String): LiveData<Note>

    @Insert
    fun insertNote(note:Note)

   @Update
    fun update(note: Note)

    @Delete
    fun deleteNote(note:Note)

    @Query("DELETE FROM notes")
    fun deleteAllNotes()

}