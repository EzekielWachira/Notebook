package tech.danielwaiguru.notebook.di

import org.koin.dsl.module
import tech.danielwaiguru.notebook.database.NoteRepository

val repositoryModule = module {
    single { NoteRepository(get()) }
}