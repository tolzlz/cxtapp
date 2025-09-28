package com.cxtapp.network.scope

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.launch

abstract class BaseScope(lifecycleOwner: LifecycleOwner? = null,
                lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
                dispatcher: CoroutineDispatcher = Dispatchers.Main): Scope(lifecycleOwner,lifeEvent,dispatcher) {
    /** 预览模式 */
    protected var preview: (suspend CoroutineScope.() -> Unit)? = null

    /** 是否启用预览 */
    protected var previewEnabled = true

    /** 是否读取缓存成功 */
    protected var previewSucceed = false
        get() = if (preview != null) field else false

    override fun launch(block: suspend CoroutineScope.() -> Unit): BaseScope {
        launch(EmptyCoroutineContext) {
            if (preview != null && previewEnabled) {
                supervisorScope {
                    previewSucceed = try {
                        preview?.invoke(this)
                        true
                    }catch (e: Exception) {
                        false
                    }
                }
            }
            block()
        }.invokeOnCompletion{
            finally(it)
        }
        return this
    }

    protected open fun start();

}