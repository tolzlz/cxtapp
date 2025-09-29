package com.cxtapp.network.scope

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ViewScope(
    val view: View,
    dispatcher: CoroutineDispatcher = Dispatchers.Main
): BaseScope(dispatcher = dispatcher) {
    override fun start() {}

    init {
        view.findViewTreeLifecycleOwner()?.lifecycle?.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) cancel()
            }
        })
    }
}