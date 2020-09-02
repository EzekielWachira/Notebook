package tech.danielwaiguru.notebook.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tech.danielwaiguru.notebook.ui.add.AddNoteViewModel
import tech.danielwaiguru.notebook.ui.edit.ReadNoteViewModel
import tech.danielwaiguru.notebook.ui.note.NoteViewModel

val viewModelModule = module {
    viewModel { AddNoteViewModel() }
    viewModel { ReadNoteViewModel() }
    viewModel { NoteViewModel() }
}