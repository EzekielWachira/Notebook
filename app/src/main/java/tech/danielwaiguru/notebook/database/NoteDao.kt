package tech.danielwaiguru.notebook.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    /**
     * function to insert a note
     */
    @Insert
    suspend fun insertNote(note: Note)
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
    fun getAllNotes(): LiveData<List<Note>>
}