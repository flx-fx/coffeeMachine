const val MIN = 0
const val MAX = 50_000_000

data class City(val name: String) {
    var population: Int = 0
        set(value) {
            field = value.coerceIn(MIN..MAX)
        }
}