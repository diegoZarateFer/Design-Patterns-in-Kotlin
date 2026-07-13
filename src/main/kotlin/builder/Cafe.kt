package builder

fun main() {
    val drinkBuilder = Drink.Builder()
    val drink = drinkBuilder.type("Tea").temperature("Cold").diningOption("In cafe").build()

    print(drink)
}