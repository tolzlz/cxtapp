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

@file:Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST")

package com.cxtapp.network.convert

import com.cxtapp.network.NetConfig
import com.cxtapp.network.exception.ConvertException
import com.cxtapp.network.exception.RequestParamsException
import com.cxtapp.network.exception.ResponseException
import com.cxtapp.network.exception.ServerResponseException
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type


/**
 * 常见的JSON转换器实现, 如果不满意继承实现自定义的业务逻辑
 *
 * @param success  后端定义为成功状态的错误码值
 * @param code  错误码在JSON中的字段名
 * @param message  错误信息在JSON中的字段名
 */
abstract class JSONConvert(
    val success: String = "0",
    val code: String = "code",
    val message: String = "msg"
) : NetConverter {

    override fun <R> onConvert(succeed: Type, response: Response): R? {
        try {
            return NetConverter.onConvert<R>(succeed, response)
        } catch (e: ConvertException) {
            val code = response.code
            when {
                code in 200..299 -> { // 请求成功
                    val bodyString = response.body?.string() ?: return null
                    return try {
                        val json = JSONObject(bodyString) // 获取JSON中后端定义的错误码和错误信息
                        val srvCode = json.getString(this.code)
                        if (srvCode == success) { // 对比后端自定义错误码
                            bodyString.parseBody<R>(succeed)
                        } else { // 错误码匹配失败, 开始写入错误异常
                            val errorMessage = json.optString(message, NetConfig.app.getString(com.drake.net.R.string.no_error_message))
                            throw ResponseException(response, errorMessage, tag = srvCode) // 将业务错误码作为tag传递
                        }
                    } catch (e: JSONException) { // 固定格式JSON分析失败直接解析JSON
                        bodyString.parseBody<R>(succeed)
                    }
                }

                code in 400..499 -> throw RequestParamsException(response, code.toString()) // 请求参数错误
                code >= 500 -> throw ServerResponseException(response, code.toString()) // 服务器异常错误
                else -> throw ConvertException(response, message = "Http status code not within range")
            }
        }
    }

    /**
     * 反序列化JSON
     *
     * @param succeed JSON对象的类型
     * @receiver 原始字符串
     */
    abstract fun <R> String.parseBody(succeed: Type): R?
}
