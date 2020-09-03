package tech.danielwaiguru.notebook.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item_view_holder.view.*
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.database.Note

class NoteAdapter(private val context: Context, private val listener: (Note) -> Unit):
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes = emptyList<Note>() //Cached copy of notes
    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val noteItem: ConstraintLayout = itemView.noteItem
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
        holder.noteItem.animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        holder.textViewTitle.text = note.noteTitle
        holder.textViewNoteText.text = note.noteText
        holder.textViewDate.text = note.createdAt
        //holder.bind(note)
        holder.itemView.setOnClickListener {
            listener(note)
        }
        /*holder.textViewDelete.setOnClickListener {
            val popup = PopupMenu(it.context, it)
            popup.menuInflater.inflate(R.menu.main_menu, popup.menu)
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
    interface NoteItemClickListener {
        fun onNoteItemClicked(note: Note)
    }
}