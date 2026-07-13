package users

class Administrator {
    private val repository = UsersRepository.getInstance("12345")

    fun work() {
        while(true) {
            val operations = Operation.entries
            print("Enter an operation: ")

            for((index, operation) in operations.withIndex()) {
                print("$index - ${operation.title}")
                if(index == operations.lastIndex) {
                    print(": ")
                } else {
                    print(", ")
                }
            }

            val operationIndex = readln().toInt()
            val operation = operations[operationIndex]

            when(operation) {
                Operation.EXIT -> {
                    UsersInvoker.addCommand(AdministratorCommands.SaveChanges(repository))
                   break
                }

                Operation.ADD_USER -> {
                   print("Enter first name: ")
                   val firstName = readln()

                   print("Enter last name: ")
                   val lastName = readln()

                   print("Enter age: ")
                   val age = readln().toInt()

                    UsersInvoker.addCommand(AdministratorCommands.AddUser(repository, firstName, lastName, age))
                }

                Operation.DELETE_USER -> {
                    print("Enter use id: ")
                    val id = readln().toInt()

                    UsersInvoker.addCommand(AdministratorCommands.DeleteUser(repository, id))
                }
            }
        }
    }

}