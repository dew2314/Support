package com.eseul.support

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.eseul.support.model.UserModel
import com.eseul.support.model.api.AuthApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Toolbar 설정
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바 제목 표시 비활성화

        // 로그인 버튼 클릭 시 메인 화면으로 이동
        val loginButton: Button = findViewById(R.id.loginBtn)
        loginButton.setOnClickListener {
            // 로그인 처리
//            saveLoginStatus(true)
            val authApi=AuthApi.create()
            val body = hashMapOf(
                "loginId" to "test1234",
                "password" to "qwer1234!"
            )
            Log.d("TAG", "onCreate: $body")
            authApi.login(body).enqueue(object : Callback<UserModel>{
                override fun onResponse(
                    call: Call<UserModel>,
                    response: Response<UserModel>
                ) {
                    Log.d("TAG", "onResponse: ${response.body()}")
                    Log.d("TAG", "onResponse: Success!")
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Log.d("TAG", "onFailure: Failure!")
                }

            })

            // MainActivity로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // 로그인 후 로그인 화면 종료
        }

        // 뒤로가기 버튼 클릭 시 이전 화면으로 이동
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 비밀번호 찾기 텍스트 클릭 시 이동할 액티비티 설정
        val additionalTextView: TextView = findViewById(R.id.findpwd)
        additionalTextView.setOnClickListener {
            val intent = Intent(this, FindPasswordActivity::class.java)
            startActivity(intent)
        }

        // 회원가입 TextView 클릭 이벤트 처리
        val registerTextView: TextView = findViewById(R.id.joinLogin)
        registerTextView.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveLoginStatus(isLoggedIn: Boolean) {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        editor.apply()
    }


}
