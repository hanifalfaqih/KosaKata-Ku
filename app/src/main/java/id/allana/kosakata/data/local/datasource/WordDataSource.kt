package id.allana.kosakata.data.local.datasource

import androidx.lifecycle.LiveData
import id.allana.kosakata.data.local.entity.Word

interface WordDataSource {

    fun getAllWords(): LiveData<List<Word>>
    fun getAllWordsByNewest(): LiveData<List<Word>>
    fun getAllWordsAZ(): LiveData<List<Word>>
    fun getAllWordsZA(): LiveData<List<Word>>
    fun getAllFavoriteWords(): LiveData<List<Word>>
    fun getAllFavoriteWordsByAZ(): LiveData<List<Word>>
    fun getAllFavoriteWordsByZA(): LiveData<List<Word>>
    fun searchKosaKata(searchQuery: String): LiveData<List<Word>>
    suspend fun insertWord(word: Word): Long
    suspend fun deleteWord(word: Word): Int
    suspend fun updateWord(word: Word): Int
}