package com.cxtapp.network.request

import com.cxtapp.network.common.MediaConst
import okhttp3.FormBody
import okhttp3.MediaType

/**
 * application/x-www-form-urlencoded
 */
class FormBodyRequest<T> : IBaseRequest<T>() {
    /**
     * multipart请求体的媒体类型
     */
    open var mediaType: MediaType = MediaConst.URLENCODED
    /**
     * 表单请求体
     * 当你设置`partBody`后当前表单请求体中的所有参数都会被存放到partBody中
     */
    open var formBody = FormBody.Builder()

    override fun getMediaType(): MediaType? {
        return mediaType
    }
}