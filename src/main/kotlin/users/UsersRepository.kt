package users

import kotlinx.serialization.json.Json
import java.io.File

class UsersRepository private constructor()  {

    init {
        println("Creating repository...")
    }

    private val file = File("users.json")

    private val _users = loadUsers()
    val users = MutableObservable(_users.toList())

    val oldestUser = MutableObservable(_users.maxBy { it.age })

    private fun loadUsers(): MutableList<User> {
        val content = file.readText().trim()
        return Json.decodeFromString(content)
    }

    fun addUser(name: String, lastname: String, age: Int) {
        val id = _users.maxOf { it.id } + 1
        val newUser = User(id, name, lastname, age)

        _users.add(newUser)
        users.currentValue = _users.toList()

        if(age > oldestUser.currentValue.age) {
            oldestUser.currentValue = _users.maxBy { it.age }
        }
    }

    fun deleteUser(id: Int) {
        _users.removeIf { it.id == id }

        users.currentValue = _users.toList()
        val newOldest = _users.maxBy { it.age }
        if(newOldest != oldestUser.currentValue) {
            oldestUser.currentValue = newOldest
        }
    }

    fun saveChanges() {
        val content = Json.encodeToString(_users)
        file.writeText(content)
    }

    companion object {
        private val lock = Any()
        private var instance: UsersRepository? = null

        fun getInstance(password: String): UsersRepository {
            val correctPassword = File("password_users.txt").readText().trim()
            if (correctPassword != password) throw IllegalArgumentException("Wrong password!")

            instance?.let { return it }
            synchronized(lock) {
                instance?.let { return it }

                return UsersRepository().also {
                    instance = it
                }
            }
        }

    }
}