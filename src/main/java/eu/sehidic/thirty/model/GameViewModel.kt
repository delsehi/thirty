package eu.sehidic.thirty.model

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val player = Player()
    var round = 0
    var throws = 0

}