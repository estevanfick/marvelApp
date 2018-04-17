package io.estevanfick.marvelapp.ui.characterlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import io.estevanfick.marvelapp.App
import io.estevanfick.marvelapp.R
import io.estevanfick.marvelapp.data.api.NetworkStatus
import io.estevanfick.marvelapp.data.repository.CharacterRepository
import io.estevanfick.marvelapp.ui.character.CharacterDetailActivity
import kotlinx.android.synthetic.main.activity_character_list.*
import javax.inject.Inject

class CharacterListActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: CharacterRepository

    var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        (application as App).appComponent.inject(this)

        val adapter = CharacterListAdapter {
            val intent = Intent(this, CharacterDetailActivity::class.java)
            intent.putExtra("character", it)
            startActivity(intent)
        }
        recyclerCharacter.adapter = adapter
        recyclerCharacter.layoutManager = GridLayoutManager(this, 2)

        val viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CharacterListViewModel(repository) as T
            }
        }).get(CharacterListViewModel::class.java)
        viewModel.characterList.observe(this, Observer {
            adapter.submitList(it)
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
        snackBar = Snackbar.make(layoutCharacterList, message, Snackbar.LENGTH_INDEFINITE)
        actionName.let {
            snackBar?.setAction(actionName) { action?.invoke() }
        }
        snackBar?.show()
    }

    fun hideSnackbar(){
        snackBar?.dismiss()
    }
}
