package com.hfad.notes20

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    companion object {
        lateinit var adapter: NotesAdapter
        lateinit var noteViewModel: NoteViewModel
        val UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = this.findViewById(R.id.recyclerView)
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this.applicationContext)
        adapter = NotesAdapter(this)
        recyclerView.adapter = adapter

        getData()

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
               return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                remove(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(recyclerView)

    }

    fun remove(position: Int) {
        val note = noteViewModel.getAllNotes()
        note.value?.get(position)?.let { noteViewModel.deleteNote(it) }
    }

    fun addNote(view: View) {
        val intent = Intent(applicationContext, NewNote::class.java)
        startActivity(intent)
    }

    fun getData(){
        noteViewModel.getAllNotes().observe(this,
            Observer<List<Note>> { t ->
                adapter.setNotes(t!!)
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val newNote = data?.getStringExtra("noteTitle")?.let {
                Note(
                    it,
                    data.getStringExtra("noteDescription"),
                    data.getStringExtra("noteDate"),
                    data.getIntExtra("noteColor", 0)
                    )
            }
            newNote?.id = data?.getStringExtra("noteId")?.toInt()!!
            if (newNote != null) {
                noteViewModel.update(newNote)
            }
        }
    }
}