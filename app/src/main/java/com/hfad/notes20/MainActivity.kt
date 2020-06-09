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
        lateinit var database: SQLiteDatabase
        lateinit var adapter: NotesAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewNotes)
        val dbHelper = NotesDBHelper(this)
        database = dbHelper.writableDatabase
        //database.delete(NotesContract.NotesEntry.TABLE_NAME, null, null)
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
        val id = notes.get(position).id
        val where = "${NotesContract.NotesEntry._ID} = ?"
        var whereArgs: Array<String> = arrayOf(id.toString())
        database.delete(NotesContract.NotesEntry.TABLE_NAME, where, whereArgs)
        getData()
        adapter.notifyDataSetChanged()
    }

    fun addNote(view: View) {
        val intent = Intent(applicationContext, NewNote::class.java)
        startActivity(intent)
    }

    fun getData(){
        notes.clear()
        val cursor: Cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, null, null, null, null, NotesContract.NotesEntry.COLUMN_DATE)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID))
            val title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION))
            val date = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DATE))
            val priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY))
            val n = Note(id, title, description, date, priority)
            notes.add(n)
        }
        cursor.close()
    }
}