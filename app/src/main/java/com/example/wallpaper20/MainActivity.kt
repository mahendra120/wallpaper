package com.example.wallpaper20

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.wallpaper20.ui.theme.fffff
import com.example.wallpaper20.ui.theme.textcolor


@SuppressLint("MutableCollectionMutableState")
class MainActivity : ComponentActivity() {
    val sp: SharedPreferences by lazy {
        getSharedPreferences("shayari-app", MODE_PRIVATE)
    }
    var selectedCategory by mutableStateOf("Home")
    var showImages by mutableStateOf(ArrayList<Int>())
    lateinit var mainSet: HashSet<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        applyFilter()
        mainSet =
            sp.getStringSet("favorite", emptySet())?.map { it.toInt() }?.toHashSet() ?: HashSet()
        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { Mybar() }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding))
                {
                    Mybackraund()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Mybar() {
        TopAppBar(
            title = { Text(text = "Wallpapers", fontSize = 23.sp, color = textcolor) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = fffff),
            navigationIcon = {
                IconButton(onClick = { finish() })
                {
                    Icon(
                        Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        tint = textcolor
                    )
                }
            }, actions = {
                IconButton(onClick = {
                    var intent = Intent(this@MainActivity, Like::class.java)
                    startActivity(intent)
                })
                {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = textcolor)
                }
            }
        )
    }

    @SuppressLint("SuspiciousIndentation")
    @Composable
    @Preview(showSystemUi = true)
    fun Mybackraund() {
        Box(modifier = Modifier.fillMaxSize())
        {
            Image(
                painter = painterResource(R.drawable.blacklogo),
                contentDescription = null,
                contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()
            )
            Column {
                Mybars()
                Mycards()
            }
        }
    }

    @Composable
    fun Mybars() {
        LazyRow {
            items(list.size)
            {
                Button(
                    onClick = {
                        selectedCategory = list[it]
                        applyFilter()
                    },
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = BorderStroke(2.dp, Color.White)
                )
                {
                    Text(text = list[it], fontSize = 13.sp, color = textcolor)
                }
            }

        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun Mycards() {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(showImages.size)
            {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp), onClick = {
                        val intent = Intent(this@MainActivity, maxsizewallpaper::class.java)
                        intent.putExtra("images", showImages)
                        intent.putExtra("index", it)
                        startActivity(intent)
                    }
                )
                {
                    GlideImage(model = showImages[it], contentDescription = null)
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 6.dp, top = 6.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    var isFavorite by remember {
                        mutableStateOf(mainSet.contains(showImages[it]))
                    }
                    IconButton(
                        onClick = {
                            val imageId = showImages[it]
                            if (mainSet.contains(imageId)) {
                                mainSet.remove(imageId)
                            } else {
                                mainSet.add(imageId)
                            }
                            sp.edit {
                                putStringSet("favorite", mainSet.map { it.toString() }.toSet())
                            }
                            isFavorite = !isFavorite
                        }
                    ) {
                        Icon(
                            if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.Red.copy(0.8f),
                            modifier = Modifier.size(26.dp)
                        )
                    }

                }
            }
        }
    }

    fun applyFilter() {
        val listttt = ArrayList<Int>()
        if (selectedCategory == list[0]) {
            shayariMap.values.forEach {
                listttt.addAll(it)
            }
        } else {
            listttt.addAll(shayariMap[selectedCategory]!!)
        }
        showImages = listttt
    }
}
