package com.hfad.notes20

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDBHelper(
    context: Context?
) : SQLiteOpenHelper(context, "notes.db", null, 1) {

    val DB_NAME = "notes.db"
    val DB_VERSION = 2

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(NotesContract.NotesEntry.CREATE_COMMAND)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL(NotesContract.NotesEntry.DROP_COMMAND)
        }
        onCreate(db)
    }
}