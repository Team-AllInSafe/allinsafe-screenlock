package com.example.allinsafe_screenlock.util

import android.content.Context
import android.content.SharedPreferences

object BootTimeManager {

    private const val PREF_NAME = "boot_time_prefs"
    private const val KEY_LAST_BOOT = "last_boot_time"

    fun saveLastBootTime(context: Context, timeMillis: Long) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putLong(KEY_LAST_BOOT, timeMillis).apply()
    }

    fun getLastBootTime(context: Context): Long {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getLong(KEY_LAST_BOOT, 0L)
    }
}
