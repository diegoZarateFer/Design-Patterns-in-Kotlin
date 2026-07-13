package users

/// Functional interface
fun interface Observer<T> {
    fun onChanged(users: T)
}