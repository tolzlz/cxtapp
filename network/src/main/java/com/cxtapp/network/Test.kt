package com.cxtapp.network

import com.cxtapp.network.Post
import com.cxtapp.network.utils.scopeNet
import kotlinx.coroutines.launch
import rxhttp.toFlow
import rxhttp.wrapper.param.RxHttp
import java.io.File

class Test {
    fun add (){
//        RxHttp.postForm("/service/...")          //post FormBody
//            .add("key", "value")                 //add param to body
//            .addQuery("key1", "value1")          //add query param
//            .addFile("file", File(".../1.png"))  //add file to body
//            .toObservable<Any>()
//            .subscribe({ student ->
//                //Default IO thread
//            }, { throwable ->
//
//            })
        scopeNet {
            val await = Post<String>("path").await()

            launch {
                val task = Post<String>("path/error").await()  // 此时发生请求错误
                val file = Get<File>(Api.FILE).await()
            }.invokeOnCompletion {
                // A
            }
        }.catch {
            // B
        }
    }

}