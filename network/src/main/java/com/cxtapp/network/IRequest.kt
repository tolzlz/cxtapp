package com.cxtapp.network

import okhttp3.Headers

interface IRequest {
    /**
     * @return 带查询参数参数的url
     */
    fun getUrl(): String?

    /**
     * @return 请求方法，GET、POST等
     */
    fun getMethod(): Method?

    /**
     * @return 请求头信息
     */
    fun getHeaders(): Headers?
}