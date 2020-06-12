package com.hfad.notes20

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private var repository: NotesRepository = NotesRepository(application)

    private var allNotes: LiveData<List<Note>> = repository.getAllNotes()

    fun insert(note: Note){
        repository.insert(note)
    }

    fun deleteAllNotes(){
        repository.deleteAllNotes()
    }

    fun deleteNote(note: Note){
        repository.deleteNote(note)
    }

    fun getAllNotes(): LiveData<List<Note>> = repository.getAllNotes()
}