package com.eseul.support

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.eseul.support.fragment.CommunityFragment
import com.eseul.support.fragment.ConvenienceFragment
import com.eseul.support.fragment.DeliveryFragment
import com.eseul.support.fragments.ScheduleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout  // 전역 변수로 선언

    private var isLoggedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // drawerLayout 초기화
        drawerLayout = findViewById(R.id.drawer_layout)

        isLoggedIn = PreferenceHelper.isLoggedIn(this)

        val navigationView: NavigationView = findViewById(R.id.nav_view)

        // Navigation Menu, Bottom Navigation 설정
        setupNavigationMenu(navigationView)
        setupBottomNavigation()

        // 기본 프래그먼트를 일정 프래그먼트로 설정
        replaceFragment(ScheduleFragment())
    }

    // Navigation Drawer 설정 메서드
    private fun setupNavigationMenu(navigationView: NavigationView) {
        navigationView.setPadding(0, 150, 0, 0)

        // 메뉴 항목 동적 설정
        val menu = navigationView.menu
        menu.clear()
        navigationView.inflateMenu(R.menu.main_menu)

        if (isLoggedIn) {
            menu.findItem(R.id.login)?.isVisible = false
        } else {
            menu.findItem(R.id.Information)?.isVisible = false
            menu.findItem(R.id.inquiry)?.isVisible = false
            menu.findItem(R.id.PI)?.isVisible = false
            menu.findItem(R.id.logout)?.isVisible = false
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            handleNavigationItemSelected(menuItem.itemId)
            drawerLayout.closeDrawer(GravityCompat.START) // 전역 변수 drawerLayout 사용
            true
        }

        navigationView.invalidate()
    }

    // Bottom Navigation 설정 메서드
    private fun setupBottomNavigation() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragmentIfNotCurrent(ScheduleFragment())
                R.id.search -> replaceFragmentIfNotCurrent(ConvenienceFragment())
                R.id.food -> replaceFragmentIfNotCurrent(DeliveryFragment())
                R.id.community -> replaceFragmentIfNotCurrent(CommunityFragment())
                else -> false
            }
        }
    }

    // Navigation Drawer의 항목 선택 처리
    private fun handleNavigationItemSelected(itemId: Int) {
        when (itemId) {
            R.id.login -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.Information -> startActivity(Intent(this, InfoActivity::class.java))
            R.id.inquiry -> startActivity(Intent(this, InquiryActivity::class.java))
            R.id.PI -> startActivity(Intent(this, PrivacyActivity::class.java))
            R.id.logout -> {
                showToast("로그아웃되었습니다.")
                logoutUser()
            }
        }
    }

    // 프래그먼트를 교체하는 메서드
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // 현재 프래그먼트와 다른 경우에만 교체
    private fun replaceFragmentIfNotCurrent(newFragment: Fragment): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        return if (currentFragment?.javaClass != newFragment.javaClass) {
            replaceFragment(newFragment)
            true
        } else {
            false
        }
    }

    // 로그아웃 처리 메서드
    private fun logoutUser() {
        PreferenceHelper.clearLoginData(this)
        isLoggedIn = false
        setupNavigationMenu(findViewById(R.id.nav_view))
    }

    // 토스트 메시지 표시 메서드
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // DrawerLayout을 여는 메서드
    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)  // 전역 변수 drawerLayout 사용
    }
}
