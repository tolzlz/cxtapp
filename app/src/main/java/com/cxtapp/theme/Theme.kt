package com.cxtapp.theme
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.cxtapp.R

enum class Theme(val marshallingId: Int, val tag: String, @field:StyleRes @get:StyleRes
@param:StyleRes val resourceId: Int, @field:StringRes @get:StringRes @param:StringRes val nameId: Int) {
    LIGHT(0, "light", 0, R.string.color_theme_light),
    DARK(1, "dark", R.style.ThemeDark, R.string.color_theme_dark),
    BLACK(2, "black", R.style.ThemeBlack, R.string.color_theme_black),
    SEPIA(3, "sepia", R.style.ThemeSepia, R.string.color_theme_sepia);

    companion object {
        val fallback: Theme
            get() = LIGHT
    }
}