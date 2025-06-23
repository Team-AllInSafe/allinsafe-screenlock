package com.example.allinsafe_screenlock.util

import android.content.Context
import android.content.SharedPreferences
import com.example.allinsafe_screenlock.pinlock.PinStorageManager

object TwoFactorAuthManager {
    private const val PREF_NAME = "TwoFactorAuthPrefs"
    private const val KEY_2FA_ENABLED = "is_2fa_enabled"

    // ✅ 2FA 설정 상태 저장
    fun set2FAEnabled(context: Context, enabled: Boolean) {
        val prefs = getPrefs(context)
        prefs.edit().putBoolean(KEY_2FA_ENABLED, enabled).apply()
    }

    // ✅ 2FA 설정 상태 확인
    fun is2FAEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_2FA_ENABLED, false)
    }

    // ✅ PIN 존재 여부 확인 (PinStorageManager 활용)
    fun hasPin(context: Context): Boolean {
        return PinStorageManager.isPinSet(context) // ✅ 여기 수정
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}
