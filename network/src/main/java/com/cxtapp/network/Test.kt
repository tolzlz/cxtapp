package com.cxtapp.network

import rxhttp.toFlow
import rxhttp.wrapper.param.RxHttp
import java.io.File

class Test {
    fun add (){
        RxHttp.postForm("/service/...")          //post FormBody
            .add("key", "value")                 //add param to body
            .addQuery("key1", "value1")          //add query param
            .addFile("file", File(".../1.png"))  //add file to body
            .toObservable<Any>()
            .subscribe({ student ->
                //Default IO thread
            }, { throwable ->

            })
    }
}