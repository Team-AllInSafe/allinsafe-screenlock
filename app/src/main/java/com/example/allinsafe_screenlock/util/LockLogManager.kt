package com.example.allinsafe_screenlock.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object LockLogManager {
    fun logLockEvent(reason: String) {
        val timestamp = System.currentTimeMillis()
        val formattedTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(timestamp))
        Log.d("🔐 LockEvent", "잠금 발생 - 사유: $reason, 시각: $formattedTime")
    }
    // 여기서 firebase에 업로드 시킬 예정
}
