package tech.danielwaiguru.notebook.database

import tech.danielwaiguru.notebook.model.Note

class NoteRepository(private val noteDao: NoteDao){
    fun getAllNotes() = noteDao.getAllNotes()
    /**
     * insert a note
     */
    suspend fun insertNote(note: Note){
        noteDao.insertNote(note)
    }
    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }
    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }
}