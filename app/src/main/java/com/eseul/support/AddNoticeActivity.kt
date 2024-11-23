package com.eseul.support

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.eseul.support.fragment.CustomCalendarDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddNoticeActivity : AppCompatActivity() {

    private lateinit var startDateTextView: TextView
    private lateinit var endDateTextView: TextView
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notice)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // 뒤로가기 버튼 설정
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        // 시작 날짜와 종료 날짜 텍스트뷰 초기화
        startDateTextView = findViewById(R.id.start_date_value)
        endDateTextView = findViewById(R.id.end_date_value)

        // 현재 날짜로 초기화
        updateDateTextViews()

        // 날짜 선택 이벤트 설정
        startDateTextView.setOnClickListener {
            showCustomCalendarDialog { date ->
                startDateTextView.text = date
            }
        }

        endDateTextView.setOnClickListener {
            showCustomCalendarDialog { date ->
                endDateTextView.text = date
            }
        }
    }

    private fun showCustomCalendarDialog(onDateSelected: (String) -> Unit) {
        val calendarDialog = CustomCalendarDialogFragment(onDateSelected)
        calendarDialog.show(supportFragmentManager, "CustomCalendarDialogFragment")
    }

    private fun updateDateTextViews() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(calendar.time)
        startDateTextView.text = currentDate
        endDateTextView.text = currentDate
    }
}
