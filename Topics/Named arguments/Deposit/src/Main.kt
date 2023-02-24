import kotlin.math.pow

const val ONE_HUNDRED = 100.0

fun main() {
    when (readln()) {
        "amount" -> printFinalAmount(startingAmount = readln().toInt())
        "percent" -> printFinalAmount(yearlyPercent = readln().toInt())
        "years" -> printFinalAmount(years = readln().toInt())
    }
}

fun printFinalAmount(startingAmount: Int = 1000, yearlyPercent: Int = 5, years: Int = 10) =
    println((startingAmount * (1 + yearlyPercent / ONE_HUNDRED).pow(years)).toInt())