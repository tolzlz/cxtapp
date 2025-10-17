package com.cxtapp

import android.app.Application
import com.cxtapp.settings.Prefs
import com.cxtapp.theme.Theme

class RunApplication : Application() {

    var currentTheme = Theme.fallback
        set(value) {
            if (value != field) {
                field = value
                Prefs.
            }
        }

    companion object {
        lateinit var instance: RunApplication
            private  set
    }
}