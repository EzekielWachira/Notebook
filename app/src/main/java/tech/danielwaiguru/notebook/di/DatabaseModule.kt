package tech.danielwaiguru.notebook.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import tech.danielwaiguru.notebook.common.Constants.DB_NAME
import tech.danielwaiguru.notebook.database.NoteDatabase

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), NoteDatabase::class.java, DB_NAME)
    }
    single { get<NoteDatabase>().noteDao() }
}