package tech.danielwaiguru.notebook.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.common.Constants.NOTE_WITHOUT_TITLE
import tech.danielwaiguru.notebook.common.Constants.NOTE_WITH_TITLE
import tech.danielwaiguru.notebook.database.Note
import tech.danielwaiguru.notebook.databinding.NoteItemWithTitleBinding
import tech.danielwaiguru.notebook.databinding.NoteItemWithoutTitleBinding
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(private val context: Context, private val listener: (Note) -> Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var notes = emptyList<Note>() //Cached copy of notes
    private var searchableList = emptyList<Note>()
    class NoteWithTitleViewHolder(private val itemBinding: NoteItemWithTitleBinding):
        RecyclerView.ViewHolder(itemBinding.root){
        fun bind(context: Context, note: Note){
            itemBinding.noteItem.animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            itemBinding.textViewTitle.text = note.noteTitle
            itemBinding.textViewNote.text = note.noteText
            itemBinding.textViewDate.text = note.createdAt
        }
    }
    class NoteWithoutViewHolder(private val itemBinding: NoteItemWithoutTitleBinding):
        RecyclerView.ViewHolder(itemBinding.root){
        fun bind(context: Context, note: Note){
            itemBinding.textViewNote.text = note.noteText
            itemBinding.textViewDate.text = note.createdAt
            itemBinding.noteItemLayout.animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == NOTE_WITH_TITLE){
            val itemBinding = NoteItemWithTitleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            NoteWithTitleViewHolder(itemBinding)
        } else{
            val itemBinding = NoteItemWithoutTitleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            NoteWithoutViewHolder(itemBinding)
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