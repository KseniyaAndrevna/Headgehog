package com.kseniyaa.headgehog

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kseniyaa.headgehog.model.Jokes
import kotlinx.android.synthetic.main.item.view.*

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private var jokes: List<Jokes> = ArrayList()

    fun setJokes(jokes :List<Jokes> ) {
        this.jokes = jokes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context = parent.context
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.joke?.text = jokes[position].joke
    }

    override fun getItemCount() = jokes.size

    class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val joke = itemView?.tv_name
    }

    val Any.TAG: String
        get() {
            val tag = javaClass.simpleName
            return if (tag.length <= 23) tag else tag.substring(0, 23)
        }
}
