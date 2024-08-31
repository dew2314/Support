package com.eseul.support

import android.content.Intent
import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private var isLoggedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 로그인 상태 확인
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)

        // 메뉴 패딩 조정
        navigationView.setPadding(0, 150, 0, 0)

        // 메뉴 항목 동적 설정
        setupNavigationMenu(navigationView)

        // 메뉴 버튼 설정
        val menuButton: ImageButton = findViewById(R.id.menu_button)
        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Navigation Drawer의 항목 선택 이벤트 처리
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.login -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish() // MainActivity 종료
                }
                R.id.Information -> {
                    val intent = Intent(this, InfoActivity::class.java)
                    startActivity(intent)
                }
                R.id.inquiry -> {
                    val intent = Intent(this, InquiryActivity::class.java)
                    startActivity(intent)
                }
                R.id.PI -> {
                    val intent = Intent(this, PiActivity::class.java)
                    startActivity(intent)
                }
                R.id.logout -> {
                    showToast("로그아웃되었습니다.")
                    saveLoginStatus(false) // 로그아웃 처리 및 메뉴 업데이트
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // 프래그먼트 매니저를 사용하여 캘린더 프래그먼트를 추가합니다.
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, CustomCalendarFragment())
        fragmentTransaction.commit()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    showToast("공지사항 선택됨")
                    true
                }
                R.id.search -> {
                    showToast("생활편의 선택됨")
                    true
                }
                R.id.food -> {
                    showToast("공동배달 선택됨")
                    true
                }
                R.id.community -> {
                    showToast("커뮤니티 선택됨")
                    true
                }
                else -> false
            }
        }

        // + 버튼 클릭 시 공지사항 등록 화면으로 이동
        val addNoticeButton: ImageButton = findViewById(R.id.add_notice_button)
        addNoticeButton.setOnClickListener {
            val intent = Intent(this, AddNoticeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveLoginStatus(isLoggedIn: Boolean) {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        editor.apply()
        this.isLoggedIn = isLoggedIn

        // 상태 변경 후 메뉴 업데이트
        setupNavigationMenu(findViewById(R.id.nav_view))
    }

    private fun setupNavigationMenu(navigationView: NavigationView) {
        val menu = navigationView.menu
        menu.clear() // 기존 메뉴 제거

        // 로그인 여부에 따라 메뉴 항목을 동적으로 추가
        navigationView.inflateMenu(R.menu.main_menu)
        if (isLoggedIn) {
            menu.findItem(R.id.login)?.isVisible = false
        } else {
            menu.findItem(R.id.Information)?.isVisible = false
            menu.findItem(R.id.inquiry)?.isVisible = false
            menu.findItem(R.id.PI)?.isVisible = false
            menu.findItem(R.id.logout)?.isVisible = false
        }

        // UI 강제 갱신
        navigationView.invalidate()
        navigationView.requestLayout()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
