package com.eseul.support

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eseul.support.model.api.JoinApi
import com.eseul.support.repository.JoinRepository
import com.eseul.support.viewmodel.JoinViewModel
import com.eseul.support.viewmodel.JoinViewModelFactory

class JoinActivity : AppCompatActivity() {

    private lateinit var joinViewModel: JoinViewModel
    private lateinit var portalIdEditText:EditText
    private lateinit var authCodeEditText:EditText
    private lateinit var passwordEditText:EditText
    private lateinit var confirmPasswordEditText:EditText
    private lateinit var nicknameEditText:EditText
    private lateinit var genderSpinner:Spinner
    private lateinit var dormTypeSpinner:Spinner
    private lateinit var dormNoEditText:EditText
    private lateinit var dormRoomEditText:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

         portalIdEditText = findViewById(R.id.potalId)
         authCodeEditText = findViewById(R.id.AuthCode)
         passwordEditText = findViewById(R.id.password_input)
         confirmPasswordEditText = findViewById(R.id.password_confirm_input)
         nicknameEditText = findViewById(R.id.nickname_input)
         genderSpinner = findViewById(R.id.gender_spinner)
         dormTypeSpinner=findViewById(R.id.dormType_spinner)
         dormNoEditText=findViewById(R.id.dormNo_input)
         dormRoomEditText=findViewById(R.id.dorm_room_no_input)

        // ViewModel 초기화
        val joinApi = JoinApi.create()
        val joinRepository = JoinRepository(joinApi)
        joinViewModel = ViewModelProvider(this, JoinViewModelFactory(joinRepository))
            .get(JoinViewModel::class.java)

        // 뒤로가기 버튼 클릭 시 로그인 화면으로 이동
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        // 전송 버튼 (ImageButton으로 정의된 버튼)
        val sendButton: ImageButton = findViewById(R.id.send_icon)
        sendButton.setOnClickListener {
            sendEmailAuthCode()
        }

        // 확인 버튼 (ImageButton으로 정의된 버튼)
        val confirmButton: ImageButton = findViewById(R.id.confirm_icon)
        confirmButton.setOnClickListener {
            verifyEmailCode()
        }

        // 회원가입 완료 버튼 클릭 시 API 호출
        val joinDoneButton: Button = findViewById(R.id.joinDone)
        joinDoneButton.setOnClickListener {
            performSignup()
        }

