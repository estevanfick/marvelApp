package io.estevanfick.marvelapp.data.model


data class Comics(
        val items: List<ComicsItens>
)

data class ComicsItens(
        val name: String
)