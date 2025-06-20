package com.example.allinsafe_screenlock

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allinsafe_screenlock.util.LockManager
import com.example.allinsafe_screenlock.util.LockLogManager
import com.example.allinsafe_screenlock.util.LockReasonManager

class MainActivity : AppCompatActivity() {

    private lateinit var dpm: DevicePolicyManager
    private lateinit var compName: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ✅ [테스트용] 부팅 시간 강제 세팅 (7시간 전)
        // 테스트가 끝나면 반드시 삭제하자 !!!
        com.example.allinsafe_screenlock.util.BootTimeManager.saveLastBootTime(
            this,
            System.currentTimeMillis() - (7 * 60 * 60 * 1000L)
        )

        dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        compName = ComponentName(this, MyDeviceAdminReceiver::class.java)

        findViewById<Button>(R.id.btn_request_admin).setOnClickListener {
            if (!dpm.isAdminActive(compName)) {
                val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                    putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName)
                    putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "분실 시 강제 잠금을 위해 관리자 권한이 필요합니다.")
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "이미 관리자 권한이 부여되어 있습니다", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btn_lock_now).setOnClickListener {
            if (dpm.isAdminActive(compName)) {
                val reason = "수동 잠금 실행됨"
                LockReasonManager.saveReason(this, reason)
                LockLogManager.log(this, reason)
                LockManager.lockNow(this)
            } else {
                Toast.makeText(this, "관리자 권한이 없습니다. 먼저 권한을 부여해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
