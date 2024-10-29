package com.example.rickmortyapp.models

data class Character(
    val info: Info,
    val results: List<CharacterResult>
)