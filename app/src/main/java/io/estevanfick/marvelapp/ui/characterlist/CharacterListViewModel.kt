package io.estevanfick.marvelapp.ui.characterlist

import android.arch.lifecycle.ViewModel
import io.estevanfick.marvelapp.data.repository.CharacterRepository


class CharacterListViewModel(val repository: CharacterRepository): ViewModel(){

    val pair = repository.getCharacterList()
    val networkStatus = pair.first
    val characterList = pair.second

}