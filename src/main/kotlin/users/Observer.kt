package users

interface Observer<T> {
    fun onChanged(users: T)
}