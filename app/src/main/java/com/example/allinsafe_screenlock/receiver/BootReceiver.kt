package com.example.allinsafe_screenlock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.example.allinsafe_screenlock.util.BootTimeManager
import com.example.allinsafe_screenlock.util.LockLogManager
import com.example.allinsafe_screenlock.util.LockManager
import com.example.allinsafe_screenlock.util.LockReasonManager

class BootReceiver : BroadcastReceiver() {

    companion object {
        //private const val THRESHOLD_MILLIS = 6 * 60 * 60 * 1000L  // 6시간
        private const val THRESHOLD_MILLIS = 1 * 1000L              // 1초
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val now = System.currentTimeMillis()
            val lastBootTime = BootTimeManager.getLastBootTime(context)

            if (lastBootTime != 0L && (now - lastBootTime) >= THRESHOLD_MILLIS) {
                val reason = "2차 인증 요청"
                LockReasonManager.saveReason(context, reason)
                LockLogManager.log(context, reason)
                LockManager.lockNow(context)
            }

            // 현재 부팅 시간 저장
            BootTimeManager.saveLastBootTime(context, now)
        }
    }
}
