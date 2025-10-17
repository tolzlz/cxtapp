package com.cxtapp.settings

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.annotation.StringRes
import androidx.core.content.edit
import com.cxtapp.RunApplication

object PrefsIoUtil {

    fun getInt(@StringRes id: Int,defaultValue: Int): Int {
        return getInt(getKey(id), defaultValue)
    }

    fun setInt(@StringRes id: Int, value: Int):Unit {
        return setInt(getKey(id), value)
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        return preferences.getInt(key,defaultValue)
    }

    fun setInt(key: String?, value: Int): Unit {
        preferences.edit(commit = false) { putInt(key,value) }
    }
    fun getKey(@StringRes id: Int, vararg formatArgs: Any?): String {
        return RunApplication.instance.resources.getString(id,*formatArgs)
    }

    private val preferences get() = PreferenceManager.getDefaultSharedPreferences(RunApplication.instance)
}