package com.cxtapp.network

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.drake.net.utils.scopeLife

class TestActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scopeLife {
            // 这里就是协程作用域内的挂起函数代码
            val data = getDataFromNetwork().await()
            updateUI(data)

            // 还可以开子协程
            launch {
                val file = downloadFile().await()
                showFile(file)
            }
        }
    }
}