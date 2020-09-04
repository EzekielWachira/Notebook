package tech.danielwaiguru.notebook.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item_view_holder.view.*
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.database.Note
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(private val context: Context, private val listener: (Note) -> Unit):
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(), Filterable {
    private var notes = emptyList<Note>() //Cached copy of notes
    private var searchableList = emptyList<Note>()
    private val onNothingFound: (() -> Unit)? = null
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

    override fun getItemCount(): Int = searchableList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note: Note = searchableList[position]
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
        this.searchableList = notes
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                searchableList = if (constraint.toString().isEmpty()){
                    notes
                } else {
                    val resultsList = ArrayList<Note>()
                    val filterPattern = constraint.toString()
                        .toLowerCase(Locale.ROOT)
                    for (note in notes){
                        if (note.noteTitle.contains(filterPattern)){
                            resultsList.add(note)
                        }
                    }
                    resultsList
                }
                filterResults.values = searchableList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                /*if (constraint.isNullOrEmpty()){
                    onNothingFound?.invoke()
                }*/
                searchableList = results?.values as ArrayList<Note>
                notifyDataSetChanged()
            }
        }
    }
}