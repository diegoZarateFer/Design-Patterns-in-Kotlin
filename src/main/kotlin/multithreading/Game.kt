package multithreading

import kotlin.concurrent.thread

fun main() {

    print("Enter your number: ")
    val userNumber = readln().toInt()

    var win = false
    thread {
        var seconds = 0
        while (!win) {
            seconds++
            println(seconds)
            Thread.sleep(1000)
        }
    }

    thread {
        var randomNumber = (1..1_000_000_000).random()
        while (randomNumber != userNumber) {
            randomNumber = (1..1_000_000_000).random()
        }

        win = true
        println("Your number is: $randomNumber !!")
    }
}