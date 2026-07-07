package multithreading

import kotlin.concurrent.thread

fun main() {
    val counter = Counter()

    val t1 = thread {
        repeat(1_000_000) {
            counter.increment()
        }
    }

    val t2 = thread {
        repeat(1_000_000) {
            counter.increment()
        }
    }

    t1.join()
    t2.join()

    println(counter.number)
}

class Counter {
    private val lock = Any()
    var number: Int = 0

    fun increment() {
        synchronized(lock) {
            number++
        }
    }
}