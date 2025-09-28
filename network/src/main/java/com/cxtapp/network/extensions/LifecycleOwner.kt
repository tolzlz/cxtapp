package com.cxtapp.network.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

fun LifecycleOwner.cancelScopeOn(scope: CoroutineScope, event: Lifecycle.Event) {
    val observer = LifecycleEventObserver { _, e ->
        if (e == event) {
            scope.coroutineContext.cancel() // 取消这个 Scope
            lifecycle.removeObserver(this) // 避免泄漏
        }
    }
    lifecycle.addObserver(observer)
}
