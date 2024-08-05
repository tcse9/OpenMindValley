package com.openmindvalley.android.app.presentation.composables

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.openmindvalley.android.app.R
import com.openmindvalley.android.app.domain.model.Media
import com.openmindvalley.android.app.domain.model.ThumbnailItem
import com.openmindvalley.android.app.presentation.theme.ChannelHeaderGradient1
import com.openmindvalley.android.app.presentation.theme.ChannelHeaderGradient2
import com.openmindvalley.android.app.presentation.theme.OpenMindValleyTheme
import com.openmindvalley.android.app.presentation.theme.RootTitle
import com.openmindvalley.android.app.presentation.theme.SecondaryRootTitle
import com.openmindvalley.android.app.presentation.theme.SecondaryRootTitleWhite
import com.openmindvalley.android.app.presentation.theme.ThumbnailSubtitle
import com.openmindvalley.android.app.presentation.theme.ThumbnailSubtitleSecondary
import com.openmindvalley.android.app.presentation.theme.ThumbnailTitle
import com.openmindvalley.android.app.presentation.viewmodels.MainViewModel
import com.openmindvalley.android.app.utils.isNotNullOrEmpty

@Composable
fun RootView(paddingValues: PaddingValues, viewModel: MainViewModel = hiltViewModel()) {
    Channel(modifier = Modifier.padding(paddingValues), viewModel = viewModel)
}

@Composable
fun NewEpisode(mediaList: List<Media>?) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(all = 16.dp)) {
        Text(text = "Channels",  style = MaterialTheme.typography.RootTitle)
        Spacer(modifier = Modifier.height(28.dp))
        Text(text = "New Episodes",  style = MaterialTheme.typography.SecondaryRootTitle)
        val thumbnailItem = mediaList?.get(0)?.list
        if (thumbnailItem.isNotNullOrEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow {
                itemsIndexed(thumbnailItem!!) { index, item ->
                    Thumbnail(imageUrl = item.thumbnailImage, title = item.title, subTitle = item.channelTitle)
                    if (index < thumbnailItem.size - 1) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun Channel(modifier: Modifier, viewModel: MainViewModel) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        val newEpisode = viewModel.mediaStateNewEpisode.value.data
        item {
            NewEpisode(mediaList = newEpisode)
        }
        item {
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp), color = MaterialTheme.colorScheme.tertiary, thickness = 1.dp)
        }

        val mediaList = viewModel.mediaStateChannel.value.data
        if (mediaList.isNotNullOrEmpty()) {
            itemsIndexed(mediaList!!) { index, item ->
                val typeName: String = if (item.isSeries) "series" else "episodes"
                val countText = "${item.mediaCount} $typeName"
                ChannelHeader(title = item.title ?: "", subTitle = if (item.mediaCount > 0) countText else null)
                ChannelRow(item.list)
                if (index < mediaList.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(all = 16.dp),
                        color = MaterialTheme.colorScheme.tertiary,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ChannelHeader(title: String, subTitle: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(size = 50.dp)
                .clip(shape = CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            ChannelHeaderGradient1,
                            ChannelHeaderGradient2
                        )
                    )
                ), contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(size = 32.dp),
                painter = painterResource(id = R.drawable.channel_header1),
                tint = Color.White,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(width = 16.dp))
        Column {
            if (title.isNotNullOrEmpty()) {
                Text(text = title, style = MaterialTheme.typography.SecondaryRootTitleWhite)
            }
            if (subTitle.isNotNullOrEmpty()) {
                Spacer(modifier = Modifier.height(height = 8.dp))
                Text(text = subTitle ?: "", style = MaterialTheme.typography.ThumbnailSubtitleSecondary)
            }
        }
    }
}

@Composable
fun ChannelRow(thumbnailItems: List<ThumbnailItem>?) {
    if (thumbnailItems.isNullOrEmpty()) {
        return
    }
    LazyRow(modifier = Modifier.padding(all = 16.dp)) {
        itemsIndexed(thumbnailItems!!) { index, item ->
            Thumbnail(
                isPortrait = item.isPortrait ?: true,
                imageUrl = item.thumbnailImage,
                title = item.title,
                subTitle = item.channelTitle
            )
            if (index < thumbnailItems.size - 1) {
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun Thumbnail(isPortrait: Boolean = true, imageUrl: String? = null, title: String? = null, subTitle: String? = null) {
    var thumbnailWidth = 152.dp
    var thumbnailHeight = 228.dp
    if (!isPortrait) {
        thumbnailWidth = 320.dp
        thumbnailHeight = 172.dp
    }
    Column(modifier = Modifier.width(thumbnailWidth)) {
        val request = ImageRequest.Builder(LocalContext.current).data(imageUrl).build()
        AsyncImage(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .size(thumbnailWidth, thumbnailHeight)
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
@Preview
fun Prev_Thumbnail() {
    OpenMindValleyTheme {
        Thumbnail(imageUrl = "https://i.ytimg.com/vi/z5AExTtw/maxresdefault.jpg")
    }
}

@Composable
@Preview
fun Prev_NewEpisode() {
    OpenMindValleyTheme {
        NewEpisode(mediaList = null)
    }
}