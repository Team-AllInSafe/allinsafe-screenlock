package com.example.allinsafe_screenlock

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.allinsafe_screenlock.util.LockLogManager
import com.example.allinsafe_screenlock.util.LockReasonManager

class MainActivity : AppCompatActivity() {
    private lateinit var dpm: DevicePolicyManager
    private lateinit var compName: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        compName = ComponentName(this, MyDeviceAdminReceiver::class.java)

        findViewById<Button>(R.id.btn_request_admin).setOnClickListener {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName)
            intent.putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "분실 시 기기 잠금을 위해 관리자 권한이 필요합니다."
            )
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_lock).setOnClickListener {
            if (dpm.isAdminActive(compName)) {
                val reason = "테스트: 수동 잠금 버튼 눌림"

                // 🔐 잠금 사유 저장
                LockReasonManager.saveLockReason(this, reason)

                // 📋 로그 기록
                LockLogManager.logLockEvent(reason)

                // 🔒 기기 잠금
                dpm.lockNow()
            } else {
                val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName)
                startActivity(intent)
            }
        }
    }
}
