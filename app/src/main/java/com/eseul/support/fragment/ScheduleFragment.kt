package com.eseul.support.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.eseul.support.AddNoticeActivity
import com.eseul.support.MainActivity
import com.eseul.support.R
import com.eseul.support.fragment.CustomCalendarFragment

class ScheduleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        // + 버튼 클릭 시 공지사항 등록 화면으로 이동
        val addNoticeButton: ImageButton = view.findViewById(R.id.add_notice_button)
        addNoticeButton.setOnClickListener {
            startActivity(Intent(activity, AddNoticeActivity::class.java))
        }

        // menu_button을 눌렀을 때 드로어 메뉴 열기
        val menuButton: ImageButton = view.findViewById(R.id.menu_button)
        menuButton.setOnClickListener {
            (activity as? MainActivity)?.openDrawer() // MainActivity의 openDrawer 메서드 호출
        }

        // CustomCalendarFragment를 가운데에 추가
        childFragmentManager.beginTransaction()
            .replace(R.id.calendar_fragment_container, CustomCalendarFragment())
            .commit()

        return view
    }
}
