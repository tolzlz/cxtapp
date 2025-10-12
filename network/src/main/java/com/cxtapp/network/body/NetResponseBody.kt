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
package com.cxtapp.network.body

import android.os.SystemClock
import com.cxtapp.network.component.Progress
import com.cxtapp.network.interfaces.ProgressListener
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException
import java.util.concurrent.ConcurrentLinkedQueue

class NetResponseBody(
    private val body: ResponseBody,
    private val progressListeners: ConcurrentLinkedQueue<ProgressListener>? = null,
    private val complete: (() -> Unit)? = null
) : ResponseBody() {

    private val progress = Progress()
    private val bufferedSource by lazy { body.source().toProgress().buffer() }
    private val contentLength by lazy { body.contentLength() }

    override fun contentType(): MediaType? {
        return body.contentType()
    }

    override fun contentLength(): Long {
        return contentLength
    }

    override fun source(): BufferedSource {
        return bufferedSource
    }

    private fun Source.toProgress() = object : ForwardingSource(this) {
        private var readByteCount: Long = 0

        @Throws(IOException::class)
        override fun read(sink: Buffer, byteCount: Long): Long {
            try {
                val bytesRead = super.read(sink, byteCount)
                if (!progressListeners.isNullOrEmpty()) {
                    readByteCount += if (bytesRead != -1L) bytesRead else 0
                    val currentElapsedTime = SystemClock.elapsedRealtime()
                    progressListeners.forEach { progressListener ->
                        progressListener.intervalByteCount += if (bytesRead != -1L) bytesRead else 0
                        val currentInterval = currentElapsedTime - progressListener.elapsedTime
                        if (!progress.finish && (readByteCount == contentLength || bytesRead == -1L || currentInterval >= progressListener.interval)) {
                            if (readByteCount == contentLength || bytesRead == -1L) {
                                progress.finish = true
                            }
                            progressListener.onProgress(
                                progress.apply {
                                    currentByteCount = readByteCount
                                    totalByteCount = contentLength
                                    intervalByteCount = progressListener.intervalByteCount
                                    intervalTime = currentInterval
                                }
                            )
                            progressListener.elapsedTime = currentElapsedTime
                            progressListener.intervalByteCount = 0L
                        }
                    }
                }
                if (bytesRead == -1L) complete?.invoke()
                return bytesRead
            } catch (e: Exception) {
                complete?.invoke()
                throw e
            }
        }
    }
}