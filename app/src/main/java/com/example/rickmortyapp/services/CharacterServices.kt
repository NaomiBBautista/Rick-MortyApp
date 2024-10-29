package com.example.rickmortyapp.services

import com.example.rickmortyapp.models.Character
import com.example.rickmortyapp.models.CharacterResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterServices {
    @GET("character")
    suspend fun getCharacter(): Character

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterResult
}
