package com.cxtapp.network.request

import android.net.Uri
import com.cxtapp.network.common.MediaConst
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.ByteString
import java.io.File

/**
 * multipart/form-data
 */
class MultipartBodyRequest<T>: IBaseRequest<T>() {
    /**
     * multipart请求体的媒体类型
     */
    open var mediaType: MediaType = MediaConst.FORM
    /**
     * multipart请求体
     * 主要存放文件/IO流
     */
    open var partBody = MultipartBody.Builder()

    fun param(name: String, value: RequestBody?) {
        value ?: return
        partBody.addFormDataPart(name, null, value)
    }

    fun param(name: String, filename: String?, value: RequestBody?) {
        value ?: return
        partBody.addFormDataPart(name, filename, value)
    }

    fun param(name: String, value: ByteString?) {
        value ?: return
        partBody.addFormDataPart(name, null, value.toRequestBody())
    }

    fun param(name: String, value: ByteArray?) {
        value ?: return
        partBody.addFormDataPart(name, null, value.toRequestBody())
    }

    fun param(name: String, value: Uri?) {
        value ?: return
        partBody.addFormDataPart(name, value.fileName(), value.toRequestBody())
    }

    fun param(name: String, value: File?) {
        value ?: return
        partBody.addFormDataPart(name, value.name, value.toRequestBody())
    }


    override fun getMediaType(): MediaType? {
        return mediaType
    }

}