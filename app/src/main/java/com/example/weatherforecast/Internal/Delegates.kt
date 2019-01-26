package com.example.weatherforecast.Internal

import kotlinx.coroutines.*

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> =
    lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) { block.invoke(this) }
    }