package com.hfad.notes20

import android.provider.BaseColumns
import android.provider.BaseColumns._ID

class NotesContract {

    class NotesEntry: BaseColumns{

        companion object {
            val _ID = BaseColumns._ID
        val TABLE_NAME = "notes"
        val COLUMN_TITLE = "title"
        val COLUMN_DESCRIPTION = "description"
        val COLUMN_DATE = "date"
        val COLUMN_PRIORITY = "priority"

        val TYPE_TEXT = "TEXT"
        val TYPE_INTEGER = "INTEGER"


            val CREATE_COMMAND =
                "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (${_ID} ${TYPE_INTEGER} PRIMARY KEY AUTOINCREMENT, " +
                        "${COLUMN_TITLE} ${TYPE_TEXT}, ${COLUMN_DESCRIPTION} ${TYPE_TEXT}, " +
                        "${COLUMN_DATE} ${TYPE_TEXT}, ${COLUMN_PRIORITY} ${TYPE_INTEGER})"

            val DROP_COMMAND = "DROP TABLE IF EXISTS ${TABLE_NAME}"
        }

    }
}