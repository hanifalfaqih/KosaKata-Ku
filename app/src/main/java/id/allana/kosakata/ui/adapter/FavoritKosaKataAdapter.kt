package id.allana.kosakata.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.allana.kosakata.data.local.entity.Word
import id.allana.kosakata.databinding.ListItemWordBinding
import id.allana.kosakata.ui.favoritkata.FavoritKataFragmentDirections

class FavoritKosaKataAdapter :
    ListAdapter<Word, FavoritKosaKataAdapter.WordViewHolder>(WordsComparator()) {

    class WordViewHolder(private val binding: ListItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Word) {
            binding.tvWord.text = data.foreignWord
            itemView.setOnClickListener {
                val action =
                    FavoritKataFragmentDirections.actionNavigationFavoritKataToDetailKosaKataFragment(
                        data
                    )
                val navController = Navigation.findNavController(itemView)
                navController.navigate(action)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding =
            ListItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}