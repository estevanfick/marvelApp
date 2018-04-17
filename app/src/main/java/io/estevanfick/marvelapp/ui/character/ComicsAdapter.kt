package io.estevanfick.marvelapp.ui.character

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.estevanfick.marvelapp.R
import io.estevanfick.marvelapp.data.model.ComicsItens
import kotlinx.android.synthetic.main.list_item_comics.view.*


class ComicsAdapter: ListAdapter<ComicsItens, ComicsAdapter.ViewHolder>(ComicsDiff()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_comics, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comics: ComicsItens) {
            itemView.txtComicsName.text = comics.name
        }
    }
}