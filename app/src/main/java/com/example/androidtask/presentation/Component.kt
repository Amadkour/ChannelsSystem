package com.example.androidtask.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.androidtask.business.domain.model.category.Category
import com.example.androidtask.business.domain.model.channel.Channel
import com.example.androidtask.business.domain.model.channel.LatestMedia
import com.example.androidtask.business.domain.model.channel.Series
import com.example.androidtask.business.domain.model.media.Media
import com.example.androidtask.presentation.sections.ChannelSection
import com.example.androidtask.presentation.theme.AppTheme
import javax.inject.Inject

class Component @Inject constructor(
) {

    @Composable
    fun textDesign(
        modifier: Modifier = Modifier
            .width(150.dp)
            .padding(top = 0.dp),
        text: String,
        color: Color = Color.White,
        textSize: TextUnit = 20.sp,
        weight: FontWeight = FontWeight.Normal,
        align: TextAlign = TextAlign.Start
    ) =
        Text(
            text = text,
            maxLines = 2,
            style = TextStyle(
                fontSize = textSize,
                color = color,
            ),
            modifier = modifier,
            textAlign = align,
            fontWeight = weight
        )

//-----------------------------(see all items)-----------------//
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun allItemDesign(items: List<Any>, isEpisode: Boolean = true) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(all = 20.dp)
    ) {

        //------------------title-----------------//
        textDesign(
            text = if (isEpisode) "Episodes" else "Series",
            textSize = 25.sp,
            color = Color(134, 137, 146),
            weight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        //------------------title-----------------//

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            Modifier
                .fillMaxSize()
                .padding(all = 20.dp)
        ) {
            if (isEpisode) {
                val itemsList = items as List<LatestMedia>

                items(itemsList) { episode ->
                    Column(Modifier.padding(start = 20.dp,bottom = 30.dp)) {
                        //----------(episode Image)---------------//
                        Image(
                            painter = rememberImagePainter(episode.coverAsset.url),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10))
                                .height(200.dp),
//                                .width(150.dp),
                            alpha = 1f,
                            contentScale = ContentScale.FillBounds
                        )
                        //----------(episode Image)---------------//
                        textDesign(text = episode.title)
                    }

                }
            } else {
                val itemsList = items as List<Series>

                items(itemsList) { series ->
                    Column(Modifier.padding(start = 20.dp)) {

                        Image(
                            painter = rememberImagePainter(series.coverAsset.url),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10))
                                .height(200.dp),
                            alpha = 1f,
                            contentScale = ContentScale.FillBounds
                        )
                        textDesign(text = series.title)
                    }
                }
            }
        }
    }
}

}