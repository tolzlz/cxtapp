package com.cxtapp.network.base

import com.cxtapp.network.common.MediaConst
import com.cxtapp.network.common.Method
import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.Request

interface IRequest<T> {
    /**
     * @return HttpUrl
     */
    fun getHttpUrl(): HttpUrl? = null

    /**
     * @return 根据以上定义的方法构建一个请求
     */
    fun buildRequest(): Request? = null

    /**
     * @return 请求方法，GET、POST等
     */
    fun getMethod(): Method? = null

    /**
     * @return 请求类型
     */
    fun getMediaType(): MediaType? = null

}