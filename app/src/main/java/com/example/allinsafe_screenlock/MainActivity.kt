package com.example.allinsafe_screenlock

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.allinsafe_screenlock.pinlock.PinLockActivity
import com.example.allinsafe_screenlock.pinlock.PinSetupActivity
import com.example.allinsafe_screenlock.util.LockReasonManager
import com.example.allinsafe_screenlock.util.TwoFactorAuthManager

class MainActivity : AppCompatActivity() {

    private lateinit var dpm: DevicePolicyManager
    private lateinit var compName: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ 2차 인증이 켜져 있고, 인증이 필요한 경우 PIN 인증화면으로 전환
        if (TwoFactorAuthManager.is2FAEnabled(this) &&
            LockReasonManager.hasReason(this)) {
            startActivity(Intent(this, PinLockActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        compName = ComponentName(this, MyDeviceAdminReceiver::class.java)

        val btnRequestAdmin = findViewById<Button>(R.id.btn_request_admin)
        val btnLockNow = findViewById<Button>(R.id.btn_lock_now)
        val switch2FA = findViewById<Switch>(R.id.switch_2fa)
        val btnSetPin = findViewById<Button>(R.id.btn_set_pin)

        // 🔹 초기 스위치 & 버튼 상태
        switch2FA.isChecked = TwoFactorAuthManager.is2FAEnabled(this)
        btnSetPin.visibility = if (switch2FA.isChecked) View.VISIBLE else View.GONE

        // 🔹 관리자 권한 요청
        btnRequestAdmin.setOnClickListener {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName)
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "기기 잠금 권한을 부여해주세요.")
            startActivity(intent)
        }

        // 🔹 수동 잠금
        btnLockNow.setOnClickListener {
            if (dpm.isAdminActive(compName)) {
                dpm.lockNow()
            } else {
                Toast.makeText(this, "관리자 권한이 필요합니다", Toast.LENGTH_SHORT).show()
            }
        }

        // 🔹 2차 인증 사용 여부 토글
        switch2FA.setOnCheckedChangeListener { _, isChecked ->
            TwoFactorAuthManager.set2FAEnabled(this, isChecked)
            btnSetPin.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        // 🔹 PIN 설정 화면 이동
        btnSetPin.setOnClickListener {
            startActivity(Intent(this, PinSetupActivity::class.java))
        }
    }
}
