package com.cxtapp.network.request

import com.cxtapp.network.base.IRequest
import com.drake.net.exception.URLParseException
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

abstract class IBaseRequest<T> : IRequest<T> {

    open var httpUrl: HttpUrl.Builder = HttpUrl.Builder()

    /**
     * 解析配置Path, 支持识别query参数和绝对路径
     * @param path 如果其不包含http/https则会自动拼接
     * 先判断host
     * 再判断goble
     */
    fun setPath(host: String?,path: String?) {
        val url = path?.toHttpUrlOrNull()
        if (url == null) {
            try {
                httpUrl = (host + path).toHttpUrl().newBuilder()
            } catch (e: Throwable) {
                throw URLParseException(host + path, e)
            }
        } else {
            this.httpUrl = url.newBuilder()
        }
    }
}