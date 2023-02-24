package machine

class CoffeeMachine {
    private enum class Offer(
        val id: String,
        val waterInMl: Int,
        val milkInMl: Int = 0,
        val coffeeInG: Int,
        val price: Int
    ) {
        ESPRESSO("1", waterInMl = 250, coffeeInG = 16, price = 4),
        LATTE("2", waterInMl = 350, milkInMl = 75, coffeeInG = 20, price = 7),
        CAPPUCCINO("3", waterInMl = 200, milkInMl = 100, coffeeInG = 12, price = 6)
    }

    enum class State {
        OFF,
        CHOOSING_ACTION,
        BUY,
        FILL_WATER,
        FILL_MILK,
        FILL_COFFEE,
        FILL_CUPS
    }

    private var money = 550
    private var waterInMl = 400
    private var milkInMl = 540
    private var coffeeInG = 120
    private var cups = 9
    var state = State.OFF
        set(value) {
            println()
            println(
                when (value) {
                    State.CHOOSING_ACTION -> "Write action (buy, fill, take, remaining, exit):"
                    State.BUY -> "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:"
                    State.FILL_WATER -> "Write how many ml of water you want to add:"
                    State.FILL_MILK -> "Write how many ml of milk you want to add:"
                    State.FILL_COFFEE -> "Write how many grams of coffee beans you want to add:"
                    State.FILL_CUPS -> "Write how many disposable cups you want to add:"
                    State.OFF -> "The machine is now turned off."
                }
            )
            field = value
        }

    fun `interface`(input: String) {
        when (state) {
            State.CHOOSING_ACTION -> choosingAction(input)
            State.BUY -> buy(input)
            State.FILL_WATER -> fillWater(input)
            State.FILL_MILK -> fillMilk(input)
            State.FILL_COFFEE -> fillCoffee(input)
            State.FILL_CUPS -> fillCups(input)
            else -> println("There is no input to take at the moment.")
        }
    }

    private fun choosingAction(input: String) {
        when (input) {
            "buy" -> state = State.BUY
            "fill" -> state = State.FILL_WATER
            "take" -> take()
            "remaining" -> remaining()
            "exit" -> state = State.OFF
            else -> {
                print("This is not a valid action. ")
                state = State.CHOOSING_ACTION
            }
        }
    }

    private fun buy(input: String) {

        fun checkResources(waterN: Int = 0, milkN: Int = 0, coffeeBeansN: Int = 0): Boolean {
            val msg = "Sorry, not enough %s!"
            println(
                when {
                    waterInMl < waterN -> msg.format("water")
                    milkInMl < milkN -> msg.format("milk")
                    coffeeInG < coffeeBeansN -> msg.format("coffee beans")
                    cups < 1 -> msg.format("cups")
                    else -> {
                        println("I have enough resources, making you a coffee!")
                        return true
                    }
                }
            )
            return false
        }

        if (input.length == 1 && input[0].isDigit() && input in "1".."3") {
            var offerChosen: Offer? = null
            for (offer in Offer.values()) if (offer.id == input) offerChosen = offer
            if (checkResources(offerChosen!!.waterInMl, offerChosen.milkInMl, offerChosen.coffeeInG)) {
                waterInMl -= offerChosen.waterInMl
                milkInMl -= offerChosen.milkInMl
                coffeeInG -= offerChosen.coffeeInG
                cups--
                money += offerChosen.price
            }
            state = State.CHOOSING_ACTION
        } else if (input == "back") state = State.CHOOSING_ACTION
        else {
            print("This is not a valid action.")
            state = State.BUY
        }
    }

    private fun fill(waterInMl: Int = 0, milkInMl: Int = 0, coffeeInG: Int = 0, cups: Int = 0) {
        this.waterInMl += waterInMl
        this.milkInMl += milkInMl
        this.coffeeInG += coffeeInG
        this.cups += cups
    }

    private fun fillCheckInput(input: String): Boolean {
        return if (input.toIntOrNull() != null) true
        else {
            println("You should enter a number.")
            false
        }
    }

    private fun fillWater(input: String) {
        state = if (fillCheckInput(input)) {
            fill(waterInMl = input.toInt())
            State.FILL_MILK
        } else State.FILL_WATER
    }

    private fun fillMilk(input: String) {
        state = if (fillCheckInput(input)) {
            fill(milkInMl = input.toInt())
            State.FILL_COFFEE
        } else State.FILL_MILK
    }

    private fun fillCoffee(input: String) {
        state = if (fillCheckInput(input)) {
            fill(coffeeInG = input.toInt())
            State.FILL_CUPS
        } else State.FILL_COFFEE
    }

    private fun fillCups(input: String) {
        state = if (fillCheckInput(input)) {
            fill(cups = input.toInt())
            State.CHOOSING_ACTION
        } else State.FILL_CUPS
    }

    private fun take() {
        println("I gave you \$$money")
        money = 0
        state = State.CHOOSING_ACTION
    }

    private fun remaining() {
        println(
            """
            
            The coffee machine has:
            $waterInMl ml of water
            $milkInMl ml of milk
            $coffeeInG g of coffee beans
            $cups disposable cups
            ${'$'}$money of money
        """.trimIndent()
        )
        state = State.CHOOSING_ACTION
    }
}

fun main() {
    val scanner = java.util.Scanner(System.`in`)
    val coffeeMachine = CoffeeMachine()
    coffeeMachine.state = CoffeeMachine.State.CHOOSING_ACTION
    running@ while (true) {
        if (scanner.hasNext()) coffeeMachine.`interface`(scanner.next())
        if (coffeeMachine.state == CoffeeMachine.State.OFF) break@running
    }
}