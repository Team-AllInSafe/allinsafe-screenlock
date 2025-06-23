package com.example.allinsafe_screenlock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.allinsafe_screenlock.pinlock.PinLockActivity
import com.example.allinsafe_screenlock.pinlock.PinStorageManager
import com.example.allinsafe_screenlock.util.TwoFactorAuthManager

class LockStatusReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_USER_PRESENT) {
            // ✅ 2차 인증 기능이 활성화되어 있고 PIN이 설정되어 있을 경우 항상 인증 요구
            if (TwoFactorAuthManager.is2FAEnabled(context) &&
                PinStorageManager.isPinSet(context)) {

                val pinIntent = Intent(context, PinLockActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                context.startActivity(pinIntent)
            }
        }
    }
}
