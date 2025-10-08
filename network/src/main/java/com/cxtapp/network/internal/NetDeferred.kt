package com.cxtapp.network.internal

import com.cxtapp.network.exception.NetException
import com.cxtapp.network.exception.URLParseException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.InternalForInheritanceCoroutinesApi

@OptIn(InternalForInheritanceCoroutinesApi::class)
internal class NetDeferred<T>(private val deferred: Deferred<T>): Deferred<T> by deferred {
    override suspend fun await(): T {
        // 追踪到网络请求异常发生位置
        val occurred = Throwable().stackTrace.getOrNull(1)?.run { " ...(${fileName}:${lineNumber})" }
        return try {
            deferred.await()
        } catch (e: Exception) {
            when {
                occurred != null && e is NetException -> e.occurred = occurred
                occurred != null && e is URLParseException -> e.occurred = occurred
            }
            throw  e
        }
    }
}