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

package tech.danielwaiguru.notebook.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MigrationV3: Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
                CREATE TABLE IF NOT EXISTS notes_temp 
                (note_id INTEGER NOT NULL, note_title TEXT NOT NULL, note_text TEXT NOT NULL, 
                created_at INTEGER NOT NULL, PRIMARY KEY(note_id))
            """.trimIndent()
        )
        database.execSQL(
            """
                INSERT INTO notes_temp (note_id, note_title, note_text, created_at) 
                SELECT noteId, noteTitle, noteText, createdAt FROM notes
            """.trimIndent()
        )
        database.execSQL("DROP TABLE notes")
        database.execSQL("ALTER TABLE notes_temp RENAME TO notes")
    }
}