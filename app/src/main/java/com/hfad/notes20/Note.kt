package com.hfad.notes20

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes")
data class Note(
    var title: String,
    var description: String,
    var date: String,
    var priority: Int){

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}