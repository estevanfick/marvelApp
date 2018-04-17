package io.estevanfick.marvelapp.ui.character

import android.support.v7.util.DiffUtil
import io.estevanfick.marvelapp.data.model.ComicsItens


class ComicsDiff: DiffUtil.ItemCallback<ComicsItens>(){

    override fun areItemsTheSame(oldItem: ComicsItens?, newItem: ComicsItens?): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ComicsItens?, newItem: ComicsItens?): Boolean {
        return oldItem == newItem
    }

}