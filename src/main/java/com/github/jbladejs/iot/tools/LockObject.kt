package com.github.jbladejs.iot.tools

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class LockObject {
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    fun await() {
        lock.withLock { condition.await() }
    }

    fun signal() {
        lock.withLock { condition.signal() }
    }
}