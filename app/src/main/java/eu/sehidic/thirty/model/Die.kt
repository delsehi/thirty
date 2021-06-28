package eu.sehidic.thirty.model

class Die (newValue: Int = 0) {
    var value = newValue // 0 if not set explicitly
    var used = false
    var keep = false
    fun roll() {
        value = (1..6).random()
    }
}