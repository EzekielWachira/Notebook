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