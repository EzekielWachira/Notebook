package tech.danielwaiguru.notebook

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import tech.danielwaiguru.notebook.di.databaseModule
import tech.danielwaiguru.notebook.di.repositoryModule
import tech.danielwaiguru.notebook.di.viewModelModule

class NoteApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
    //initialize koin
    private fun initKoin(){
        startKoin {
            AndroidLogger()
            androidContext(this@NoteApp)
            modules(listOf(databaseModule, repositoryModule, viewModelModule))
        }
    }
}