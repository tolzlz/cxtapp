package com.cxtapp.network

import com.cxtapp.network.utils.scopeNet
import kotlinx.coroutines.launch
import java.io.File

class NetTest {

    fun add() {
        scopeNet {
            val await = Post<String>("path").await()

            launch {
                val task = Post<String>("path/error").await()  // 此时发生请求错误
//                val file = Get<File>(Api.FILE).await()
            }.invokeOnCompletion {
                // A
            }
        }.catch {
            // B
        }
    }
}