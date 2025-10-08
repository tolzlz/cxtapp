package com.cxtapp.network.exception

/**
 * URL地址错误
 *
 * @param message 错误描述信息
 * @param cause 错误原因
 */
open class URLParseException(
    message: String? = null,
    cause: Throwable? = null,
) : Exception(message, cause) {

    var occurred: String = ""

    override fun getLocalizedMessage(): String? {
        return super.getLocalizedMessage() + occurred
    }
}