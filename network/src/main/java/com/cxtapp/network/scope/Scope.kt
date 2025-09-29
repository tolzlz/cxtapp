package com.cxtapp.network.scope

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.drake.net.utils.runMain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class Scope(lifecycleOwner: LifecycleOwner? = null,
                 lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
                 val dispatcher: CoroutineDispatcher = Dispatchers.Main) : CoroutineScope {


    init {
        runMain {
            lifecycleOwner?.lifecycle?.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (lifeEvent == event) cancel()
                }
            })
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        catch(throwable)
    }

    protected var catch: (Scope.(Throwable) -> Unit)? = null
    protected var finally: (Scope.(Throwable?) -> Unit)? = null


    override val coroutineContext: CoroutineContext =
        dispatcher + exceptionHandler + SupervisorJob()


    open fun launch(block: suspend CoroutineScope.() -> Unit): Scope {
        launch(EmptyCoroutineContext) {
            block()
        }.invokeOnCompletion {
            finally(it)
        }
        return this
    }
    protected fun catch(e: Throwable) {
        catch?.invoke(this@Scope,e) ?: handleError(e)
    }
    /**
     * @param e 如果发生异常导致作用域执行完毕, 则该参数为该异常对象, 正常结束则为null
     */
    protected fun finally(e: Throwable?) {
        finally?.invoke(this@Scope,e)
    }

    /**
     * 当作用域内发生异常时回调
     */
    open fun catch(block: Scope.(Throwable) -> Unit = {}): Scope {
        this.catch  = block
        return this
    }
    /**
     * 无论正常或者异常结束都将最终执行
     */
    open fun finally(block: Scope.(Throwable?) -> Unit = {}): Scope {
        this.finally = block
        return this
    }


    /**
     * 错误处理
     */
    open fun handleError(e: Throwable) {
        //TODO
    }

    open fun cancel(cause: CancellationException? = null) {
        val job = coroutineContext[Job]
            ?: error("Scope cannot be cancelled because it does not have a job: $this")
        job.cancel(cause)
    }


}