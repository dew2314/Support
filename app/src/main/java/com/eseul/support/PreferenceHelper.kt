package com.eseul.support

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {

    private const val PREFERENCES_NAME = "app_prefs"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    // 로그인 상태 저장
    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    // 로그인 상태 불러오기
    fun isLoggedIn(context: Context): Boolean {
        return getPreferences(context).getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // 액세스 토큰 저장
    fun setAccessToken(context: Context, token: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_ACCESS_TOKEN, token)
        editor.apply()
    }

    // 액세스 토큰 불러오기
    fun getAccessToken(context: Context): String? {
        return getPreferences(context).getString(KEY_ACCESS_TOKEN, null)
    }

    // 리프레시 토큰 저장
    fun setRefreshToken(context: Context, token: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_REFRESH_TOKEN, token)
        editor.apply()
    }

    // 리프레시 토큰 불러오기
    fun getRefreshToken(context: Context): String? {
        return getPreferences(context).getString(KEY_REFRESH_TOKEN, null)
    }

    // 모든 로그인 데이터 삭제 (로그아웃 시 사용)
    fun clearLoginData(context: Context) {
        val editor = getPreferences(context).edit()
        editor.remove(KEY_IS_LOGGED_IN)
        editor.remove(KEY_ACCESS_TOKEN)
        editor.remove(KEY_REFRESH_TOKEN)
        editor.apply()
    }
}
