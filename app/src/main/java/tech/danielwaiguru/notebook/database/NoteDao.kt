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

package tech.danielwaiguru.notebook.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tech.danielwaiguru.notebook.model.Note

@Dao
interface NoteDao {
    /**
     * function to insert a note
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long
    /**
     * function to update a note
     */
    @Update
    suspend fun updateNote(note: Note)
    /**
     * function to delete a note
     */
    @Delete
    suspend fun deleteNote(note: Note)
    /**
     * function to retrieve all notes from the database
     */
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<Note>>
}