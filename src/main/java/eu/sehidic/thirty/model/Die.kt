package eu.sehidic.thirty.model

class Die {
    var value = 0
    var used = false
    var keep = false
    fun roll() {
        value = (1..6).random()
    }
}