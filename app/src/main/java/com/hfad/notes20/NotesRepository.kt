package com.hfad.notes20

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class NotesRepository(application: Application) {

    private var notesDao: NotesDao
    private var allNotes: LiveData<List<Note>>

    init {
        val database: NotesDatabase = NotesDatabase.getInstance(
            application.applicationContext
        )!!
        notesDao = database.notesDao()
        allNotes = notesDao.getAllNotes()
    }

    fun insert(note: Note){
        val insertNoteAsyncTask = InsertNoteAsyncTask(notesDao).execute(note)
    }

    fun deleteAllNotes(){
        DeleteAllNotesAsyncTask(notesDao).execute()
    }

    fun deleteNote(note: Note){
        val deleteNoteAsyncTask = DeleteNoteAsyncTask(notesDao).execute(note)
    }

    fun getAllNotes(): LiveData<List<Note>> = allNotes

    inner class InsertNoteAsyncTask(notesDao: NotesDao): AsyncTask<Note, Unit, Unit>() {

        val notesDao = notesDao

        override fun doInBackground(vararg params: Note?) {
            notesDao.insertNote(params[0]!!)
        }

    }

    inner class DeleteNoteAsyncTask(notesDao: NotesDao): AsyncTask<Note, Unit, Unit>() {

        val notesDao = notesDao

        override fun doInBackground(vararg params: Note?) {
            notesDao.deleteNote(params[0]!!)
        }

    }

    inner class DeleteAllNotesAsyncTask(val notesDao: NotesDao): AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg params: Unit?) {
            notesDao.deleteAllNotes()
        }

    }
}