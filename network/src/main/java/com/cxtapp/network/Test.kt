package com.cxtapp.network

import rxhttp.toFlow
import rxhttp.wrapper.param.RxHttp

class Test {
    fun add (){
        RxHttp.get("/server/..")
            .add("key", "value")
            .toFlow<User>()
            .catch {
                //Failure
            }.collect {
                //Success
            }
    }
}