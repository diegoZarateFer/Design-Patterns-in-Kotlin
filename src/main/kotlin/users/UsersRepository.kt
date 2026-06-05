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
        private val instance : UsersRepository by lazy {
            UsersRepository()
        }

        fun getInstance(password: String) : UsersRepository {
            val correctPassword = File("password_users.txt").readText().trim()
            if (correctPassword != password) throw IllegalArgumentException("Wrong password!")

            return instance
        }

    }
}