package id.allana.kosakata.data.network.datasource

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.text_to_speech.v1.TextToSpeech
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions
import com.ibm.watson.text_to_speech.v1.util.WaveUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class KosaKataDataSourceImpl : KosaKataDataSource {

    companion object {
        const val API_KEY = "REPLACE WITH YOUR OWN"
        const val URL = "REPLACE WITH YOUR OWN"
    }

    private val authenticator = IamAuthenticator.Builder()
        .apikey(API_KEY)
        .build()

    private val textToSpeech = TextToSpeech(authenticator).apply {
        serviceUrl = URL
    }

    override suspend fun synthesizeTextToSpeech(text: String, context: Context) {
        withContext(Dispatchers.IO) {
            val synthesizeOptions = SynthesizeOptions.Builder()
                .text(text)
                .voice("de-DE_DieterV3Voice")
                .accept("audio/mp3")
                .build()

            // encode text to easier create filename
            val fileName = text.encodeToByteArray().toString()

            val file = File(context.filesDir, fileName)
            if (!file.exists()) {
                // If the file doesn't exist, generate the audio file and save it to the cache
                val inputStream = textToSpeech.synthesize(synthesizeOptions).execute().result
                val audio = WaveUtils.reWriteWaveHeader(inputStream)

                context.openFileOutput(fileName, Context.MODE_PRIVATE).use { fos ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while ((audio.read(buffer).also { length = it }) > 0) {
                        fos.write(buffer, 0, length)
                    }
                }
            }

            // Play the sound
            playSound(file, context)
        }
    }

    override suspend fun playSound(file: File, context: Context) {
        withContext(Dispatchers.IO) {
            val fileUri: Uri = Uri.parse(file.path)
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build()
            )
            mediaPlayer.setDataSource(context, fileUri)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener { mp -> mp.start() }
        }
    }
}
