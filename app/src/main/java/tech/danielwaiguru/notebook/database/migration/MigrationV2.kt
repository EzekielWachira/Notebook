/*
 *   Copyright 2020 Daniel Waiguru
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package tech.danielwaiguru.notebook.database.migration

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.text.SimpleDateFormat
import java.util.*

class MigrationV2: Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database
            .execSQL("CREATE TABLE notes_new (noteId INTEGER, noteTitle TEXT, noteText TEXT, createdAt INTEGER, PRIMARY KEY(noteId))")
        //Copy all the data
        /*database.execSQL(
            "INSERT INTO notes_database_new (note_id, note_title, note_text, created_at) SELECT noteId, noteTitle, noteText, createdAt FROM $DB_NAME"
        )*/
        val cursor = database.query("SELECT * FROM notes")
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val values = ContentValues()
                values.put("noteId", cursor.getLongOrEmpty("noteId"))
                values.put("noteTitle", cursor.getStringOrEmpty("noteTitle"))
                values.put("noteText", cursor.getStringOrEmpty("noteText"))
                val createdOnStr = cursor.getStringOrEmpty("createdAt")
                values.put("createdAt", getDateInMillis(createdOnStr))
                database.insert("notes_new", SQLiteDatabase.CONFLICT_REPLACE,values)
            }
            cursor.close()
        }
        //Remove old table
        database.execSQL("DROP TABLE notes")
        //Rename the new table
        database.execSQL("ALTER TABLE notes_new RENAME TO notes")
    }
    private fun getDateInMillis(dateString: String?): Long {
        return if (dateString == null || dateString == "") {
            0
        } else {
            try {
                val formatter = SimpleDateFormat("d MMM, yyyy HH:mm a", Locale.getDefault())
                val rawDate = formatter.parse(dateString)
                rawDate?.time ?: 0
            } catch (e: Exception) {
                e.printStackTrace()
                0
            }
        }
    }
    private fun Cursor?.getStringOrEmpty(columnName: String): String {
        val columnIndex = this?.getColumnIndex(columnName) ?: -1
        return if (columnIndex > -1) {
            this?.getString(columnIndex) ?: ""
        } else {
            ""
        }
    }
    private fun Cursor?.getLongOrEmpty(columnName: String): Int {
        val columnIndex = this?.getColumnIndex(columnName) ?: -1
        return if (columnIndex > -1) {
            this?.getInt(columnIndex) ?: 0
        } else {
            0
        }
    }

}