package users

import command.Command
import kotlinx.serialization.json.Json
import observer.MutableObservable
import observer.Observable
import java.io.File
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class UsersRepository private constructor() {

    init {
        println("Creating repository...")
    }

    private val file = File("users.json")
    private val _userList = loadUsers()

    /**
     * Se aprovecha el down casting para no permitir el acceso desde fuera de esta clase
     * al [currentValue] del [observer.MutableObservable].
     *
     * Se aprovecha el back field para retornar un objeto tipo [observer.Observable].
     */
    private val _users = MutableObservable(_userList.toList())
    val users: Observable<List<User>>
        get() = _users

    val oldestUser = MutableObservable(_userList.maxBy { it.age })

    private fun loadUsers(): MutableList<User> {
        val content = file.readText().trim()
        return Json.decodeFromString(content)
    }

    fun addUser(name: String, lastname: String, age: Int) {
        Thread.sleep(10_000)
        val id = _userList.maxOf { it.id } + 1
        val newUser = User(id, name, lastname, age)

        _userList.add(newUser)
        _users.currentValue = _userList.toList()

        if (age > oldestUser.currentValue.age) {
            oldestUser.currentValue = _userList.maxBy { it.age }
        }
    }

    fun deleteUser(id: Int) {
        Thread.sleep(10_000)
        _userList.removeIf { it.id == id }
        _users.currentValue = _userList.toList()
        val newOldest = _userList.maxBy { it.age }
        if (newOldest != oldestUser.currentValue) {
            oldestUser.currentValue = newOldest
        }
    }

    fun saveChanges() {
        val content = Json.encodeToString(_userList)
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