package com.example.allinsafe_screenlock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.allinsafe_screenlock.util.LockReasonManager

class LockStatusReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (Intent.ACTION_USER_PRESENT == intent?.action) {
            val reason = LockReasonManager.getLastLockReason(context)

            // ✅ 로그 기록만 남김
            Log.d("LockStatusReceiver", "📡 브로드캐스트 수신됨: ${intent.action}")
            Log.d("LockStatusReceiver", "✅ 사용자 잠금 해제 감지됨")
            Log.d("LockStatusReceiver", "🔍 저장된 잠금 사유: $reason")
        }
    }
}
