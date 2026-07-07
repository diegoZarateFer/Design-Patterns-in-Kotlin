package users

import kotlinx.serialization.json.Json
import java.io.File

class UsersRepository private constructor() {

    init {
        println("Creating repository...")
    }

    private val file = File("users.json")

    private val _users = loadUsers()
    val users: MutableList<User>
        get() = _users.toMutableList()

    private fun loadUsers(): List<User> {
        val content = file.readText().trim()
        return Json.decodeFromString(content)
    }

    companion object {
        private val lock = Any()
        private var instance : UsersRepository? = null

        fun getInstance(password: String) : UsersRepository {
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