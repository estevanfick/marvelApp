package io.estevanfick.marvelapp.ui.characterlist

import android.support.v7.util.DiffUtil
import io.estevanfick.marvelapp.data.model.Character


class CharacterDiff: DiffUtil.ItemCallback<Character>(){
    override fun areItemsTheSame(oldItem: Character?, newItem: Character?): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Character?, newItem: Character?): Boolean {
        return oldItem == newItem
    }


}