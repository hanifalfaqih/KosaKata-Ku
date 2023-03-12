package id.allana.kosakata.ui.kuiskata.permainan

import android.util.Log
import androidx.lifecycle.LiveData
import id.allana.kosakata.data.local.datasource.WordDataSource
import id.allana.kosakata.data.local.entity.Word

class PermainanKuisKataRepository(private val wordDataSource: WordDataSource): PermainanKuisKataContract.Repository {

    override fun getAllWords(): LiveData<List<Word>> {
        return wordDataSource.getAllWords()
    }

    override fun logResponse(msg: String?) {
        Log.i("REPOSITORY", "KuisKataRepository created!")
    }
}