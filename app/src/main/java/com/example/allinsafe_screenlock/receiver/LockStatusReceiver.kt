package com.example.allinsafe_screenlock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.allinsafe_screenlock.util.LockReasonManager
import com.example.allinsafe_screenlock.util.TwoFactorAuthManager
import com.example.allinsafe_screenlock.pinlock.PinStorageManager


// 브로드캐스트를 통한 화면켜짐 감지

class LockStatusReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_USER_PRESENT) {
            // ✅ 화면잠금 기능 + 2차 인증 + PIN 설정 시 → 인증 필요 기록
            if (TwoFactorAuthManager.isScreenLockEnabled(context) &&
                TwoFactorAuthManager.is2FAEnabled(context) &&
                PinStorageManager.isPinSet(context)) {

                // ✅ 잠금 사유 기록 (MainActivity에서 인증화면 실행을 유도)
                LockReasonManager.saveReason(context, "화면이 꺼졌다가 켜짐")
            }
        }
    }
}
