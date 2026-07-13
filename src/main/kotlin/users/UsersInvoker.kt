package users

import command.Command
import command.Invoker
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

object UsersInvoker : Invoker<AdministratorCommands> {

    private val commands = LinkedBlockingQueue<Command>()
    override fun addCommand(command: AdministratorCommands) {
        commands.add(command)
        println("Command was added: $command")
    }

    init {
        thread {
            while (true) {
                println("Waiting for a task...")
                val command = commands.take()

                println("Executing command : $command ...")
                command.execute()
                println("Command has been executed: $command!")
            }
        }
    }
}