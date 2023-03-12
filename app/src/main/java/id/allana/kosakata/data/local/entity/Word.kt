package id.allana.kosakata.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "words_table")
@Parcelize
data class Word (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val foreignWord: String,
    val meaningWord: String,
    var isFavorite: Boolean = false
        ) : Parcelable