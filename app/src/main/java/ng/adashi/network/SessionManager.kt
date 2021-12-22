package ng.adashi.network

import android.content.Context
import android.content.SharedPreferences
import ng.adashi.R
import ng.adashi.domain_models.login.AgentUser
import ng.adashi.utils.App
import javax.inject.Inject

class SessionManager
@Inject
constructor(var context: Context) {

    var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val LOGINSTATE = "login_state"
        const val AGENT_ID = "agent_id"
        const val AGENT_FIRST_NAME = "agent_first_name"
    }

    /**
     * Function to save auth token
     */

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putBoolean(LOGINSTATE, true)
        editor.apply()
        App.token = token
    }

    fun saveCurrentAgent(agent: AgentUser) {
        val editor = prefs.edit()
        editor.putString(AGENT_ID, agent.agentID)
        editor.putString(AGENT_FIRST_NAME, agent.firstName)
        editor.apply()
    }

    fun clearAuthToken() {
        val editor = prefs.edit().clear()
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */

    fun fetchAuthToken(): String? {
        App.token = prefs.getString(USER_TOKEN, null)
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchCurrentAgent(): AgentUser {
        return AgentUser(null,
            null,
            null,
            prefs.getString(AGENT_ID,"ID")!!,
            null,
            prefs.getString(AGENT_FIRST_NAME,"NAME")!!,
            null,
            null,
            null,
            null,
            null)
    }


}