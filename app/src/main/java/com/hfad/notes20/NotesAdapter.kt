package com.hfad.notes20

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView


class NotesAdapter(context: Context): RecyclerView.Adapter<NotesAdapter.NotesHolder>() {

    private var notes: List<Note> = ArrayList()
    private var parent = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val view: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_notes, parent, false)
        return NotesHolder(view)
    }

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        val note = notes[position]
        holder.textViewTitle.setText(note.title)
        holder.textViewDescription.text = note.description
        holder.textViewDayOfWeek.text = (note.date)
        holder.textViewTitle.setBackgroundColor(note.priority)

        holder.setActualPosition(position)
        holder.setListeners(position)

    }

    inner class NotesHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView
        val textViewDescription: TextView
        val textViewDayOfWeek: TextView

        private var nPosition: Int = 0

        init {
            textViewTitle = itemView.findViewById(R.id.textViewTitle)
            textViewDescription = itemView.findViewById(R.id.textViewDescription)
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDate)

        }

        fun setActualPosition(position :Int){
            nPosition = position
        }

        fun setListeners(position: Int){
            itemView.setOnClickListener(object: View.OnClickListener{
                override fun onClick(v: View?) {
                    val intent = Intent(parent, ChangeNoteActivity::class.java)
                    intent.putExtra("itemId", notes[nPosition].id)
                    (parent as Activity).startActivityForResult(intent, MainActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE)
                }
            })
        }
    }


}