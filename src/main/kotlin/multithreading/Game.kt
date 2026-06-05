package multithreading

import kotlin.concurrent.thread

fun main() {

    print("Enter your number: ")
    val userNumber = readln().toInt()

    var timer = 0
    val timerThread = thread {
        while (!Thread.currentThread().isInterrupted) {
            timer++
            println(timer)
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                println("Timer Stopped At $timer")
                break
            }
        }
    }

    thread {
        var randomNumber = (1..1_000_000_000).random()
        while (randomNumber != userNumber) {
            randomNumber = (1..1_000_000_000).random()
        }

        timerThread.interrupt()
        println("Your number is: $randomNumber !!")
    }
}