package tech.danielwaiguru.notebook.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note (
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0,
    var noteTitle: String,
    var noteText: String,
    var createdAt: String
): Parcelable