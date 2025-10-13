package com.cxtapp.network.request.body

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink

class ProtobufBody: RequestBody() {
    override fun contentType(): MediaType? {
        TODO("Not yet implemented")
    }

    override fun writeTo(sink: BufferedSink) {
        TODO("Not yet implemented")
    }
}