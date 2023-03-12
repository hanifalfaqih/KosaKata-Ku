package id.allana.kosakata.ui.favoritkata

import android.util.Log
import androidx.lifecycle.LiveData
import id.allana.kosakata.data.local.datasource.WordDataSource
import id.allana.kosakata.data.local.entity.Word

class FavoritKataRepository(private val wordDataSource: WordDataSource): FavoritKataContract.Repository {

    override fun getAllFavoriteWords(): LiveData<List<Word>> {
        return wordDataSource.getAllFavoriteWords()
    }

    override fun getAllFavoriteWordsAZ(): LiveData<List<Word>> {
        return wordDataSource.getAllFavoriteWordsByAZ()
    }

    override fun getAllFavoriteWordsZA(): LiveData<List<Word>> {
        return wordDataSource.getAllFavoriteWordsByZA()
    }

    override fun logResponse(msg: String?) {
        Log.i("REPOSITORY", "FavoriteKataRepository created!")
    }
}