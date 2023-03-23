package id.allana.kosakata.ui.detailkosakata

import android.content.Context
import android.util.Log
import id.allana.kosakata.data.local.datasource.WordDataSource
import id.allana.kosakata.data.local.entity.Word
import id.allana.kosakata.data.network.datasource.KosaKataDataSource

class DetailKosaKataRepository(private val wordDataSource: WordDataSource, private val kosaKataDataSource: KosaKataDataSource): DetailKosaKataContract.Repository {

    override suspend fun synthesizeTextToSpeech(text: String, context: Context) {
        kosaKataDataSource.synthesizeTextToSpeech(text, context)
    }

    override suspend fun updateWord(word: Word): Int {
        return wordDataSource.updateWord(word)
    }

    override suspend fun deleteWord(word: Word): Int {
        return wordDataSource.deleteWord(word)
    }

    override suspend fun deleteCacheFile() {
        kosaKataDataSource.deleteCacheFiles()
    }

    override fun logResponse(msg: String?) {
        Log.i("REPOSITORY", "KosaKataRepository created!")
    }
}