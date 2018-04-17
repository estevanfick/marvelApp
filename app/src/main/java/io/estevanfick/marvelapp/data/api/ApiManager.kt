package io.estevanfick.marvelapp.data.api

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import io.estevanfick.marvelapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiManager {

    companion object {
        var url = "https://gateway.marvel.com/v1/public/"
    }

    @Provides
    fun createService(): MarvelAPI {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val interceptor = Interceptor { chain ->
            val timestamp = System.currentTimeMillis().toString()
            val apiKey = BuildConfig.PUBLIC_KEY
            val privateKey = BuildConfig.PRIVATE_KEY
            val hash = HashGenerator.md5(timestamp, privateKey, apiKey)
            var request = chain.request()
            val url = request?.url()?.newBuilder()
                    ?.addQueryParameter("ts", timestamp)
                    ?.addQueryParameter("apikey", apiKey)
                    ?.addQueryParameter("hash", hash)?.build()
            request = request.newBuilder().url(url).build()
            chain?.proceed(request)
        }

        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(interceptor)
                .build()
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        return retrofit.create(MarvelAPI::class.java)
    }


}