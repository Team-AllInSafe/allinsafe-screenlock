// 여기서 조건 3가지 추가할 예정입니다!

package com.example.allinsafe_screenlock.util

import android.content.Context
import android.content.SharedPreferences

object LockReasonManager {
    private const val PREF_NAME = "LockPrefs"
    private const val KEY_REASON = "last_lock_reason"

    fun saveLockReason(context: Context, reason: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val success = prefs.edit().putString(KEY_REASON, reason).commit() // 🔄 commit()으로 변경
        android.util.Log.d("LockReasonManager", "🔐 Lock reason saved: $reason (success: $success)")
    }


    fun getLastLockReason(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_REASON, "알 수 없는 이유") ?: "알 수 없는 이유"
    }
}
