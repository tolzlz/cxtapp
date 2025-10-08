package com.cxtapp.network.request.body

import com.cxtapp.network.common.MediaConst
import java.io.IOException
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okio.BufferedSink
import org.json.JSONObject

class JsonBody private constructor(
    private val json: String,
    private val mediaType: MediaType = MediaConst.JSON
) : RequestBody() {

    override fun contentType(): MediaType = mediaType

    override fun contentLength(): Long = json.toByteArray(Charsets.UTF_8).size.toLong()

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        sink.writeUtf8(json)
    }

    companion object {

        fun create(json: String): JsonBody {
            return JsonBody(json)
        }
    }

    // 🔧 Builder
    class Builder {
        private val jsonObject = JSONObject()

        fun add(key: String, value: Any?): Builder {
            jsonObject.put(key, value)
            return this
        }

        fun addAll(map: Map<String, Any?>): Builder {
            for ((k, v) in map) {
                jsonObject.put(k, v)
            }
            return this
        }

        fun build(): JsonBody {
            return JsonBody(jsonObject.toString())
        }
    }
}
