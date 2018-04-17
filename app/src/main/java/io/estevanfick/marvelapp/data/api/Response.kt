package io.estevanfick.marvelapp.data.api

import io.estevanfick.marvelapp.data.model.Character


class Response (
        val code: Int,
        val status: String,
        val data: ResponseData
)

class ResponseData (
        val total: Int,
        val results: List<Character>
)