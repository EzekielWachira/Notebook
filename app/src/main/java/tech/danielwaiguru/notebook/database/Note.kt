package tech.danielwaiguru.notebook.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val noteId: Int,
    val noteTitle: String?,
    val noteText: String
)