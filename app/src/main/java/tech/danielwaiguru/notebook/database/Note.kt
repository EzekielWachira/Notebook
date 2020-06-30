package tech.danielwaiguru.notebook.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val noteId: Int,
    var noteTitle: String,
    var noteText: String,
    var createdAt: String
)