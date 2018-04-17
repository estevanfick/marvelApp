package io.estevanfick.marvelapp.data.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import io.estevanfick.marvelapp.data.model.Character
import javax.inject.Inject


class CharacterDataSourceFactory @Inject constructor(val source: CharacterDataSource): DataSource.Factory<Int, Character>(){

    val liveData = MutableLiveData<CharacterDataSource>()

    override fun create(): DataSource<Int, Character> {
        liveData.postValue(source)
        return source
    }

}