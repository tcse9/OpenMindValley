package com.openmindvalley.android.app.presentation.ui.screen.main.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openmindvalley.android.app.R
import com.openmindvalley.android.app.domain.model.Media
import com.openmindvalley.android.app.presentation.theme.OpenMindValleyTheme
import com.openmindvalley.android.app.presentation.theme.SecondaryRootTitle
import com.openmindvalley.android.app.presentation.theme.TextPrimary
import com.openmindvalley.android.app.presentation.theme.TextSecondary
import com.openmindvalley.android.app.presentation.theme.ThumbnailSubtitle

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