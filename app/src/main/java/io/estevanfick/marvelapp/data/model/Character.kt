package io.estevanfick.marvelapp.data.model


data class Character (
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: Thumbnail,
        val comics: Comics
) {
    val linkImage: String
        get() = "${thumbnail.path}/portrait_medium.${thumbnail.extension}"
}