package tech.danielwaiguru.notebook.di

import org.koin.dsl.module
import tech.danielwaiguru.notebook.ui.add.AddNoteViewModel
import tech.danielwaiguru.notebook.ui.edit.ReadNoteViewModel
import tech.danielwaiguru.notebook.ui.note.NoteViewModel

val viewModelModule = module {
    single { AddNoteViewModel() }
    single { ReadNoteViewModel() }
    single { NoteViewModel() }
}