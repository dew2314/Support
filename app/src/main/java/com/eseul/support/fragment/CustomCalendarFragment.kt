package com.eseul.support.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.eseul.support.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CustomCalendarFragment : Fragment() {

    private lateinit var dateText: TextView
    private lateinit var calendarGrid: GridLayout
    private var calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_custom_calendar, container, false)

        setupCustomCalendar(rootView)

        return rootView
    }

    private fun setupCustomCalendar(view: View) {
        val calendarContainer: LinearLayout = view.findViewById(R.id.calendarContainer)

        // 월/연도와 이전/다음 버튼 추가
        val headerLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // 좌우 버튼 생성
        val prevButton = ImageButton(requireContext()).apply {
            setImageResource(R.drawable.ic_left_arrow)
            setBackgroundColor(android.graphics.Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val nextButton = ImageButton(requireContext()).apply {
            setImageResource(R.drawable.ic_right_arrow)
            setBackgroundColor(android.graphics.Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // 년도 텍스트 설정
        dateText = TextView(requireContext()).apply {
            textSize = 16f
            setPadding(16, 0, 16, 0)
            gravity = Gravity.CENTER
            setTextColor(android.graphics.Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1f
            }
        }

        // 버튼과 텍스트를 LinearLayout에 추가
        headerLayout.addView(prevButton)
        headerLayout.addView(dateText)
        headerLayout.addView(nextButton)

        calendarContainer.addView(headerLayout)

        // 구분선 추가
        val divider = View(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                resources.getDimensionPixelSize(R.dimen.divider_height)
            ).apply {
                topMargin = resources.getDimensionPixelSize(R.dimen.divider_top_margin)
                bottomMargin = 9
            }
            setBackgroundColor(android.graphics.Color.parseColor("#E4E4E4"))
        }

        calendarContainer.addView(divider)

        // 요일 레이아웃 설정
        val daysOfWeekLayout = GridLayout(requireContext()).apply {
            rowCount = 1
            columnCount = 7
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 31
            }
        }

        val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")
        daysOfWeek.forEachIndexed { index, day ->
            val textView = TextView(requireContext()).apply {
                text = day
                gravity = Gravity.CENTER
                textSize = 16f
                setTextColor(
                    when (index) {
                        0 -> android.graphics.Color.RED
                        6 -> android.graphics.Color.BLUE
                        else -> android.graphics.Color.BLACK
                    }
                )
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(index, 1f)
                    rowSpec = GridLayout.spec(0)
                    setMargins(4, 4, 4, 4)
                }
            }
            daysOfWeekLayout.addView(textView)
        }

        calendarContainer.addView(daysOfWeekLayout)

        // 날짜 레이아웃 설정
        calendarGrid = GridLayout(requireContext()).apply {
            rowCount = 6
            columnCount = 7
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        calendarContainer.addView(calendarGrid)

        prevButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }

        nextButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }

        updateCalendar()
    }

    private fun updateCalendar() {
        val dateFormat = SimpleDateFormat("yyyy년 M월", Locale.getDefault())
        dateText.text = dateFormat.format(calendar.time)

        calendarGrid.removeAllViews()

        val monthStart = calendar.clone() as Calendar
        monthStart.set(Calendar.DAY_OF_MONTH, 1)
        var startDayOfWeek = monthStart.get(Calendar.DAY_OF_WEEK) - 1

        // 이전 달의 마지막 날 설정
        val prevMonth = calendar.clone() as Calendar
        prevMonth.add(Calendar.MONTH, -1)
        val lastDayOfPrevMonth = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH)

        // 요일 위치에 맞춰서 빈 공간을 추가 (이전 달의 날짜)
        for (i in 0 until startDayOfWeek) {
            val emptyView = TextView(requireContext()).apply {
                text = (lastDayOfPrevMonth - startDayOfWeek + i + 1).toString() // 이전 달의 날짜
                gravity = Gravity.CENTER
                textSize = 16f
                setTextColor(android.graphics.Color.parseColor("#CACACA")) // 이전 달 날짜 색상 설정
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(i, 1f)
                    rowSpec = GridLayout.spec(0)
                    setMargins(34, 75, 34, 30)
                }
            }
            calendarGrid.addView(emptyView)
        }

        var maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        var dayCounter = 1
        var currentRow = 0

        while (dayCounter <= maxDay) {
            for (i in startDayOfWeek until 7) {
                if (dayCounter > maxDay) break
                val dayView = TextView(requireContext()).apply {
                    text = dayCounter.toString()
                    gravity = Gravity.CENTER
                    textSize = 16f
                    setTextColor(android.graphics.Color.BLACK)
                    val topMargin = if (currentRow == 0) 75 else 0
                    val leftRightMargin = 34
                    val bottomMargin = 30

                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0
                        height = GridLayout.LayoutParams.WRAP_CONTENT
                        columnSpec = GridLayout.spec(i, 1f)
                        rowSpec = GridLayout.spec(currentRow)
                        setMargins(
                            leftRightMargin,
                            topMargin,
                            leftRightMargin,
                            bottomMargin
                        )
                    }
                }
                calendarGrid.addView(dayView)
                dayCounter++
            }
            startDayOfWeek = 0
            currentRow++
        }

        var lastDate = Calendar.getInstance()
        Log.d("TAG", "updateCalendar: ${lastDate}")
        Log.d("TAG", "updateCalendar: ${calendar}")
        Log.d("TAG", "updateCalendar: ${calendar.get(Calendar.MONTH)}")
        lastDate.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        lastDate.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        lastDate.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))


        Log.d(
            "TAG",
            "updateCalendar: $maxDay $dayCounter $currentRow ${lastDate.get(Calendar.DAY_OF_WEEK)} ${lastDate}"
        )

        maxDay = 7 - lastDate.get(Calendar.DAY_OF_WEEK)
        dayCounter = 1
        currentRow -= 1

        while (dayCounter <= maxDay) {
            for (i in lastDate.get(Calendar.DAY_OF_WEEK) until 7) {
                val dayView = TextView(requireContext()).apply {
                    text = dayCounter.toString()
                    gravity = Gravity.CENTER
                    textSize = 16f
                    setTextColor(android.graphics.Color.parseColor("#CACACA"))
                    val topMargin = if (currentRow == 0) 75 else 0
                    val leftRightMargin = 34
                    val bottomMargin = 30

                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0
                        height = GridLayout.LayoutParams.WRAP_CONTENT
                        columnSpec = GridLayout.spec(i, 1f)
                        rowSpec = GridLayout.spec(currentRow)
                        setMargins(
                            leftRightMargin,
                            topMargin,
                            leftRightMargin,
                            bottomMargin
                        )
                    }
                }
                calendarGrid.addView(dayView)
                dayCounter++
            }
        }
    }
}
