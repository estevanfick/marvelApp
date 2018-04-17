package io.estevanfick.marvelapp.di

import dagger.Component
import io.estevanfick.marvelapp.data.api.ApiManager
import io.estevanfick.marvelapp.ui.character.CharacterDetailActivity
import io.estevanfick.marvelapp.ui.characterlist.CharacterListActivity

@Component (modules = [ApiManager::class])
interface AppComponent {
    fun inject(target: CharacterListActivity)
    fun inject(target: CharacterDetailActivity)
}