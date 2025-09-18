/*
 * MIT License
 *
 * Copyright (c) 2023 劉強東 https://github.com/liangjingkanji
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.cxtapp.network.exception

import com.cxtapp.network.cache.ForceCache
import okhttp3.Request

/**
 * 读取缓存失败
 * 仅当设置强制缓存模式[com.drake.net.cache.CacheMode.READ]和[com.drake.net.cache.CacheMode.REQUEST_THEN_READ]才会发生此异常
 * @param request 请求信息
 * @param message 错误描述信息
 * @param cause 错误原因
 */
class NoCacheException(
    request: Request,
    message: String? = null,
    cause: Throwable? = null
) : NetException(request, message, cause) {

    override fun getLocalizedMessage(): String {
        return "cacheKey = " + ForceCache.key(request) + " " + super.getLocalizedMessage()
    }
}