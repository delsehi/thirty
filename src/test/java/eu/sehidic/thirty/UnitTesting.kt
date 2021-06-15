package eu.sehidic.thirty

import eu.sehidic.thirty.model.Die
import eu.sehidic.thirty.model.GameViewModel
import eu.sehidic.thirty.model.Player
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
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

class TestGame {
    @Test
    fun calculateLowScore() {
        val p = Player()
        val sut = GameViewModel()


    }
}