        setUpSpinner() // 성별 스피너 초기화
        setUpDormSpinner()  // 기숙사 스피너 초기화
    }

    private fun sendEmailAuthCode() {
        val portalIdEditText: EditText = findViewById(R.id.potalId)
        val portalId = portalIdEditText.text.toString()

        if (portalId.isBlank()) {
            Toast.makeText(this, "포털 아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        // ViewModel을 통해 인증 코드 요청
        joinViewModel.mail(portalId).observe(this) { result ->
            if (result.isSuccess) {
                Toast.makeText(this, "인증코드가 이메일로 전송되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "인증코드 전송 실패: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyEmailCode() {
        val portalIdEditText: EditText = findViewById(R.id.potalId)
        val authCodeEditText: EditText = findViewById(R.id.AuthCode)

        val portalId = portalIdEditText.text.toString()
        val authCode = authCodeEditText.text.toString()

        if (portalId.isBlank() || authCode.isBlank()) {
            Toast.makeText(this, "포털 아이디와 인증코드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        // ViewModel을 통해 인증 코드 확인 요청
        joinViewModel.mailCheck(portalId, authCode).observe(this) { result ->
            if (result.isSuccess) {
                Toast.makeText(this, "이메일 인증이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "이메일 인증 실패: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performSignup() {
        val portalId = portalIdEditText.text.toString()
        val authCode = authCodeEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()
        val nickname = nicknameEditText.text.toString()
        val gender = genderKR2EN[genderSpinner.selectedItem.toString()]!!
        val dormType = dormKR2EN[dormTypeSpinner.selectedItem.toString()]!!
        val dormNo = dormNoEditText.text.toString()
        val roomNo = dormRoomEditText.text.toString()

        // 유효성 검사
        if (portalId.isBlank() || authCode.isBlank() || password.isBlank() || confirmPassword.isBlank()
            || nickname.isBlank() || gender.isBlank() || dormType.isBlank() || dormNo.isBlank() || roomNo.isBlank()) {
            Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        // ViewModel 호출
        joinViewModel.signup(
            portalId, authCode, password, confirmPassword, nickname, gender, dormType, dormNo, roomNo
        ).observe(this) { result ->
            if (result.isSuccess) {
                showCompletionDialog(nickname)
            } else {
                Toast.makeText(this, "회원가입 실패: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setUpSpinner() {
        val genderSpinner: Spinner = findViewById(R.id.gender_spinner)
        val genderArray = arrayOf("남성", "여성", "성별") // 마지막 항목은 힌트로 사용

        val adapter = object : ArrayAdapter<String>(
            this,
            R.layout.spinner_item, // 선택된 아이템 레이아웃
            genderArray
        ) {
            override fun getCount(): Int {
                // 힌트를 제외한 항목 수 반환
                return super.getCount() - 1
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                setIconForGender(view, position) // 드롭다운 항목에 아이콘 설정

                // 항목별 배경 설정
                when (position) {
                    0 -> view.setBackgroundResource(R.drawable.top_rounded_edittext_background) // 남성
                    1 -> view.setBackgroundResource(R.drawable.bottom_rounded_edittext_background) // 여성
                }
                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                setIconForGender(view, if (position < genderArray.size - 1) position else 2)
                return view
            }
        }

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        genderSpinner.adapter = adapter

        // 기본 선택값 설정 (힌트 항목의 인덱스 사용)
        genderSpinner.setSelection(genderArray.size - 1) // "성별 선택"을 기본값으로 설정

        // 선택 이벤트 처리
        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val textView = view as? TextView
                if (position == adapter.count) {
                    // 힌트가 선택된 경우 색상을 회색으로 설정
                    textView?.setTextColor(Color.GRAY)
                } else {
                    // 남성 또는 여성이 선택된 경우 검정색으로 설정
                    textView?.setTextColor(Color.BLACK)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 선택되지 않은 경우 기본값 유지
            }
        }
    }

    // 선택 값에 따라 아이콘을 설정하는 함수
    private fun setIconForGender(textView: TextView?, position: Int) {
        val icon = when (position) {
            0 -> R.drawable.icon_male // 남성 아이콘
            1 -> R.drawable.icon_female // 여성 아이콘
            2 -> R.drawable.gender // 성별 선택 기본 아이콘
            else -> 0 // 기본값
        }
        textView?.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0) // 왼쪽에 아이콘 설정
        textView?.compoundDrawablePadding = if (icon != 0) 8 else 0 // 아이콘이 없으면 간격 제거
    }

    val genderKR2EN = hashMapOf(
        "남성" to "MAN",
        "여성" to "WOMAN"
    )

    // 기숙사 동 Spinner 설정
    private fun setUpDormSpinner() {
        val dormTypeSpinner: Spinner = findViewById(R.id.dormType_spinner)
        val dormTypeArray = arrayOf("기숙사 동", "고운 A", "고운 B", "고운 C", "경상남자", "경상여자") // 초기값 포함

        // 어댑터 설정
        val adapter = object : ArrayAdapter<String>(
            this,
            R.layout.spinner_dorm_item, // 선택된 아이템 레이아웃
            dormTypeArray
        ) {
            override fun getCount(): Int {
                // "기숙사 동"을 제외한 나머지 항목만 반환
                return super.getCount() - 1
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                // 드롭다운 항목별 배경 설정
                setDormBackground(view, position)
                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                if (position == dormTypeArray.size - 1) {
                    // 기본 힌트 설정
                    view.setTextColor(Color.GRAY)
                    view.text = "기숙사 동"
                } else {
                    view.setTextColor(Color.BLACK)
                }
                return view
            }
        }

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        dormTypeSpinner.adapter = adapter

        // 기본 선택값 설정
        dormTypeSpinner.setSelection(adapter.count)

        // 선택 이벤트 처리
        dormTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position == adapter.count) {
                    // "기숙사 동" 힌트 색상 설정
                    (view as? TextView)?.setTextColor(Color.GRAY)
                } else {
                    (view as? TextView)?.setTextColor(Color.BLACK)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 선택되지 않은 경우 처리
            }
        }
    }

    val dormKR2EN = hashMapOf(
        "고운 A" to "GounA",
        "고운 B" to "GounB",
        "고운 C" to "GounC",
        "경상남자" to "GyungM",
        "경상여자" to "GyungW",
    )

    // 각 기숙사 동별 배경 설정 함수
    private fun setDormBackground(textView: TextView?, position: Int) {
        when (position) {
            1 -> textView?.setBackgroundResource(R.drawable.top_rounded_edittext_background) // 고운 A
            2 -> textView?.setBackgroundResource(R.drawable.medium_rounded_edittext) // 고운 B
            3 -> textView?.setBackgroundResource(R.drawable.medium_rounded_edittext) // 고운 C
            4 -> textView?.setBackgroundResource(R.drawable.medium_rounded_edittext) // 경상남자
            5 -> textView?.setBackgroundResource(R.drawable.bottom_rounded_edittext_background) // 경상여자
            else -> {
                // 기본값을 "기숙사 동"으로 설정
                textView?.setBackgroundResource(R.drawable.top_rounded_edittext_background) // 기본 배경
                textView?.text = "기숙사 동" // 기본 힌트 텍스트
                textView?.setTextColor(Color.GRAY) // 힌트 색상 설정
            }
        }
    }





    private fun showCompletionDialog(nickname: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("${nickname}님 환영합니다!")
        builder.setMessage("${nickname} 회원가입이 완료되었습니다.")
        builder.setPositiveButton("로그인하러가기") { _, _ ->
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val dialog = builder.create()
        dialog.show()
    }
}
