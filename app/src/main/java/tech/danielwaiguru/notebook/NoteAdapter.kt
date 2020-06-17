package tech.danielwaiguru.notebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tech.danielwaiguru.notebook.database.Note

class NoteAdapter(): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes = emptyList<Note>() //Cached copy of words
    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewTitle: TextView = itemView.findViewById<TextView>(R.id.textViewTitle)
        val textViewNoteText: TextView = itemView.findViewById<TextView>(R.id.textViewNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item_view_holder, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.textViewTitle.text = note.noteTitle
        holder.textViewNoteText.text = note.noteText
    }
    internal fun setNotes(notes: List<Note>){
        this.notes = notes
    }
}