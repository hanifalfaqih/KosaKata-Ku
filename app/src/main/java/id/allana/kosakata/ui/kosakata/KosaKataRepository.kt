package id.allana.kosakata.ui.kosakata

import android.util.Log
import androidx.lifecycle.LiveData
import id.allana.kosakata.data.local.datasource.WordDataSource
import id.allana.kosakata.data.local.entity.Word

class KosaKataRepository(private val wordDataSource: WordDataSource): KosaKataContract.Repository {

    override fun getAllWords(): LiveData<List<Word>> {
        return wordDataSource.getAllWords()
    }

    override fun getAllWordsByNewest(): LiveData<List<Word>> {
        return wordDataSource.getAllWordsByNewest()
    }

    override fun getAllWordsAZ(): LiveData<List<Word>> {
        return wordDataSource.getAllWordsAZ()
    }

    override fun getAllWordsZA(): LiveData<List<Word>> {
        return wordDataSource.getAllWordsZA()
    }

    override fun searchKosaKata(searchQuery: String): LiveData<List<Word>> {
        return wordDataSource.searchKosaKata(searchQuery)
    }

    override fun logResponse(msg: String?) {
        Log.i("REPOSITORY", "KosaKataRepository created!")
    }
}