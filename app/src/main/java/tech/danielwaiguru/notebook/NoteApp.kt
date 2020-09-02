package tech.danielwaiguru.notebook

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin

class NoteApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
    //initialize koin
    private fun initKoin(){
        startKoin {
            AndroidLogger()
            androidContext(this@NoteApp)
            modules(listOf())
        }
    }
}