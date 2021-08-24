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

package tech.danielwaiguru.notebook

import com.google.android.play.core.splitcompat.SplitCompatApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import tech.danielwaiguru.notebook.data.di.appModule
import tech.danielwaiguru.notebook.data.di.databaseModule
import tech.danielwaiguru.notebook.data.di.repositoryModule
import tech.danielwaiguru.notebook.presentation.di.viewModelModule

class NoteApp: SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
    //initialize koin
    private fun initKoin(){
        startKoin {
            AndroidLogger()
            androidContext(this@NoteApp)
            modules(listOf(databaseModule, repositoryModule, viewModelModule, appModule))
        }
    }
}