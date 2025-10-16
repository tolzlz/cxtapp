package com.cxtapp.navtab.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.google.android.material.bottomnavigation.BottomNavigationView


class NavTabLayout(context: Context, attrs: AttributeSet): LinearLayout(context,attrs){
    init {
        orientation = HORIZONTAL
    }
}