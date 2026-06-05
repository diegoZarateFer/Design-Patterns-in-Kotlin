package users

fun main() {
    UsersRepository.getInstance("12345").users.forEach(::println)
    UsersRepository.getInstance("12345").users.forEach(::println)
    UsersRepository.getInstance("12345").users.forEach(::println)
}