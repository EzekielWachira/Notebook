package tech.danielwaiguru.notebook.database

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes = noteDao.getAllNotes()
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