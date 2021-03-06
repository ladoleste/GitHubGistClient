package br.com.ladoleste.githubgistclient.features.favorites

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.ladoleste.githubgistclient.common.loadImage
import br.com.ladoleste.githubgistclient.databinding.ItemGistBinding
import br.com.ladoleste.githubgistclient.dto.Gist
import br.com.ladoleste.githubgistclient.features.common.ItemClick


class FavoritesAdapter(_items: List<Gist>, private val itemClick: ItemClick) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private val items = mutableListOf<Gist>()

    init {
        items.addAll(_items)
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesAdapter.ViewHolder, position: Int) {
        val gist = items[position]
        holder.itemView.setOnClickListener { itemClick.onItemClick(gist) }
        holder.bind(gist)
    }

    class ViewHolder(private val binding: ItemGistBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Gist) {
            binding.gist = item
            binding.ivAvatar.loadImage(item.owner?.avatarUrl)
            binding.executePendingBindings()
        }
    }

    fun updateItems(it: List<Gist>) {
        items.clear()
        items.addAll(it)
        notifyDataSetChanged()
    }
}