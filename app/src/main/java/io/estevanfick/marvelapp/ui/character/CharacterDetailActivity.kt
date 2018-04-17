package io.estevanfick.marvelapp.ui.character

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.squareup.picasso.Picasso
import io.estevanfick.marvelapp.App
import io.estevanfick.marvelapp.R
import io.estevanfick.marvelapp.data.api.NetworkStatus
import io.estevanfick.marvelapp.data.model.Character
import io.estevanfick.marvelapp.data.repository.CharacterRepository
import kotlinx.android.synthetic.main.activity_character.*
import javax.inject.Inject

class CharacterDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: CharacterRepository

    var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        (application as App).appComponent.inject(this)

        val characterId = intent.getIntExtra("character", 0)

        recyclerComics.layoutManager = LinearLayoutManager(this)
        recyclerComics.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        val adapter = ComicsAdapter()
        recyclerComics.adapter = adapter

        val viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CharacterDetailViewModel(repository) as T
            }
        }).get(CharacterDetailViewModel::class.java)
        viewModel.getCharacter(characterId)
        viewModel.character.observe(this, Observer {
            Log.d("TAF", it.toString())
            populateData(it)
            adapter.submitList(it?.comics?.items)
        })
        viewModel.networkStatus.observe(this, Observer {
            when (it) {
                NetworkStatus.RUNNING -> {
                    showSnackbar("Loading...")
                }
                NetworkStatus.SUCCESS -> {
                    hideSnackbar()
                }
                NetworkStatus.FAILED -> {
                    showSnackbar("Internet Error", "Close"){
                        hideSnackbar()
                    }
                }
            }
        })
    }

    fun showSnackbar(message: String, actionName: String? = null, action: (() -> Unit)? = null ) {
        snackBar = Snackbar.make(layoutCharacterDetail, message, Snackbar.LENGTH_INDEFINITE)
        actionName.let {
            snackBar?.setAction(actionName) { action?.invoke() }
        }
        snackBar?.show()
    }

    fun hideSnackbar(){
        snackBar?.dismiss()
    }

    private fun populateData(character: Character?) {
        title = character?.name
        txtCharacterDetailName.text = character?.name
        txtCharacterDetailDescription.text = character?.description
        Picasso.get()
                .load(character?.linkImage)
                .into(imgCharacterDetail)
    }
}
