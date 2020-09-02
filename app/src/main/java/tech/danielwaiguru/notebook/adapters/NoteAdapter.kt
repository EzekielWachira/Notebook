package tech.danielwaiguru.notebook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.database.Note

class NoteAdapter(private val listener: (Note) -> Unit): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes = emptyList<Note>() //Cached copy of notes
    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewNoteText: TextView = itemView.findViewById(R.id.textViewNote)
        val textViewDate : TextView = itemView.findViewById(R.id.textViewDate)
        fun bind(note: Note){
            textViewTitle.text = note.noteTitle
            textViewNoteText.text = note.noteText
        }
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
        val note: Note = notes[position]
        holder.textViewTitle.text = note.noteTitle
        holder.textViewNoteText.text = note.noteText
        holder.textViewDate.text = note.createdAt
        //holder.bind(note)
        holder.itemView.setOnClickListener {
            listener(note)
        }
        /*holder.textViewDelete.setOnClickListener {
            val popup = PopupMenu(it.context, it)
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {menuItem ->
                return@OnMenuItemClickListener when(menuItem.itemId){
                    R.id.action_delete ->{
                        val noteViewModel = NoteViewModel(Application())
                        noteViewModel.delete(note)
                        true
                    }
                    else -> false
                }

            })
            popup.show()
        }*/
        holder.bind(note)
    }
    internal fun setNotes(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }
}