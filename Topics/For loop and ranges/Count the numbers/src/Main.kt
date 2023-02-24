fun main() {
    val a = readln().toInt()
    val b = readln().toInt()
    val n = readln().toInt()
    var count = 0
    for (i in a..b) {
        if (i % n == 0) count++
    }
    println(count)
}