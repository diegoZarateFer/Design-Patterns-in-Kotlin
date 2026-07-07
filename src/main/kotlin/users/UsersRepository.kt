package users

import kotlinx.serialization.json.Json
import java.io.File

class UsersRepository private constructor(): Observable<List<User>>  {

    init {
        println("Creating repository...")
    }

    private val file = File("users.json")

    private val _observers = mutableListOf<Observer<List<User>>>()
    override val currentValue: List<User>
        get() = users
    override val observers: List<Observer<List<User>>>
        get() = _observers.toList()

    private val _users = loadUsers()
    val users: MutableList<User>
        get() = _users.toMutableList()

    override fun notifyObservers() {
        for(observer in _observers) {
            observer.onChanged(users)
        }
    }

    override fun unregisterObserver(observer: Observer<List<User>>) {
        _observers.remove(observer)
    }

    override fun registerObserver(observer: Observer<List<User>>) {
        _observers.add(observer)
        observer.onChanged(users)
    }

    var display: Display? = null
        set(value) {
            field = value
        }

    private fun loadUsers(): MutableList<User> {
        val content = file.readText().trim()
        return Json.decodeFromString(content)
    }

    fun addUser(name: String, lastname: String, age: Int) {
        val id = users.maxOf { it.id } + 1
        val newUser = User(id, name, name, age)
        _users.add(newUser)

        notifyObservers()
    }

    fun deleteUser(id: Int) {
        _users.removeIf { it.id == id }
        notifyObservers()
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