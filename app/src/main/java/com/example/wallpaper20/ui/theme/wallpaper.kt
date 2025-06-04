package com.example.wallpaper20.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

// In your Theme.kt file where you define the ImageVector
val wallpaper: ImageVector
    get() {
        if (_wallpaper != null) {
            return _wallpaper!!
        }
        _wallpaper = ImageVector.Builder(
            name = "wallpaper",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(680f, 320f)
                quadToRelative(-17f, 0f, -28.5f, -11.5f)
                reflectiveQuadTo(640f, 280f)
                reflectiveQuadToRelative(11.5f, -28.5f)
                reflectiveQuadTo(680f, 240f)
                reflectiveQuadToRelative(28.5f, 11.5f)
                reflectiveQuadTo(720f, 280f)
                reflectiveQuadToRelative(-11.5f, 28.5f)
                reflectiveQuadTo(680f, 320f)
                moveTo(360f, 560f)
                lineToRelative(108f, -140f)
                lineToRelative(62f, 80f)
                lineToRelative(92f, -120f)
                lineToRelative(138f, 180f)
                close()
                moveTo(160f, 880f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(80f, 800f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(560f)
                horizontalLineToRelative(560f)
                verticalLineToRelative(80f)
                close()
                moveToRelative(80f, -505f)
                verticalLineToRelative(-215f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(320f, 80f)
                horizontalLineToRelative(200f)
                verticalLineToRelative(80f)
                horizontalLineTo(320f)
                verticalLineToRelative(215f)
                close()
                moveToRelative(80f, 345f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(240f, 640f)
                verticalLineToRelative(-185f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(185f)
                horizontalLineToRelative(200f)
                verticalLineToRelative(80f)
                close()
                moveToRelative(280f, 0f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(200f)
                verticalLineToRelative(-185f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(185f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(800f, 720f)
                close()
                moveToRelative(200f, -345f)
                verticalLineToRelative(-215f)
                horizontalLineTo(600f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(200f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(880f, 160f)
                verticalLineToRelative(215f)
                close()
            }
        }.build()
        return _wallpaper!!
    }

private var _wallpaper: ImageVector? = null