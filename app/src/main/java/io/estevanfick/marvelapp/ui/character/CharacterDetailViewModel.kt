package io.estevanfick.marvelapp.ui.character

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.estevanfick.marvelapp.data.api.NetworkStatus
import io.estevanfick.marvelapp.data.model.Character
import io.estevanfick.marvelapp.data.repository.CharacterRepository
import kotlinx.coroutines.experimental.async


class CharacterDetailViewModel(val repository: CharacterRepository): ViewModel(){

    val character = MutableLiveData<Character>()
    val networkStatus = MutableLiveData<NetworkStatus>()

    fun getCharacter(characterId: Int){
        if (character.value?.id != characterId) {
            networkStatus.postValue(NetworkStatus.RUNNING)
            async {
                try {
                    val response = repository.getCharacter(characterId)
                    networkStatus.postValue(NetworkStatus.SUCCESS)
                    character.postValue(response)
                } catch (e: Exception) {
                    networkStatus.postValue(NetworkStatus.FAILED)
                }
            }
        }
    }
}