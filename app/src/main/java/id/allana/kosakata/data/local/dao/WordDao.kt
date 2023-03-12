package id.allana.kosakata.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.allana.kosakata.data.local.entity.Word

@Dao
interface WordDao {

    @Query("SELECT * from words_table ORDER BY id ASC")
    fun getAllWords(): LiveData<List<Word>>

    @Query("SELECT * from words_table ORDER BY id DESC")
    fun getAllWordsByNewest(): LiveData<List<Word>>

    @Query("SELECT * from words_table ORDER BY foreignWord ASC")
    fun getAllWordsByAZ(): LiveData<List<Word>>

    @Query("SELECT * from words_table ORDER BY foreignWord DESC")
    fun getAllWordsByZA(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: Word): Long

    @Query("SELECT * from words_table WHERE isFavorite = :isFavorite")
    fun getAllFavoriteWords(isFavorite: Boolean = true): LiveData<List<Word>>

    @Query("SELECT * from words_table WHERE isFavorite = :isFavorite ORDER BY foreignWord ASC")
    fun getAllFavoriteWordsByAZ(isFavorite: Boolean = true): LiveData<List<Word>>

    @Query("SELECT * from words_table WHERE isFavorite = :isFavorite ORDER BY foreignWord DESC")
    fun getAllFavoriteWordsByZA(isFavorite: Boolean = true): LiveData<List<Word>>

    @Query("SELECT * from words_table WHERE foreignWord LIKE :searchQuery OR meaningWord LIKE :searchQuery")
    fun searchKosaKata(searchQuery: String): LiveData<List<Word>>

    @Update
    suspend fun updateWord(word: Word): Int

    @Delete
    suspend fun deleteWord(word: Word): Int
}