fun main() {
    var max1 = 0
    var max2 = 0
    for (i in 1..readln().toInt()) {
        val x = readln().toInt()
        if (x > max1) {
            max2 = max1
            max1 = x
        } else if (x > max2) max2 = x
    }
    println(if (max2 == 0) max1 else max1 * max2)
}