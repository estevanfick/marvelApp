package io.estevanfick.marvelapp.data.repository

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.estevanfick.marvelapp.data.api.MarvelAPI
import io.estevanfick.marvelapp.data.api.NetworkStatus
import io.estevanfick.marvelapp.data.model.Character
import java.util.concurrent.Executors
import javax.inject.Inject


class CharacterRepository @Inject constructor(val api: MarvelAPI, val dataSource: CharacterDataSourceFactory) {

    fun getCharacterList(): Pair<LiveData<NetworkStatus>, LiveData<PagedList<Character>>> {
        val pagedList = LivePagedListBuilder(dataSource, 10)
                .setFetchExecutor(Executors.newFixedThreadPool(3))
        return Pair(dataSource.source.networkState, pagedList.build())
    }

    suspend fun getCharacter(characterId: Int): Character{
        val response = api.getCharacter(characterId).await()
        return response.data.results[0]
    }
}