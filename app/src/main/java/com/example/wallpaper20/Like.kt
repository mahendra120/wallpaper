package com.example.wallpaper20

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.wallpaper20.ui.theme.fffff
import com.example.wallpaper20.ui.theme.textcolor

class Like : ComponentActivity() {

    var list33 by mutableStateOf(arrayOf<Int>())
    lateinit var mainSet: HashSet<Int>
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sp = getSharedPreferences("shayari-app", MODE_PRIVATE)
        mainSet =
            sp.getStringSet("favorite", emptySet())?.map { it.toInt() }?.toHashSet() ?: HashSet()

        list33 = mainSet.toTypedArray()

        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = { Mybar() })
            { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
                {
                    Mybackraund(list33)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Mybar() {
        TopAppBar(
            title = { Text(text = "LIKE", fontSize = 20.sp, color = textcolor) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = fffff),
            navigationIcon = {
                IconButton(onClick = {
                    var intent = Intent(this@Like, MainActivity::class.java)
                    startActivity(intent)
                })
                {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = textcolor
                    )
                }
            }
        )
    }

    @Composable
    fun Mybackraund(list331: Array<Int>) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.blacklogo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Mytitit(list331)
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun Mytitit(list331: Array<Int>) {
        LazyVerticalGrid(columns = GridCells.Fixed(2))
        {
            items(list33.size) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    onClick = {}
                )
                {
                    GlideImage(model = list331[it], contentDescription = null)
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp, end = 5.dp),
                    contentAlignment = Alignment.TopEnd
                )
                {
                    IconButton(onClick = {
                        val string = mainSet.map { it.toString() }.toSet()
                        mainSet.remove(list33[it])
                        sp.edit().apply{
                            putStringSet("favorite", string)
                        }
                        list33 = mainSet.toTypedArray()

                    })
                    {
                        Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.Red)
                    }
                }
            }
        }
    }
}
