package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var wordList: MutableList<String> = mutableListOf()
    private lateinit var currentWord:String
    private var _score = 0
    val score: Int get() = _score
    private var currentWordCount = 0
    private lateinit var _currentScrambledWord: String
    val currentScrambleWord:String get() = _currentScrambledWord
    fun getNextWord(){
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while(String(tempWord) == currentWord) {
            tempWord.shuffle()
        }
        if(wordList.contains(currentWord)){
            getNextWord()
        }else{
            _currentScrambledWord = String(tempWord)
            ++currentWordCount
            wordList.add(currentWord)
        }
    }
    /*
* Returns true if the current word count is less than MAX_NO_OF_WORDS.
* Updates the next word.
*/
    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }
    private fun increaseScore(){
        _score += SCORE_INCREASE
    }
    fun isUserWordCorrect(playerWord:String): Boolean{
        if(playerWord.equals(currentWord)){
            increaseScore()
            return true
        }
        return false
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }
}