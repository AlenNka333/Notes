package com.hfad.notes20

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    companion object {
        var notes: ArrayList<Note> = ArrayList()
        lateinit var database: NotesDatabase
        lateinit var adapter: NotesAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerViewNotes)
        database = NotesDatabase.getInstance(this)!!

        getData()

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NotesAdapter(notes)
        recyclerView.adapter = adapter

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
        val note = notes.get(position)
        database.notesDao().deleteNote(note)
        getData()
        adapter.notifyDataSetChanged()
    }

    fun addNote(view: View) {
        val intent = Intent(applicationContext, NewNote::class.java)
        startActivity(intent)
    }

    fun getData(){
        val notesDB = database.notesDao().getAllNotes()
        notes.clear()
        notes.addAll(notesDB)
    }
}