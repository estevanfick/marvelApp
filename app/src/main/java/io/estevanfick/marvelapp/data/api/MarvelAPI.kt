package io.estevanfick.marvelapp.data.api

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MarvelAPI {

    @GET("characters")
    fun getCharacters(@Query("offset") offset: Int): Deferred<Response>

    @GET("characters/{id}")
    fun getCharacter(@Path("id") characterId: Int): Deferred<Response>
}