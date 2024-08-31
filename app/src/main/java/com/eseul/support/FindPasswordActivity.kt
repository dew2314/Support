package com.eseul.support

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class FindPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)

        // 뒤로가기 버튼 클릭 시 로그인 화면으로 이동
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            // 기본 뒤로가기 동작 수행
            onBackPressed()
        }

        // 로그인 하러 가기 버튼 클릭시 로그인 화면으로 이동
        val goLoginButton: Button = findViewById(R.id.goLogin)
        goLoginButton.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}