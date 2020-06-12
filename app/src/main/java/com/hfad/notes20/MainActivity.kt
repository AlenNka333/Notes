package com.hfad.notes20

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    companion object {
        var notes: ArrayList<Note> = ArrayList()
        lateinit var database: NotesDatabase
        lateinit var adapter: NotesAdapter
        lateinit var noteViewModel: NoteViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerViewNotes)

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NotesAdapter()
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
}