package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel containing the app data and methods to process the data
 */
class GameViewModel : ViewModel() {
    // List of words used in the game
    private var wordList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String
    private val _score = MutableLiveData<Int>(0)

    /**Faltaba inicializar.*/

    val score: LiveData<Int> get() = _score
    private val _currentWordCount = MutableLiveData<Int>(0)
    val currentWordCount: LiveData<Int> get() = _currentWordCount
    //private lateinit var _currentScrambledWord: String
    //Cambiamos a MutableLiveData
    private val _currentScrambledWord = MutableLiveData<String>()

    val currentScrambledWord: LiveData<String> get() = _currentScrambledWord

    /*
    * Updates currentWord and currentScrambledWord with the next word.
    */
    fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord) == currentWord) {
            tempWord.shuffle()
        }
        if (wordList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord.value = String(tempWord)
            /**Incrementamos en uno MutableLIveData*/
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordList.add(currentWord)
        }
    }

    /*
* Returns true if the current word count is less than MAX_NO_OF_WORDS.
* Updates the next word.
*/
    fun nextWord(): Boolean {
        /**Comparación no nula*/
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    /*
    * Increases the game score if the player's word is correct.
    */
    private fun increaseScore() {
        /**Sumando con MutableLiveData*/
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    /*
    * Returns true if the player word is correct.
    * Increases the score accordingly.
    */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord)) {
            increaseScore()
            return true
        }
        return false
    }

    /*
    * Re-initializes the game data to restart the game.
    */
    fun reinitializeData() {
        /**modificando la "variable" MutableLiveData*/
        _score.value = 0
        _currentWordCount.value = 0
        wordList.clear()
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }
}