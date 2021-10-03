package com.example.androidtask.presentation.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.androidtask.business.domain.model.media.Media
import com.example.androidtask.presentation.Component
import javax.inject.Inject

class NewEpisodesSection @Inject constructor(
    private val component: Component,
    ) {
    @Composable
     fun newEpisodesDesign(media: List<Media>) {
        component.textDesign(
            text = "New Episodes",
            textSize = 25.sp,
            color = Color(134,137,146),
            weight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            itemsIndexed(media) { _, item ->
                Column(Modifier.padding(end = 20.dp)) {
                    Image(
                        painter = rememberImagePainter(item.coverAsset.url),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10))
                            .height(200.dp)
                            .width(150.dp),
                        alpha = 1f,
                        contentScale = ContentScale.FillBounds
                    )
                    component.textDesign(text = item.title)
                    component.textDesign(text = item.type, color = Color(155,158,165),
                    )

                }
            }
        }
    }

}