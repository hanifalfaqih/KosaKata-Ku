package id.allana.kosakata.ui.tambahkata

import android.util.Log
import id.allana.kosakata.data.local.datasource.WordDataSource
import id.allana.kosakata.data.local.entity.Word

class TambahKataDialogRepository(private val wordDataSource: WordDataSource): TambahKataDialogContract.Repository {
    override suspend fun insertWord(word: Word) = wordDataSource.insertWord(word)

    override fun logResponse(msg: String?) {
        Log.i("REPOSITORY", "TambahKataDialogRepository created!")
    }

}