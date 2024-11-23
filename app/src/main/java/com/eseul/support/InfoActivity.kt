package com.eseul.support

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        // Toolbar 설정
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바 제목 표시 비활성화

        // 뒤로가기 버튼 클릭 시 공지화면으로 이동
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }


    }
}