package id.allana.kosakata.data.network.datasource

import android.content.Context
import java.io.File

interface KosaKataDataSource {

    suspend fun synthesizeTextToSpeech(text: String, context: Context)
    suspend fun playSound(file: File, context: Context)
    suspend fun deleteCacheFiles()

}