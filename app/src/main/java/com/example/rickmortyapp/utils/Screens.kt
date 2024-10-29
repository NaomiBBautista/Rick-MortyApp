package com.example.rickmortyapp.utils

sealed class Screens(val route: String) {
    data object Home: Screens("home")
    data object Detalles: Screens("detail")
}