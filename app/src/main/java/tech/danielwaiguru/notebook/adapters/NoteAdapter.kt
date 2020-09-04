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
import kotlinx.android.synthetic.main.note_item.view.*
import kotlinx.android.synthetic.main.note_item_view_holder.view.noteItem
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.common.Constants.NOTE_WITHOUT_TITLE
import tech.danielwaiguru.notebook.common.Constants.NOTE_WITH_TITLE
import tech.danielwaiguru.notebook.database.Note
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(private val context: Context, private val listener: (Note) -> Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var notes = emptyList<Note>() //Cached copy of notes
    private var searchableList = emptyList<Note>()
    class NoteWithTitleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val noteItem: ConstraintLayout = itemView.noteItem
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewNoteText: TextView = itemView.findViewById(R.id.textViewNote)
        private val textViewDate : TextView = itemView.findViewById(R.id.textViewDate)
        fun bind(context: Context, note: Note){
            noteItem.animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            textViewTitle.text = note.noteTitle
            textViewNoteText.text = note.noteText
            textViewDate.text = note.createdAt
        }
    }
    class NoteWithoutViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val noteLayout: ConstraintLayout = itemView.noteItem
        private val noteText: TextView = itemView.textViewNote
        private val date: TextView = itemView.textViewDate
        fun bind(context: Context, note: Note){
            noteText.text = note.noteText
            date.text = note.createdAt
            noteLayout.animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == NOTE_WITH_TITLE){
            NoteWithTitleViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.note_item_view_holder, parent, false
                )
            )
        } else{
            NoteWithoutViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.note_item, parent, false
            ))
        }
    }

    override fun getItemCount(): Int = searchableList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = searchableList[position]
        if (getItemViewType(position) == NOTE_WITH_TITLE){
            (holder as NoteWithTitleViewHolder).bind(context, note)
        }
        else{
            (holder as NoteWithoutViewHolder).bind(context, note)
        }
        holder.itemView.setOnClickListener {
            listener(note)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (searchableList[position].noteTitle == ""){
            NOTE_WITHOUT_TITLE
        }
        else {
            NOTE_WITH_TITLE
        }
    }
    internal fun setData(notes: List<Note>){
        this.searchableList = ArrayList(notes)
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                searchableList = if (constraint.isNullOrEmpty()){
                    notes
                } else {
                    val resultsList = ArrayList<Note>()
                    val filterPattern = constraint.toString()
                        .toLowerCase(Locale.ROOT)
                    for (note in notes){
                        if (note.noteText.toLowerCase(Locale.ROOT).contains(filterPattern)){
                            resultsList.add(note)
                        }
                    }
                    resultsList
                }
                filterResults.values = searchableList
                return filterResults
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchableList = results?.values as ArrayList<Note>
                notifyDataSetChanged()
            }
        }
    }
}