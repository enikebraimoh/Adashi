package ng.adashi.utils

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Utils {

    class LoginState(context: Context) {
        private val accessToken = context.createDataStore("token_key")
        private val login = context.createDataStore("login_key")

        companion object {
            val TOKEN = preferencesKey<String>("token_value")
            val LOGIN_STATE = preferencesKey<Boolean>("state_value")
        }

        suspend fun saveLoginState(login_state: Boolean) {
            login.edit {
                it[LOGIN_STATE] = login_state
            }
        }
        suspend fun saveAccessToken(token_value: String) {
            accessToken.edit {
                it[TOKEN] = token_value
            }
        }

        val loginStateFlow: Flow<Boolean> = login.data.map {
            it[LOGIN_STATE] ?: false
        }
        val tokenStateFlow: Flow<Boolean> = accessToken.data.map {
            it[LOGIN_STATE] ?: false
        }

    }

}