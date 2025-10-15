package com.cxtapp.network.request.body

import com.cxtapp.network.request.MediaConst
import com.google.protobuf.MessageLite
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.IOException

/*
 * Copyright (C) 2025 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * update by  retrofit2.converter.protobuf.ProtoStreamingRequestBody
 */

class ProtoRequestBody(private val value: MessageLite) : RequestBody() {
    override fun contentType(): MediaType {
        return MediaConst.PROTOBUF
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        value.writeTo(sink.outputStream())
    }

    class Builder(){
        var value: MessageLite? = null

        fun addMessage(message: MessageLite) = apply {
            this.value = message
        }


        fun build(): ProtoRequestBody {
            check(value != null) { "ProtoRequest Body must have at least one part." }
            return ProtoRequestBody(value!!)
        }
    }
}
