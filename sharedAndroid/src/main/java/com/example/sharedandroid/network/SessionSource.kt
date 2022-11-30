package com.example.sharedandroid.network

import android.content.Context
import com.example.automobile.exceptions.NoSessionException
import com.example.automobile.network.ISessionSource
import com.example.automobile.network.model.SessionData

class SessionSource(private val context: Context) : ISessionSource {
    override fun getSessionData(): SessionData {
        val sharedPref = context.getSharedPreferences("com.example.automobile.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
            ?: throw NoSessionException("Session not found on the device!")

        val jwt = sharedPref.getString("jwt", null)
        val refreshToken = sharedPref.getString("refreshToken", null)

        if (jwt == null || refreshToken == null) throw NoSessionException("Session not found on the device!")
        return SessionData(jwt , refreshToken)
    }

    override fun updateSessionData(sessionData: SessionData) {
        val sharedPref = context.getSharedPreferences("com.example.automobile.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putString("jwt", sessionData.jwt)
            putString("refreshToken", sessionData.refreshToken)
            //apply()
            commit()
        }
    }

    override fun removeSessionData() {
        val sharedPref = context.getSharedPreferences("com.example.automobile.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
            ?: throw NoSessionException("Session not found on the device!")

        with(sharedPref.edit()){
            remove("jwt")
            remove("refreshToken")
            commit()
        }
    }
}