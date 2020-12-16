package tech.danielwaiguru.notebook.database

import org.koin.core.KoinComponent
import org.koin.core.inject

class NoteRepository : KoinComponent{
    private val noteDao: NoteDao by inject()
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