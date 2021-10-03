package com.example.androidtask.presentation.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.androidtask.business.domain.model.category.Category
import com.example.androidtask.business.domain.model.channel.Channel
import com.example.androidtask.business.domain.model.channel.LatestMedia
import com.example.androidtask.business.domain.model.channel.Series
import com.example.androidtask.business.domain.model.media.Media
import com.example.androidtask.presentation.Component
import com.example.androidtask.presentation.MainActivity
import com.example.androidtask.presentation.theme.AppTheme
import javax.inject.Inject

class ChannelSection
@Inject
constructor(
    private val component: Component,
    private val newEpisodesSection: NewEpisodesSection,
    private val categorySection: CategorySection
) {


    //----------(Channel design)---------------//

    @Composable
    fun channelDesign(
        media: List<Media>,
        channels: List<Channel>,
        categories: List<Category>,
        onClickOnSeeAll: () -> Unit
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            //----------(draw horizontal Line )---------------//
            item {
                newEpisodesSection.newEpisodesDesign(media = media)
                Divider(
                    color = Color.DarkGray,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(10.dp, 40.dp, 10.dp, 10.dp)
                )
            }

            itemsIndexed(channels) { _, channel ->
                titleDesign(item = channel)
                //----------(draw series design if this channel have series else draw episodes design)-----------//
                if (channel.series.isEmpty())
                    episodesDesign(channel = channel, onClickOnSeeAll = onClickOnSeeAll)
                else
                    seriesDesign(channel = channel, onClickOnSeeAll = onClickOnSeeAll)

                //----------(draw horizontal Line )---------------//
                Divider(
                    color = Color.DarkGray,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(10.dp, 40.dp, 10.dp, 10.dp)
                )
            }
            //----------(draw Category section )---------------//
            item {
                //----------(Category title)---------------//
                component.textDesign(
                    text = "Browse by categories",
                    textSize = 30.sp,
                    weight = FontWeight.Bold,
                    color = Color(151,156,159),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    //----------(Categories)---------------//
                    for (i in 0..categories.size-1 step 2) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()

                        ) {
                            categorySection.categoryItemDesign(
                                categories[i].name,
                                modifier = Modifier.weight(0.5f)
                            )
                            //----------(draw category if exits)---------------//
                            if (i + 1 < categories.size) {
                                categorySection.categoryItemDesign(
                                    categories[i + 1].name,
                                    modifier = Modifier.weight(0.5f)
                                )
                            }
                            else {
                                Box(
                                    modifier = Modifier.weight(0.5f)
                                )
                            }
                        }
                    }
                }
            }

        }
    }

    //----------(Episodes design)---------------//
    @Composable
    fun episodesDesign(channel: Channel, onClickOnSeeAll: () -> Unit) {
        Column() {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                items(
                    //------------(show first six items of Latest Media List)------------//
                    items = presentedChannelItems(channel.latestMedia) as List<LatestMedia>
                ) { episode ->
                    Column(Modifier.padding(start = 20.dp)) {
                        //----------(episode Image)---------------//
                        Image(
                            painter = rememberImagePainter(episode.coverAsset.url),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10))
                                .height(200.dp)
                                .width(150.dp),
                            alpha = 1f,
                            contentScale = ContentScale.FillBounds
                        )
                        //----------(episode Image)---------------//
                        component.textDesign(text = episode.title)
                    }
                }
                //------(Show see more Button if this episode have more items)---------//
                item {

                    if (channel.latestMedia.size > 6) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .clip(RoundedCornerShape(10))
                                .height(200.dp)
                                .width(150.dp)
                                .background(color = Color(58, 61, 70))

                                .clickable {
                                    MainActivity.selectedList = channel.latestMedia
                                    MainActivity.selectedListTypeIsEpisode = true
                                    onClickOnSeeAll()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            component.textDesign(
                                text = "See all",
                                textSize = 20.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(color = Color.White)
                                    .padding(all = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    //----------(series design)---------------//
    @Composable
    fun seriesDesign(channel: Channel, onClickOnSeeAll: () -> Unit) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            itemsIndexed(presentedChannelItems(channel.series) as List<Series>) { _, series ->
                Column(Modifier.padding(start = 10.dp)) {

                    //----------(series Image)---------------//
                    Image(
                        painter = rememberImagePainter(series.coverAsset.url),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10))
                            .height(200.dp)
                            .width(300.dp),
                        alpha = 1f,
                        contentScale = ContentScale.FillBounds
                    )
                    //----------(series title)--------------//
                    component.textDesign(text = series.title, modifier = Modifier.width(280.dp))
                }
            }
            //------(Show see more Button if this series have more items)---------//
            item {
                if (channel.series.size > 6) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(10))
                            .height(200.dp)
                            .width(300.dp)
                            .background(color = Color.Black)

                            .clickable {
                                MainActivity.selectedList = channel.series
                                MainActivity.selectedListTypeIsEpisode = false

                                onClickOnSeeAll()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        component.textDesign(
                            text = "See all",
                            textSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(color = Color.White)
                                .padding(all = 10.dp)
                        )
                    }
                }
            }
        }
    }

    //----------(channel title design)---------//
    @Composable
    fun titleDesign(item: Channel) {
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberImagePainter(item.coverAsset.url),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .size(50.dp),
                alpha = 1f,
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
            ) {
                component.textDesign(text = item.title, modifier = Modifier.fillMaxWidth())
                component.textDesign(
                    text = item.mediaCount.toString() +
                            if (item.series.isEmpty())
                                " episodes"
                            else
                                " series",
                    color = Color(155, 158, 165),
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }
    }

    //------(return first six items of List if it contain more than six)---------//
    private fun presentedChannelItems(items: List<Any>): List<Any> {
        return if (items.size > 6) items.subList(0, 6)
        else items
    }

    //------------------(home screen)--------------//
    @Composable
    fun Home(
        scaffoldState: ScaffoldState,
        navController: NavHostController,
        isLoding: Boolean,
        isError: Boolean,
        categoriesList: List<Category>,
        channelsList: List<Channel>,
        mediaList: List<Media>
    ) {
        AppTheme(darkTheme = true) {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    component.textDesign(
                        text = "Channels",
                        textSize = 35.sp,
                        color = Color(191, 192, 196),
                        weight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(all = 20.dp)
                    )
                }
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 10.dp),

                    ) {

                    if (isLoding) {
                        Box(
                            modifier = Modifier.fillMaxSize(0.5f),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else if (isError) {
                        Box(
                            modifier = Modifier.fillMaxSize(0.5f),
                            contentAlignment = Alignment.Center
                        ) {
                            component.textDesign(text = "Error")
                        }
                    } else {

                        channelDesign(
                            media = mediaList,
                            channels = channelsList,
                            categories = categoriesList,
                            onClickOnSeeAll = {
                                navController.navigate("SeeAll") {
                                    popUpTo("Home")
                                }
                            })
                    }

                }
            }
        }
    }
}