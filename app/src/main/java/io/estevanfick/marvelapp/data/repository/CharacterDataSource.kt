package io.estevanfick.marvelapp.data.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import io.estevanfick.marvelapp.data.api.MarvelAPI
import io.estevanfick.marvelapp.data.api.NetworkStatus
import io.estevanfick.marvelapp.data.model.Character
import kotlinx.coroutines.experimental.async
import javax.inject.Inject


class CharacterDataSource @Inject constructor(val api: MarvelAPI): PageKeyedDataSource<Int, Character>(){

    val networkState = MutableLiveData<NetworkStatus>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Character>) {
        networkState.postValue(NetworkStatus.RUNNING)
        async {
            try {
                val list = api.getCharacters(0).await()
                networkState.postValue(NetworkStatus.SUCCESS)
                callback.onResult(list.data.results, 0, 20)
            } catch (e: Exception) {
                networkState.postValue(NetworkStatus.FAILED)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        networkState.postValue(NetworkStatus.RUNNING)
        async {
            try {
                val list = api.getCharacters(params.key).await()
                networkState.postValue(NetworkStatus.SUCCESS)
                callback.onResult(list.data.results, params.key + 20)
            } catch (e: Exception) {
                networkState.postValue(NetworkStatus.FAILED)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {

    }

}