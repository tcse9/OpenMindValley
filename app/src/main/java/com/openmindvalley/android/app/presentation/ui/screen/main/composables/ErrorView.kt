package com.openmindvalley.android.app.presentation.ui.screen.main.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openmindvalley.android.app.R
import com.openmindvalley.android.app.presentation.theme.OpenMindValleyTheme
import com.openmindvalley.android.app.presentation.theme.TextPrimary
import com.openmindvalley.android.app.presentation.theme.ThumbnailSubtitle
import com.openmindvalley.android.app.presentation.theme.ThumbnailTitle

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
        Text(text = errorMessage ?: "", style = MaterialTheme.typography.ThumbnailSubtitle.copy(color = TextPrimary), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = { onRetryClicked() }) {
            Text(text = stringResource(R.string.retry), style = MaterialTheme.typography.ThumbnailSubtitle.copy(color = TextPrimary), textAlign = TextAlign.Center)
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