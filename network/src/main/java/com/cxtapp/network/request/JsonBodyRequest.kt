package com.cxtapp.network.request

import com.cxtapp.network.common.MediaConst
import com.cxtapp.network.request.body.JsonBody
import okhttp3.MediaType
import okhttp3.MultipartBody

class JsonBodyRequest<T>: IBaseRequest<T>() {
    /**
     * multipart请求体的媒体类型
     */
    open var mediaType: MediaType = MediaConst.JSON
    /**
     * multipart请求体
     * 主要存放文件/IO流
     */
    open var jsonBody = JsonBody.Builder()

    override fun getMediaType(): MediaType? {
        return mediaType
    }
}