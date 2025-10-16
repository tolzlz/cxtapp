package com.cxtapp.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cxtapp.activity.SingleFragmentActivity
import com.cxtapp.databinding.ActivityHomeBinding

class HomeActivity : SingleFragmentActivity<Fragment>() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun inflateAndSetContentView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}