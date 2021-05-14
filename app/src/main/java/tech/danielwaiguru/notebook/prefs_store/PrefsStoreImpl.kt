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

package tech.danielwaiguru.notebook.prefs_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import tech.danielwaiguru.notebook.common.Constants.DARK_MODE_ENABLED
import tech.danielwaiguru.notebook.common.Constants.STORE_NAME
import java.io.IOException

class PrefsStoreImpl(private val context: Context): PrefsStore {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(STORE_NAME)
    override fun isNightMode(): Flow<Boolean> = context.dataStore.data.catch { exception ->
        if (exception is IOException){
            emit(emptyPreferences())
        }
    }.map { it[PreferencesKeys.NIGHT_MODE_KEY] ?: false }.flowOn(Dispatchers.IO)

    override suspend fun toggleNightMode() {
        context.dataStore.edit {
            it[PreferencesKeys.NIGHT_MODE_KEY] = !(it[PreferencesKeys.NIGHT_MODE_KEY] ?: false)
        }
    }
}
private object PreferencesKeys {
    val NIGHT_MODE_KEY = booleanPreferencesKey(DARK_MODE_ENABLED)
}