package com.example.wallpaper20


import android.Manifest
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.wallpaper20.ui.theme.download
import com.example.wallpaper20.ui.theme.textcolor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.ArrayList


enum class wallpaperType {
    HOME, LOCK, BOTH
}

class maxsizewallpaper : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val imageList = intent.getIntegerArrayListExtra("images")!!
        val index = intent.getIntExtra("index", 0)
        setContent {
            Myboxs(imageList, index)
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun Myboxs(imageList: ArrayList<Int>, index: Int) {
        Box(modifier = Modifier.fillMaxSize())
        {
            var pagerState = rememberPagerState(pageCount = {
                imageList.size
            }, initialPage = index)

            val bottomSheetState = rememberModalBottomSheetState()
            var isShowBottomSheet by remember { mutableStateOf(false) }

            if (isShowBottomSheet) {
                ModalBottomSheet(onDismissRequest = {
                    isShowBottomSheet = false
                }, sheetState = bottomSheetState) {
                    Row {
                        Button(
                            onClick = {
                                setMyWallpaper(
                                    imageList[pagerState.currentPage],
                                    wallpaperType.LOCK
                                )
                            },
                            modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        )
                        {
                            Image(
                                painter = painterResource(R.drawable.lock),
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                        Button(
                            onClick = {
                                setMyWallpaper(
                                    imageList[pagerState.currentPage],
                                    wallpaperType.HOME
                                )
                            },
                            modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        )
                        {
                            Image(
                                painter = painterResource(R.drawable.home),
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                        Button(
                            onClick = {
                                setMyWallpaper(
                                    imageList[pagerState.currentPage],
                                    wallpaperType.BOTH
                                )
                            },
                            modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        )
                        {
                            Image(
                                painter = painterResource(R.drawable.wallpaper),
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    }
                }
            }


            HorizontalPager(
                state = pagerState, modifier = Modifier.fillMaxSize()
            ) { page ->
                Box()
                {
                    GlideImage(
                        model = imageList[page],
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(
                                radiusX = 25.dp,
                                radiusY = 25.dp,
                                edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp)),
                            )
                    )
                    GlideImage(
                        model = imageList[page],
                        contentScale = ContentScale.Inside,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            IconButton(
                onClick = {
                    finish()
                },
                modifier = Modifier
                    .padding(top = 60.dp)
                    .align(Alignment.TopStart)
            )
            {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = Color.White
                )
            }
            Row(modifier = Modifier.align(Alignment.BottomCenter))
            {
                Button(
                    onClick = {
                        downloadWallpaper(imageList[pagerState.currentPage])
                    },
                    modifier = Modifier
                        .padding(bottom = 40.dp, start = 2.dp, end = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))
                )
                {
                    Icon(
                        painter = rememberVectorPainter(download),
                        contentDescription = "Download",
                        tint = textcolor, modifier = Modifier.size(27.dp)
                    )
                }
                Button(
                    onClick = {
                        isShowBottomSheet = true
                    },
                    modifier = Modifier
                        .padding(bottom = 40.dp, start = 2.dp, end = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.wallpaper),
                        contentDescription = "wallpaper",
                        tint = textcolor, modifier = Modifier.size(27.dp)
                    )
                }
                Button(
                    onClick = {
                        shareImge(imageList[pagerState.currentPage])
                    },
                    modifier = Modifier
                        .padding(bottom = 40.dp, start = 2.dp, end = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = "wallpaper",
                        tint = textcolor, modifier = Modifier.size(27.dp)
                    )
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun shareImge(resourcesId: Int) {
        GlobalScope.launch {
            val bitmap = BitmapFactory.decodeResource(resources, resourcesId)
            val imagaforder = File(getCacheDir(), "images")
            var uri: Uri? = null
            try {
                imagaforder.mkdirs() // for Image folder create
                val file = File(imagaforder, "shared_image.png")
                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                uri = FileProvider.getUriForFile(
                    this@maxsizewallpaper,
                    "com.example.downloading.file provider",
                    file
                )

                val intent = Intent(Intent.ACTION_SEND)
                // putting uri of image to be shared
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                // adding text to share
                intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image")
                // Add subject Here
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
                // setting type to image
                intent.setType("image/*")
                // calling startactivity() to share
                startActivity(Intent.createChooser(intent, "Share Via"))

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@maxsizewallpaper, "" + e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun downloadWallpaper(resourceId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Decode large image efficiently
                val options = BitmapFactory.Options().apply {
                    inPreferredConfig = Bitmap.Config.ARGB_8888 // High quality
                }

                val bitmap = BitmapFactory.decodeResource(
                    this@maxsizewallpaper.resources,
                    resourceId,
                    options
                )

                // Use timestamp for unique filename
                val fileName = "wallpaper_${System.currentTimeMillis()}.jpg"
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    fileName
                )

                // Compress and write
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                }

                // Make file visible in gallery
                MediaScannerConnection.scanFile(
                    this@maxsizewallpaper,
                    arrayOf(file.absolutePath),
                    arrayOf("image/jpeg"),
                    null
                )

                // Show toast on main thread
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@maxsizewallpaper,
                        "Download successful: $fileName",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@maxsizewallpaper, "Download failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    private fun setMyWallpaper(resourceId: Int, type: wallpaperType) {
        val m: WallpaperManager = WallpaperManager.getInstance(this@maxsizewallpaper)
        when (type) {
            wallpaperType.HOME -> m.setResource(
                resourceId,
                WallpaperManager.FLAG_SYSTEM
            ) // only Background
            wallpaperType.LOCK -> m.setResource(resourceId, WallpaperManager.FLAG_LOCK) // only Lock
            wallpaperType.BOTH -> m.setResource(resourceId) // both
        }
    }
}
