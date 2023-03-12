package id.allana.kosakata.data.local.datasource

import androidx.lifecycle.LiveData
import id.allana.kosakata.data.local.dao.WordDao
import id.allana.kosakata.data.local.entity.Word

class WordDataSourceImpl(private val wordDao: WordDao): WordDataSource {
    override fun getAllWords(): LiveData<List<Word>> {
        return wordDao.getAllWords()
    }

    override fun getAllWordsByNewest(): LiveData<List<Word>> {
        return wordDao.getAllWordsByNewest()
    }

    override fun getAllWordsAZ(): LiveData<List<Word>> {
        return wordDao.getAllWordsByAZ()
    }

    override fun getAllWordsZA(): LiveData<List<Word>> {
        return wordDao.getAllWordsByZA()
    }

    override fun getAllFavoriteWords(): LiveData<List<Word>> {
        return wordDao.getAllFavoriteWords()
    }

    override fun getAllFavoriteWordsByAZ(): LiveData<List<Word>> {
        return wordDao.getAllFavoriteWordsByAZ()
    }

    override fun getAllFavoriteWordsByZA(): LiveData<List<Word>> {
        return wordDao.getAllFavoriteWordsByZA()
    }

    override fun searchKosaKata(searchQuery: String): LiveData<List<Word>> {
        return wordDao.searchKosaKata(searchQuery)
    }

    override suspend fun insertWord(word: Word): Long {
        return wordDao.insertWord(word)
    }

    override suspend fun deleteWord(word: Word): Int {
        return wordDao.deleteWord(word)
    }

    override suspend fun updateWord(word: Word): Int {
        return wordDao.updateWord(word)
    }

}