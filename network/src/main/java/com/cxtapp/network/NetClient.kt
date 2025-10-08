package com.cxtapp.network

import com.cxtapp.network.base.IRequest
import com.cxtapp.network.common.MediaConst
import com.cxtapp.network.internal.NetDeferred
import com.cxtapp.network.request.IBaseRequest
import com.cxtapp.network.request.JsonBodyRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.ensureActive

class NetClient {


    /**
     * 同步请求
     */
    public fun <T> reqSync(request: IRequest<T>, reqCache: Boolean = false) {

    }

    /**
     * 协程请求
     */
    public fun <T> reqCoro(request: IRequest<T>, reqCache: Boolean  = false,scope: CoroutineScope): Deferred<T>  =
        NetDeferred(scope.async(Dispatchers.IO + SupervisorJob()) {
            coroutineContext.ensureActive()
            val mediaType = request.getMediaType()

            val result = when (mediaType) {
                MediaConst.JSON -> {
                    JsonBodyRequest().apply {
                        setPath(request.getHttpUrl())
                        method = Method.POST
                        setGroup(coroutineContext[CoroutineExceptionHandler])
                        tag(tag)
                        block?.invoke(this)
                    }.execute()
                }

                MediaConst.URLENCODED -> {

                }

                MediaConst.PROTOBUF -> {

                }

                else -> throw IllegalArgumentException("不支持的 MediaType: $mediaType")
            }

            result // Deferred<T> 的返回值
        })


}