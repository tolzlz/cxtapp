package com.cxtapp.network.request

import com.cxtapp.network.common.MediaConst
import com.cxtapp.network.request.body.ProtobufBody
import okhttp3.MediaType

class ProtobufRequest<T>: IBaseRequest<T>() {

    /**
     * multipart请求体的媒体类型
     */
    open var mediaType: MediaType = MediaConst.PROTOBUF
    /**
     * multipart请求体
     * 主要存放文件/IO流
     */
    open var protobufBody = ProtobufBody.Builder()

    override fun getMediaType(): MediaType? {
        return mediaType
    }
}