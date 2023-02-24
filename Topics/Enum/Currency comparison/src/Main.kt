enum class Countries(val currency: String) {
    GERMANY( "Euro"),
    MALI("CFA franc"),
    DOMINICA("Eastern Caribbean dollar"),
    CANADA("Canadian dollar"),
    SPAIN("Euro"),
    AUSTRALIA("Australian dollar"),
    BRAZIL("Brazilian real"),
    SENEGAL("CFA franc"),
    FRANCE("Euro"),
    GRENADA("Eastern Caribbean dollar"),
    KIRIBATI("Australian dollar")
}

fun main() {
    val input = readln().split(" ")
    for (country1 in Countries.values()) {
        if (country1.name == input[0].uppercase()) {
            for (country2 in Countries.values()) {
                if (country2.name == input[1].uppercase()) {
                    println(country1.currency == country2.currency)
                    return
                }
            }
        }
    }
    println(false)
}