package com.abdallahyasser.maslahty.util

import android.content.Context
import android.content.SharedPreferences
import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.google.gson.Gson

object PreferenceManager {
    private const val PREF_NAME = "maslahty_prefs"
    private const val KEY_ONBOARDING_SHOWN = "onboarding_shown"
    private const val KEY_USER_JSON = "saved_user"
    private lateinit var preferences: SharedPreferences
    private val gson = Gson()

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // Onboarding flag
    fun setOnboardingShown(shown: Boolean) {
        preferences.edit().putBoolean(KEY_ONBOARDING_SHOWN, shown).apply()
    }

    fun isOnboardingShown(): Boolean {
        return preferences.getBoolean(KEY_ONBOARDING_SHOWN, false)
    }

    // User persistence
    fun saveUser(user: User) {
        val json = gson.toJson(user)
        preferences.edit().putString(KEY_USER_JSON, json).apply()
    }

    fun getUser(): User? {
        val json = preferences.getString(KEY_USER_JSON, null) ?: return null
        return gson.fromJson(json, User::class.java)
    }

    fun clearUser() {
        preferences.edit().remove(KEY_USER_JSON).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return getUser() != null
    }
}
