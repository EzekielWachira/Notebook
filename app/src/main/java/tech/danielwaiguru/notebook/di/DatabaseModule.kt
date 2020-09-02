package tech.danielwaiguru.notebook.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import tech.danielwaiguru.notebook.common.Constants.DB_NAME
import tech.danielwaiguru.notebook.database.NoteDatabase

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), NoteDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<NoteDatabase>().noteDao() }
}