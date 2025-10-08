package com.cxtapp.network

import android.content.Context
import okhttp3.OkHttpClient

class NetFactory{
    private var mNetClient:NetClient? = null

    constructor(netClient: NetClient? = null) {
        mNetClient = netClient
    }
    companion object {
        public var mContext: Context? = null



        val instance: NetFactory by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            NetFactory()
        }

        fun initialize(host:String = "",context: Context? = null,config: OkHttpClient.Builder.() -> Unit = {}) {

        }

        fun initialize(host: String = "",context: Context? = null,config: OkHttpClient.Builder) {

        }

//        fun test() {
//            initialize {
//                connectTimeout(15, TimeUnit.SECONDS)
//                readTimeout(20, TimeUnit.SECONDS)
//            }
//            initialize(config = OkHttpClient.Builder().apply {
//                connectTimeout(15, TimeUnit.SECONDS)
//                readTimeout(20, TimeUnit.SECONDS)
//            })
//        }
    }
}