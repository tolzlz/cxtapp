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
     * 再判断globle
     */
    fun setPath(reqUrl: String) {
        val url = reqUrl.toHttpUrlOrNull()
        if (url == null) {
            throw URLParseException(reqUrl)
        } else {
            this.httpUrl = url.newBuilder()
        }
    }

    /**
     * 先解析reqUrl，如果没有host，则去判断host+reqUrl
     */
    fun setPath(host:String, reqUrl: String) {
        val url = reqUrl.toHttpUrlOrNull()
        if (url == null) {
            try {
                httpUrl = (host + reqUrl).toHttpUrl().newBuilder()
            } catch (e: Throwable) {
                throw URLParseException(reqUrl, e)
            }
        } else {
            this.httpUrl = url.newBuilder()
        }
    }

    /**
     * 解析配置Path, 支持识别query参数和绝对路径
     * @param path 如果其不包含http/https则会自动拼接
     * 先判断host
     * 再判断goble
     */
    fun setPath(url: HttpUrl) {
        httpUrl = url.newBuilder()
    }
}