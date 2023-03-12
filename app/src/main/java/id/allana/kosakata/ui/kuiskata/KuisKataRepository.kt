package id.allana.kosakata.ui.kuiskata

import android.util.Log
import androidx.lifecycle.LiveData
import id.allana.kosakata.data.local.datasource.WordDataSource
import id.allana.kosakata.data.local.entity.Word

class KuisKataRepository(private val wordDataSource: WordDataSource) : KuisKataContract.Repository {

    override fun getAllWords(): LiveData<List<Word>> {
        return wordDataSource.getAllWords()
    }

    override fun logResponse(msg: String?) {
        Log.i("REPOSITORY", "KuisKataRepository created!")
    }
}