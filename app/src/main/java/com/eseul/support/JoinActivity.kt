package com.eseul.support

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        // 뒤로가기 버튼 클릭 시 로그인 화면으로 이동
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // 회원가입 완료 버튼 클릭 시 알림창 표시
        val joinDoneButton: Button = findViewById(R.id.joinDone)
        joinDoneButton.setOnClickListener {
            showCompletionDialog()
        }
    }

    private fun showCompletionDialog() {
        // 닉네임 입력란에서 입력된 텍스트 가져오기
        val nicknameEditText: EditText = findViewById(R.id.nickname_input)
        val nickname = nicknameEditText.text.toString()

        // 알림창 구성
        val builder = AlertDialog.Builder(this)
        builder.setTitle("${nickname}님 환영합니다!")
        builder.setMessage("${nickname} 회원가입이 완료되었습니다.")
        builder.setPositiveButton("로그인하러가기") { _, _ ->
            // 로그인 화면으로 이동
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }

        // 알림창 표시
        val dialog = builder.create()
        dialog.show()
    }
}
