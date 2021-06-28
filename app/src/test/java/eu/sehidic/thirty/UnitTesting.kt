package eu.sehidic.thirty

import eu.sehidic.thirty.model.*
import org.junit.Test
import org.junit.Assert.*

class TestScore {
    @Test
    fun dice_14_gives_5() {
        val data = arrayOf (Die(1), Die(4))
        assertEquals(Score(5), getScoreFromDice(Choice.FIVE, data))
    }
    @Test
    fun dice_123456_gives_6() {
        val data = arrayOf (Die(1), Die(2), Die(3), Die(4),
        Die(5), Die(6))
        assertEquals(Score(6), getScoreFromDice(Choice.LOW, data))
    }
}

class TestDice {
    @Test
    fun roll_gives_correct_values() {
        val sut = Die()
        for (i in 0..100) {
            sut.roll()
            assert(sut.value in 1..6)
        }
    }
}

class TestPlayer {
    @Test
    fun hasSixDice() {
        val p = Player()
        assertEquals(6, p.dice.size)
    }
}
/*
class TestGame {
    @Test
    fun calculateLowScore() {
        val p = Player()
        val sut = GameViewModel()
    }
} */