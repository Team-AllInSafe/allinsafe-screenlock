package com.example.allinsafe_screenlock.pinlock

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allinsafe_screenlock.MainActivity
import com.example.allinsafe_screenlock.R
import com.example.allinsafe_screenlock.util.LockReasonManager

class PinLockActivity : AppCompatActivity() {

    private lateinit var edtPin: EditText
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_lock)

        edtPin = findViewById(R.id.et_pin)
        btnSubmit = findViewById(R.id.btn_submit)

        btnSubmit.setOnClickListener {
            val inputPin = edtPin.text.toString()

            if (PinStorageManager.isPinCorrect(this, inputPin)) {
                Toast.makeText(this, "✅ 인증 성공", Toast.LENGTH_SHORT).show()

                // ✅ 잠금 사유 제거
                LockReasonManager.clearReason(this)

                // ✅ MainActivity로 이동 (기존 백스택 제거)
                val intent = Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "❌ PIN이 올바르지 않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Suppress("MissingSuperCall")
    override fun onBackPressed() {
        // 뒤로가기 방지
    }
}
