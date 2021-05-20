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

import kotlinx.coroutines.flow.Flow
import tech.danielwaiguru.notebook.domain.NoteRepository
import tech.danielwaiguru.notebook.model.Note

class NoteRepositoryImpl(private val noteDao: NoteDao): NoteRepository{
    /**
     * insert a note
     */
    override suspend fun insertNote(note: Note){
        noteDao.insertNote(note)
    }
    override suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }
    override suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

    override fun getAllNotes(searchQuery: String): Flow<List<Note>> {
        return noteDao.getAllNotes(searchQuery)
    }
}