/*
 *   Copyright 2020 Daniel Waiguru
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package tech.danielwaiguru.notebook.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.danielwaiguru.notebook.R
import tech.danielwaiguru.notebook.common.Constants.NOTE_WITHOUT_TITLE
import tech.danielwaiguru.notebook.common.Constants.NOTE_WITH_TITLE
import tech.danielwaiguru.notebook.databinding.NoteItemWithTitleBinding
import tech.danielwaiguru.notebook.databinding.NoteItemWithoutTitleBinding
import tech.danielwaiguru.notebook.domain.model.Note
import tech.danielwaiguru.notebook.utils.DateUtils

class NoteAdapter(private val context: Context, private val listener: (Note) -> Unit):
    ListAdapter<Note, RecyclerView.ViewHolder>(NoteDiffCallback) {
    class NoteWithTitleViewHolder(private val itemBinding: NoteItemWithTitleBinding):
        RecyclerView.ViewHolder(itemBinding.root){
        fun bind(context: Context, note: Note){
            itemBinding.noteItem.animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            itemBinding.textViewTitle.text = note.noteTitle
            itemBinding.textViewNote.text = note.noteText
            itemBinding.textViewDate.text = DateUtils.formatTimeToDate(note.createdAt)
        }
    }
    class NoteWithoutViewHolder(private val itemBinding: NoteItemWithoutTitleBinding):
        RecyclerView.ViewHolder(itemBinding.root){
        fun bind(context: Context, note: Note){
            itemBinding.textViewNote.text = note.noteText
            itemBinding.textViewDate.text = DateUtils.formatTimeToDate(note.createdAt)
            itemBinding.noteItemLayout.animation = AnimationUtils
                .loadAnimation(context, R.anim.fade_in)
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = getItem(position)
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
        return if (getItem(position).noteTitle == ""){
            NOTE_WITHOUT_TITLE
        }
        else {
            NOTE_WITH_TITLE
        }
    }
}