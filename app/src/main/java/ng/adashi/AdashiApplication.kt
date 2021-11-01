package ng.adashi

import android.app.Application
import ng.adashi.network.SessionManager
import ng.adashi.utils.App

class AdashiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val sessionManager = SessionManager(this)
         App.token = sessionManager.fetchAuthToken()

    }

}