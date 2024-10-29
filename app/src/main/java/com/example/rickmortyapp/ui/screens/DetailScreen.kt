package com.example.rickmortyapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rickmortyapp.R
import com.example.rickmortyapp.models.Character
import com.example.rickmortyapp.models.CharacterResult
import com.example.rickmortyapp.models.Location
import com.example.rickmortyapp.models.Origin
import com.example.rickmortyapp.services.CharacterServices
import com.example.rickmortyapp.ui.theme.blackOps
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun DetailScreen(innerPadding: PaddingValues, characterId: Int){
    var character by remember { mutableStateOf(CharacterResult(
        created = "",
        episode = listOf(),
        gender = "",
        id = 0,
        image = "",
        location = Location(name = "", url = ""),
        name = "",
        origin = Origin(name = "", url = ""),
        species = "",
        status = "",
        type = "",
        url = ""
    ))}
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
                val response = characterServices.getCharacterById(characterId)
                isLoading = false
                character = response
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(),
            contentAlignment = Alignment.Center
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.background2),
                contentDescription = "Fondo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.3f)

//            AsyncImage(
//                model = character.image,
//                contentDescription = "Fondo",
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop,
//                alpha = 0.3f)

            Box(
                modifier = Modifier
                    .width(350.dp)
                    .height(500.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.Black.copy(alpha = 0.75f))) {

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AsyncImage(
                        model = character.image,
                        contentDescription = character.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)),
                        contentScale = ContentScale.Crop,
                    )

                    Text(
                        text = character.name,
                        fontSize = 30.sp,
                        fontFamily = blackOps,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        modifier = Modifier.padding(start = 30.dp, top = 8.dp)
                    )

                    Text(
                        text = "Specie: " + character.species,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        modifier = Modifier.padding(start = 30.dp, top = 2.dp))

                    Text(
                        text = "Status: " + character.status,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        modifier = Modifier.padding(start = 30.dp, top = 2.dp))

                    Text(
                        text = "Gender: " + character.gender,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        modifier = Modifier.padding(start = 30.dp, top = 2.dp))

                    Text(
                        text = "Origin: " + character.origin.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        modifier = Modifier.padding(start = 30.dp, top = 2.dp))

                    Text(
                        text = "Location: " + character.location.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        modifier = Modifier.padding(start = 30.dp, top = 2.dp))
                }
            }
        }


    }
}


@Composable
@Preview
fun DetailPreview(){
    DetailScreen(innerPadding = PaddingValues(0.dp), 1)
}