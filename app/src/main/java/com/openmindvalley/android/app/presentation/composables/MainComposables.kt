package com.openmindvalley.android.app.presentation.composables

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.openmindvalley.android.app.R
import com.openmindvalley.android.app.domain.model.Media
import com.openmindvalley.android.app.domain.model.ThumbnailItem
import com.openmindvalley.android.app.presentation.theme.ChannelHeaderGradient1
import com.openmindvalley.android.app.presentation.theme.ChannelHeaderGradient2
import com.openmindvalley.android.app.presentation.theme.OpenMindValleyTheme
import com.openmindvalley.android.app.presentation.theme.RootTitle
import com.openmindvalley.android.app.presentation.theme.SecondaryRootTitle
import com.openmindvalley.android.app.presentation.theme.SecondaryRootTitleWhite
import com.openmindvalley.android.app.presentation.theme.TextPrimary
import com.openmindvalley.android.app.presentation.theme.TextSecondary
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
                || viewModel.mediaStateChannel.value.error != null
                || viewModel.mediaStateCategories.value.error != null
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
                        HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp), color = MaterialTheme.colorScheme.tertiary, thickness = 1.dp)
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
fun Thumbnail(isPortrait: Boolean = true, imageUrl: String? = null, title: String? = null, subTitle: String? = null) {
    var thumbnailWidth = 152.dp
    var thumbnailHeight = 228.dp
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
                style = MaterialTheme.typography.ThumbnailSubtitle.copy(color = Color.White)
            )
        }
    }
}

@Composable
fun ShortSnackbar(
    message: String,
    actionLabel: String?,
    duration: SnackbarDuration = SnackbarDuration.Short,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarHostState) {
        snackbarHostState.showSnackbar(message, actionLabel, duration = duration)
    }

    SnackbarHost(
        hostState = snackbarHostState,
    )
}

@Composable
fun ErrorView(errorTitle: String? = stringResource(id = R.string.error), errorMessage: String? = null, onRetryClicked: () -> Unit = {}) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Row {
            Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = errorTitle ?: "", style = MaterialTheme.typography.ThumbnailTitle, textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = errorMessage ?: "", style = MaterialTheme.typography.ThumbnailSubtitle.copy(color = Color.White), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = { onRetryClicked() }) {
            Text(text = stringResource(R.string.retry), style = MaterialTheme.typography.ThumbnailSubtitle.copy(color = Color.White), textAlign = TextAlign.Center)
        }

    }
}

@Composable
@Preview
fun Prev_ErrorView() {
    OpenMindValleyTheme {
        ErrorView(errorMessage = stringResource(R.string.generic_something_went_wrong))
    }
}

@Composable
@Preview
fun Prev_LoadMoreThumbnail() {
    LoadMoreThumbnail(isPortrait = true)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryFlowRow(categories: List<Media>?) {
    Column {
        Text(modifier = Modifier.padding(all = 16.dp), text = stringResource(R.string.browse_by_categories),  style = MaterialTheme.typography.SecondaryRootTitle)
        FlowRow(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp)) {
            categories?.get(0)?.categoryNames?.forEach {
                CateGoryChip(name = it)
            }
        }
    }
}

@Composable
fun CateGoryChip(name: String) {
    Row(
        modifier = Modifier
            .clickable(enabled = true, onClick = {})
            .padding(8.dp)
            .background(
                color = TextSecondary.copy(alpha = 0.20f),
                shape = RoundedCornerShape(36.dp)
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = name,
            style = MaterialTheme.typography.ThumbnailSubtitle.copy(color = TextPrimary)
        )
    }
}

@Composable
@Preview
fun Prev_CateGoryChip() {
    OpenMindValleyTheme {
        CateGoryChip(name = "Comedy")
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