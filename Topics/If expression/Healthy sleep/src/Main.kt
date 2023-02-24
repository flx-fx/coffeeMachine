fun main() {
    val a = readln().toInt()
    val b = readln().toInt()
    println(
        when (readln().toInt()) {
            in a..b -> "Normal"
            in Int.MIN_VALUE..a -> "Deficiency"
            else -> "Excess"
        }
    )
}