package com.eseul.support

import LoginViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.eseul.support.model.UserModel
import com.eseul.support.model.api.AuthApi
import com.eseul.support.repository.AuthRepository
import com.eseul.support.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Toolbar 설정
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // ViewModel 초기화
        val authApi = AuthApi.create()
        val authRepository = AuthRepository(authApi)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(authRepository))
            .get(LoginViewModel::class.java)

        // 사용자 입력
        val usernameEditText = findViewById<EditText>(R.id.potalId) // 포털 아이디 입력란
        val passwordEditText = findViewById<EditText>(R.id.potalPwd) // 비밀번호 입력란

        // 로그인 버튼 클릭 시
        val loginButton: Button = findViewById(R.id.loginBtn)
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            lifecycleScope.launch {
                loginViewModel.login(username, password)
            }
        }

        // 로그인 결과 
        loginViewModel.loginResult.observe(this) { result ->
            if (result is UserModel) {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()

                // 로그인 성공 시 토큰 저장
                result.let {
                    PreferenceHelper.setLoggedIn(this, true)
                    PreferenceHelper.setAccessToken(this, it.loginData.loginAcToken)
                    PreferenceHelper.setRefreshToken(this, it.loginData.loginReToken)
                }
                //helper.accessToken = "asdasd...."
                //helper.refershToken = "asdasd...."
                //helper.refershToken
                //loginViewModel.saveToken()

                // MainActivity로 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "Login failed: ${result}")
            }
        }

        // 뒤로가기 버튼 클릭 시
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        // 비밀번호 찾기 및 회원가입 클릭 이벤트 처리
        val findPwdTextView: TextView = findViewById(R.id.findpwd)
        findPwdTextView.setOnClickListener {
            val intent = Intent(this, FindPasswordActivity::class.java)
            startActivity(intent)
        }

        val joinTextView: TextView = findViewById(R.id.joinLogin)
        joinTextView.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }
}
