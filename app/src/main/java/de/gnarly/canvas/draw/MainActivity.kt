package de.gnarly.canvas.draw

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			Column(
				modifier = Modifier
					.padding(8.dp)
					.fillMaxSize(),
				verticalArrangement = Arrangement.SpaceBetween,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				DrawSimpleCircle(color = Color.Green)
				DrawCircleWithGradient(
					centerColor = Color.Green,
					outerColor = Color.DarkGray
				)
				DrawSimpleRect(color = Color.DarkGray)
				DrawSimpleRoundRect(
					color = Color.LightGray,
					cornerRadius = CornerRadius(10f, 10f)
				)
				DrawSimpleLine(color = Color.Blue)
				DrawSimpleOval(color = Color.Magenta)
				DrawSimpleArc(color = Color.Cyan)
				DrawSimplePath(color = Color.Black)
				DrawSimplePoints(color = Color.Red)
				DrawSimpleOutline(color = Color.Yellow)
				val androidImageBitmap = remember {
					val androidBitmap = BitmapFactory.decodeResource(resources, R.drawable.android)
					androidBitmap.asImageBitmap()
				}
				DrawSimpleImage(image = androidImageBitmap)
				DrawSimpleText(text = "Hello Canvas", 12.sp)
			}

		}
	}
}

@Composable
fun DrawSimpleCircle(
	color: Color,
	modifier: Modifier = Modifier,
	minSize: DpSize = DpSize(32.dp, 32.dp)
) {
	Canvas(modifier = modifier.size(minSize)) {
		drawCircle(color)
	}
}

@Composable
fun DrawCircleWithGradient(
	centerColor: Color,
	outerColor: Color,
	modifier: Modifier = Modifier,
	minSize: DpSize = DpSize(32.dp, 32.dp)
) {
	Canvas(modifier = modifier.size(minSize)) {
		drawCircle(
			brush = Brush.radialGradient(
				colors = listOf(
					centerColor, outerColor
				)
			)
		)
	}
}

@Composable
fun DrawSimpleRect(
	color: Color,
	modifier: Modifier = Modifier,
	minSize: DpSize = DpSize(32.dp, 32.dp)
) {
	Canvas(modifier = modifier.size(minSize)) {
		drawRect(
			color = color
		)
	}
}

@Composable
fun DrawSimpleRoundRect(
	color: Color,
	cornerRadius: CornerRadius,
	modifier: Modifier = Modifier,
	minSize: DpSize = DpSize(32.dp, 32.dp)
) {
	Canvas(modifier = modifier.size(minSize)) {
		drawRoundRect(
			color = color,
			cornerRadius = cornerRadius
		)
	}
}

@Composable
fun DrawSimpleLine(
	color: Color,
	modifier: Modifier = Modifier,
	minSize: DpSize = DpSize(32.dp, 32.dp)
) {
	Canvas(modifier = modifier.size(minSize)) {
		drawLine(
			color = color,
			start = Offset(0f, 0f),
			end = Offset(size.width, size.width),
			strokeWidth = 2.dp.toPx()
		)
	}
}

@Composable
fun DrawSimpleOval(
	color: Color,
	modifier: Modifier = Modifier,
	minSize: DpSize = DpSize(32.dp, 64.dp)
) {
	Canvas(modifier = modifier.size(minSize)) {
		drawOval(
			color = color
		)
	}
}

@Composable
fun DrawSimpleArc(
	color: Color,
	modifier: Modifier = Modifier,
	minSize: DpSize = DpSize(32.dp, 32.dp)
) {
	Canvas(modifier = modifier.size(minSize)) {
		drawArc(
			color = color,
			startAngle = 180f,    // 0f is at 3 o'clock of the circle
			sweepAngle = 180f,    // go 180° from startAngle
			useCenter = false,    // whether to fill the arc or not
			style = Stroke(width = 2.dp.toPx())
		)
	}
}

@Composable
fun DrawSimplePath(
	color: Color,
	modifier: Modifier = Modifier,
	minSize: DpSize = DpSize(32.dp, 32.dp)
) {
	Canvas(modifier = modifier.size(minSize)) {

		val path = Path().apply {
			lineTo(size.width, 0f)    // line from start to end
			quadraticBezierTo(            // arc from the top to  bottom
				size.width / 2, size.height / 2,
				size.width, size.height
			)
			lineTo(0f, size.height) // line from end to start
			quadraticBezierTo(            // arc from bottom to top
				size.width / 2, size.height / 2,
				0f, 0f
			)
			close()                        // link end to start
		}

		drawPath(
			color = color,
			path = path,
			style = Stroke(width = 2.dp.toPx())
		)
	}
}

@Composable
fun DrawSimplePoints(
	color: Color,
	modifier: Modifier = Modifier,
	minSize: DpSize = DpSize(32.dp, 32.dp)
) {
	Canvas(modifier = modifier.size(minSize)) {

		drawPoints(
			points = listOf(
				Offset(0f, 0f),
				Offset(size.width, 0f),
				Offset(size.width, size.height),
				Offset(0f, size.height),
				Offset(size.width / 2, size.height / 2)
			),
			color = color,
			pointMode = PointMode.Points,
			strokeWidth = 2.dp.toPx()
		)
	}
}

@Composable
fun DrawSimpleOutline(
	color: Color,
	modifier: Modifier = Modifier,
	minSize: DpSize = DpSize(32.dp, 32.dp)
) {
	Canvas(modifier = modifier.size(minSize)) {

		drawOutline(
			outline = Outline.Rectangle(Rect(0f, 0f, size.width, size.height)),
			color = color,
			style = Stroke(width = 2.dp.toPx())
		)
	}
}

@Composable
fun DrawSimpleImage(
	image: ImageBitmap,
	modifier: Modifier = Modifier,
	minSize: DpSize = DpSize(32.dp, 32.dp)
) {
	Canvas(modifier = modifier.size(minSize)) {
		drawImage(
			image = image,
			dstSize = IntSize(size.width.toInt(), size.width.toInt()),
			colorFilter = ColorFilter.lighting(Color.Yellow, Color.Red)
		)
	}
}

@Composable
fun DrawSimpleText(
	text: String,
	textSize: TextUnit,
	modifier: Modifier = Modifier,
	minSize: DpSize = DpSize(32.dp, 32.dp)
) {
	Canvas(modifier = modifier.size(minSize)) {
		val paint = android.graphics.Paint()
		paint.textSize = textSize.toPx()
		paint.color = android.graphics.Color.BLACK
		drawIntoCanvas {
			it.nativeCanvas.drawText(text, 0f, 0f, paint)
		}
	}
}