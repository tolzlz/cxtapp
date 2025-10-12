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

package com.cxtapp.network.body

import com.cxtapp.network.interfaces.ProgressListener
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import okio.ByteString
import java.util.concurrent.ConcurrentLinkedQueue

fun RequestBody.toNetRequestBody(listeners: ConcurrentLinkedQueue<ProgressListener>? = null) = NetRequestBody(this, listeners)

fun ResponseBody.toNetResponseBody(
    listeners: ConcurrentLinkedQueue<ProgressListener>? = null, complete: (() -> Unit)? = null
) = NetResponseBody(this, listeners, complete)

/**
 * 复制一段指定长度的字符串内容
 * @param byteCount 复制的字节长度, 允许超过实际长度, 如果-1则返回完整的字符串内容
 */
fun RequestBody.peekBytes(byteCount: Long = 1024 * 1024): ByteString {
    val buffer = Buffer()
    writeTo(buffer)
    val maxSize = if (byteCount < 0) buffer.size else minOf(buffer.size, byteCount)
    return buffer.readByteString(maxSize)
}

/**
 * 复制一段指定长度的字符串内容
 * @param byteCount 复制的字节长度, 允许超过实际长度, 如果-1则返回完整的字符串内容
 */
fun ResponseBody.peekBytes(byteCount: Long = 1024 * 1024): ByteString {
    val peeked = source().peek()
    peeked.request(byteCount)
    val maxSize = if (byteCount < 0) peeked.buffer.size else minOf(byteCount, peeked.buffer.size)
    return peeked.readByteString(maxSize)
}

/**
 * 获取Content-Disposition里面的filename属性值
 * 可以此来判断是否为文件类型
 */
fun MultipartBody.Part.fileName(): String? {
    val contentDisposition = headers?.get("Content-Disposition") ?: return null
    val regex = ";\\s${"filename"}=\"(.+?)\"".toRegex()
    val matchResult = regex.find(contentDisposition)
    return matchResult?.groupValues?.getOrNull(1)
}

/**
 * 获取Content-Disposition里面的字段名称
 */
fun MultipartBody.Part.name(): String? {
    val contentDisposition = headers?.get("Content-Disposition") ?: return null
    val regex = ";\\s${"name"}=\"(.+?)\"".toRegex()
    val matchResult = regex.find(contentDisposition)
    return matchResult?.groupValues?.getOrNull(1)
}

/**
 * 将[MultipartBody.Part.body]作为字符串返回
 * 如果[MultipartBody.Part]有指定fileName那么视为文件类型将返回fileName值而不是文件内容
 */
fun MultipartBody.Part.value(): String? {
    return fileName() ?: body.peekBytes().utf8()
}