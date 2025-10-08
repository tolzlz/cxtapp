package com.cxtapp.network.request.body

import com.cxtapp.network.common.MediaConst
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import com.google.protobuf.MessageLite

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.IOException
import okio.buffer
import okio.sink

class ProtobufBody private constructor(
    private val data: ByteArray,
    private val mediaType: MediaType = MediaConst.PROTOBUF
) : RequestBody() {

    override fun contentType(): MediaType = mediaType

    override fun contentLength(): Long = data.size.toLong()

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        sink.write(data)
    }

    companion object {
        fun create(message: MessageLite): ProtobufBody {
            return ProtobufBody(message.toByteArray())
        }

        fun create(bytes: ByteArray): ProtobufBody {
            return ProtobufBody(bytes)
        }
    }

    class Builder {
        private var data: ByteArray? = null
        private var mediaType: MediaType = MediaConst.PROTOBUF

        fun setMessage(message: MessageLite) = apply {
            this.data = message.toByteArray()
        }

        fun setBytes(bytes: ByteArray) = apply {
            this.data = bytes
        }

        fun setMediaType(type: MediaType) = apply {
            this.mediaType = type
        }

        fun build(): ProtobufBody {
            requireNotNull(data) { "Protobuf data cannot be null" }
            return ProtobufBody(data!!, mediaType)
        }
    }
}
