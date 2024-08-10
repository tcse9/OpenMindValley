package com.openmindvalley.android.app.presentation.ui.screen.main.composables

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.openmindvalley.android.app.R
import com.openmindvalley.android.app.presentation.theme.OpenMindValleyTheme
import com.openmindvalley.android.app.presentation.theme.TextPrimary
import com.openmindvalley.android.app.presentation.theme.ThumbnailSubtitle
import com.openmindvalley.android.app.presentation.theme.ThumbnailTitle
import com.openmindvalley.android.app.utils.isNotNullOrEmpty

@Composable
fun Thumbnail(isPortrait: Boolean = true, imageUrl: String? = null, title: String? = null, subTitle: String? = null) {
    var thumbnailWidth by remember {
        mutableStateOf(152.dp)
    }
    var thumbnailHeight by remember {
        mutableStateOf(228.dp)
    }
    if (!isPortrait) {
        thumbnailWidth = 320.dp
        thumbnailHeight = 172.dp
    }

    var isLoaded by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isLoaded) 1f else 0.75f,
        animationSpec = tween(durationMillis = 500)
    )

    Column(modifier = Modifier.width(thumbnailWidth)) {
        val request = ImageRequest.Builder(LocalContext.current).listener(object : ImageRequest.Listener{
            override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                super.onSuccess(request, result)
                isLoaded = true
            }

            override fun onError(request: ImageRequest, result: ErrorResult) {
                super.onError(request, result)
                isLoaded = false
            }
        }).data(imageUrl).build()

        AsyncImage(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .size(thumbnailWidth, thumbnailHeight)
                .scale(scale)
                .background(color = MaterialTheme.colorScheme.secondary),
            contentScale = ContentScale.Crop,
            model = request,
            contentDescription = null,
        )
        if (title.isNotNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title ?: "", maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.ThumbnailTitle)
        }
        if (subTitle.isNotNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = subTitle ?: "",  maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.ThumbnailSubtitle)
        }
    }
}

@Composable
fun LoadMoreThumbnail(isPortrait: Boolean = true) {
    val context = LocalContext.current

    var thumbnailWidth = 152.dp
    var thumbnailHeight = 228.dp
    if (!isPortrait) {
        thumbnailWidth = 320.dp
        thumbnailHeight = 172.dp
    }
    Box(
        modifier = Modifier
            .size(width = thumbnailWidth, height = thumbnailHeight)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        OutlinedButton(onClick = { Toast.makeText(context,
            context.getString(R.string.generic_msg_under_construction), Toast.LENGTH_SHORT).show() }) {
            Text(
                text = stringResource(R.string.load_more),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.ThumbnailSubtitle.copy(color = TextPrimary)
            )
        }
    }
}

@Composable
@Preview
fun Prev_LoadMoreThumbnail() {
    LoadMoreThumbnail(isPortrait = true)
}

@Composable
@Preview
fun Prev_Thumbnail() {
    OpenMindValleyTheme {
        Thumbnail(imageUrl = "https://i.ytimg.com/vi/z5AExTtw/maxresdefault.jpg")
    }
}