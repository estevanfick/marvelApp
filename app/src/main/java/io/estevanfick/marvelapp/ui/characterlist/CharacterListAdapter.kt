package io.estevanfick.marvelapp.ui.characterlist

import android.arch.paging.PagedListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.estevanfick.marvelapp.R
import io.estevanfick.marvelapp.data.model.Character
import kotlinx.android.synthetic.main.list_item_character.view.*


class CharacterListAdapter(val listener: (Int) -> Unit): PagedListAdapter<Character, CharacterListAdapter.ViewHolder>(CharacterDiff()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_character, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(character: Character, listener: (Int) -> Unit) {
            itemView.txtCharacterName.text = character.name
            Picasso.get()
                    .load(character.linkImage)
                    .into(itemView.imgCharacter)
            itemView.setOnClickListener {
                listener(character.id)
            }
        }
    }
}