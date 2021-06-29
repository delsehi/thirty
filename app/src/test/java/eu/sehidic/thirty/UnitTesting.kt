package eu.sehidic.thirty

import eu.sehidic.thirty.model.*
import org.junit.Test
import org.junit.Assert.*

/**
 * Basic unit tests used during development.
 * @author Delfi Sehidic
 */

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
    @Test
    fun score_5_and_9_gives_14() {
        val sut = Player()
        val score1 = Score(5)
        val score2 = Score(9)
        sut.addScore(score1)
        sut.addScore(score2)
        assertEquals(14, sut.getCurrScore())
    }
}

class TestGame {
    @Test
    fun choicesAvailableOnlyOnce() {
        val sut = GameViewModel()
        sut.throwDice()
        val dice = sut.getDice()
        val a = sut.getAvailableChoices()
        sut.addScore(Choice.LOW, dice)
        sut.roundDone()
        val b = sut.getAvailableChoices()
        assert(!b.contains(Choice.LOW))
        assertNotEquals(a.size, b.size)
    }
    @Test
    fun roundsAreCorrect() {
        val sut = GameViewModel()
        val data = arrayOf (Die(1), Die(3))
        val data2 = arrayOf (Die(6), Die(6))
        val data3 = arrayOf (Die(4), Die(2), Die(1))
        val data4 = arrayOf (Die(6), Die(1))
        sut.addScore(Choice.LOW, data)
        sut.roundDone()
        sut.addScore(Choice.TWELVE, data2)
        sut.roundDone()
        sut.addScore(Choice.SEVEN, data3)
        sut.addScore(Choice.SEVEN, data4)
        sut.roundDone()
        val actual = sut.getRounds()
        val expected = arrayOf (
            Round(1, Choice.LOW, 4),
            Round(2, Choice.TWELVE, 12),
            Round(3, Choice.SEVEN, 14)
        )
        assertArrayEquals(expected, actual)

    }

}