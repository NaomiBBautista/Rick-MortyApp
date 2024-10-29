package com.example.rickmortyapp.ui.screens

import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.rickmortyapp.R
import com.example.rickmortyapp.models.CharacterResult
import com.example.rickmortyapp.services.CharacterServices
import com.example.rickmortyapp.ui.components.CardCharacter
import com.example.rickmortyapp.ui.theme.blackOps
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController) {
    var characters by remember { mutableStateOf(listOf<CharacterResult>()) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch {
            try {
                val BASE_URL = "https://rickandmortyapi.com/api/"
                val characterServices = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CharacterServices::class.java)

                isLoading = true
                val response = characterServices.getCharacter()
                isLoading = false
                characters = response.results
            } catch (e: HttpException) {
                isLoading = false
            }
        }
    }

    if (isLoading) {
        // Circular progress bar
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    else {
        // Mostrar los personajes
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black) ){
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.background2),
                contentDescription = "Fondo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.4f)

            // LazyColumn encima de la imagen
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 80.dp, end = 16.dp, bottom = 30.dp)) {
                items(characters) { c ->
                    CardCharacter(
                        name = c.name,
                        specie = c.species,
                        status = c.status,
                        image = c.image,
                        onClick = { navController.navigate("detail/${c.id}") }
                    )
                }
            }
        }
    }
}

//@Composable
//@Preview
//fun HomeScreenPreview()
//{
//    HomeScreen(innerPadding = PaddingValues(0.dp))
//}