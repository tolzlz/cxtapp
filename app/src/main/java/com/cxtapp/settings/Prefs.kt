package com.cxtapp.settings

import com.cxtapp.R

object Prefs {
    var currentThemeId
        get() = PrefsIoUtil.getInt(R.string.preference_key_color_theme,fallback.marshallingId)
        set(theme: Int): Unit = PrefsIoUtil.setInt(R.string.preference_key_color_theme, theme)
}