package com.hfad.notes20

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter: RecyclerView.Adapter<NotesAdapter.NotesHolder>() {

    private var notes: List<Note> = ArrayList()

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

    }

    class NotesHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView
        val textViewDescription: TextView
        val textViewDayOfWeek: TextView

        init {
            textViewTitle = itemView.findViewById(R.id.textViewTitle)
            textViewDescription = itemView.findViewById(R.id.textViewDescription)
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek)
            itemView.setOnClickListener(object: View.OnClickListener{
                override fun onClick(v: View?) {

                }

            })
        }
    }
}