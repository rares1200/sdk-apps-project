package ro.weekendrrsapps.sdk.prefs

import android.content.Context
import android.content.SharedPreferences

class AppPrefs(ctx: Context, prefsName: String) {


    val mPrefs = ctx.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        mPrefs?.edit()?.putString(key, value)?.apply()
    }

    fun getString(key: String): String {
        return mPrefs?.getString(key,"")?:""
    }

    fun saveBoolean(key: String, value: Boolean) {
        mPrefs?.edit()?.putBoolean(key, value)?.apply()
    }

    fun getBoolean(key: String): Boolean {
        return mPrefs?.getBoolean(key, false)?:false
    }

    fun saveInt(key: String, value: Int) {
        mPrefs?.edit()?.putInt(key, value)?.apply()
    }

    fun getInt(key: String): Int {
        return mPrefs?.getInt(key, 0)?:0
    }

    fun saveLong(key: String, value: Long) {
        mPrefs?.edit()?.putLong(key, value)?.apply()
    }

    fun getLong(key: String, fallback: Long = 0L): Long {
        return mPrefs?.getLong(key, fallback)?:fallback
    }

}