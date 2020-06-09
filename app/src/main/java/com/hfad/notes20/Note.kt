package com.hfad.notes20

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(val title: String,
           val description: String,
                val date: String,
           val priority: Int){

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}