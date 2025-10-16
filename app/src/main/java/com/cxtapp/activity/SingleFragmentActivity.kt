package com.cxtapp.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cxtapp.R

abstract class SingleFragmentActivity<T : Fragment> : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateAndSetContentView()
    }
    protected open fun inflateAndSetContentView() {
        setContentView(R.layout.activity_single_fragment)
    }
}