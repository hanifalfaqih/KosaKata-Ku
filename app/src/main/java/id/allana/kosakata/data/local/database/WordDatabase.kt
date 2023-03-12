package id.allana.kosakata.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.allana.kosakata.data.local.dao.WordDao
import id.allana.kosakata.data.local.entity.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabase(context: Context): WordDatabase {
            if (INSTANCE == null) {
                synchronized(WordDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WordDatabase::class.java,
                        "word_database"
                    ).build()
                }
            }
            return INSTANCE as WordDatabase
        }
    }

}