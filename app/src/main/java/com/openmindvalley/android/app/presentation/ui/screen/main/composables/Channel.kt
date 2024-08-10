package com.openmindvalley.android.app.presentation.ui.screen.main.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.openmindvalley.android.app.presentation.theme.TextPrimary
import com.openmindvalley.android.app.presentation.theme.ThumbnailSubtitleSecondary
import com.openmindvalley.android.app.presentation.ui.screen.main.viewmodel.MainViewModel
import com.openmindvalley.android.app.utils.isNotNullOrEmpty

@Composable
fun NewEpisode(mediaList: List<Media>?) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(all = 16.dp)) {

        val originalList = mediaList?.get(0)?.list
        val thumbnailItems = mediaList?.get(0)?.list?.take(6)

        if (thumbnailItems.isNotNullOrEmpty()) {
            Text(text = stringResource(R.string.channels),  style = MaterialTheme.typography.RootTitle)
            Spacer(modifier = Modifier.height(28.dp))
            Text(text = stringResource(R.string.new_episodes),  style = MaterialTheme.typography.SecondaryRootTitle)
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow {
                itemsIndexed(thumbnailItems!!) { index, item ->
                    Thumbnail(imageUrl = item.thumbnailImage, title = item.title, subTitle = item.channelTitle)
                    if (index < thumbnailItems.size - 1) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
                if (originalList.isNotNullOrEmpty() && originalList?.size!! > 6) {
                    item {
                        Spacer(modifier = Modifier.width(16.dp))
                        LoadMoreThumbnail(isPortrait = originalList[0].isPortrait ?: true)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Channel(modifier: Modifier, viewModel: MainViewModel) {
    val state = rememberPullToRefreshState()
    if (state.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.loadData()
            state.endRefresh()
        }
    }
    Box(Modifier.nestedScroll(state.nestedScrollConnection)) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            val newEpisode = viewModel.mediaStateNewEpisode.value.data
            val mediaList = viewModel.mediaStateChannel.value.data

            if (viewModel.mediaStateNewEpisode.value.error != null
                && viewModel.mediaStateChannel.value.error != null
                && viewModel.mediaStateCategories.value.error != null
            ) {
                item {
                    ErrorView(errorMessage = stringResource(R.string.generic_something_went_wrong)) {
                        viewModel.loadData()
                    }
                }
            }


            if (!state.isRefreshing) {
                if (mediaList.isNotNullOrEmpty()) {
                    item {
                        NewEpisode(mediaList = newEpisode)
                    }
                    item {
                        HorizontalDivider(modifier = Modifier.padding(all = 16.dp), color = MaterialTheme.colorScheme.tertiary, thickness = 1.dp)
                    }

                    itemsIndexed(mediaList!!) { index, item ->
                        val typeName: String = if (item.isMediaTypeSeries) stringResource(R.string.series) else stringResource(
                            R.string.episodes
                        )
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

                    item {
                        HorizontalDivider(modifier = Modifier.padding(all = 16.dp), color = MaterialTheme.colorScheme.tertiary, thickness = 1.dp)
                    }
                    item {
                        CategoryFlowRow(categories = viewModel.mediaStateCategories.value.data)
                    }
                }
            }
        }
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = state,
        )
    }
}

@Composable
fun ChannelHeader(title: String, subTitle: String?, iconUrl: String? = null) {
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

            val request = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .placeholder(R.drawable.default_header_icon)
                .error(R.drawable.default_header_icon)
                .build()

            AsyncImage(
                model = request,
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.width(width = 16.dp))
        Column {
            if (title.isNotNullOrEmpty()) {
                Text(text = title, style = MaterialTheme.typography.SecondaryRootTitle.copy(color = TextPrimary))
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
    val customRangeList = thumbnailItems?.take(6)
    if (customRangeList.isNullOrEmpty()) {
        return
    }
    LazyRow(modifier = Modifier.padding(all = 16.dp)) {
        itemsIndexed(customRangeList) { index, item ->
            Thumbnail(
                isPortrait = item.isPortrait ?: true,
                imageUrl = item.thumbnailImage,
                title = item.title,
                subTitle = item.channelTitle
            )
            if (index < customRangeList.size - 1) {
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
        if (thumbnailItems.isNotNullOrEmpty() && thumbnailItems.size > 6) {
            item {
                Spacer(modifier = Modifier.width(16.dp))
                LoadMoreThumbnail(isPortrait = thumbnailItems[0].isPortrait ?: true)
            }
        }
    }
}

@Composable
@Preview
fun Prev_NewEpisode() {
    OpenMindValleyTheme {
        NewEpisode(mediaList = null)
    }
}