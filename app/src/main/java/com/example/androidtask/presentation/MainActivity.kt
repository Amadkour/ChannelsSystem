package com.example.androidtask.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidtask.business.domain.model.category.Category
import com.example.androidtask.business.domain.model.channel.Channel
import com.example.androidtask.business.domain.model.media.Media
import com.example.androidtask.business.domain.util.DataState
import com.example.androidtask.di.ChannelStateEvent
import com.example.androidtask.di.ChannelViewModel
import com.example.androidtask.di.MediaStateEvent
import com.example.androidtask.di.MediaViewModel
import com.example.androidtask.presentation.sections.ChannelSection
import com.example.androidtask.presentation.view_model.CategoryStateEvent
import com.example.androidtask.presentation.view_model.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        var selectedList: List<Any> = listOf()
        var selectedListTypeIsEpisode = true
        var online = false
    }

    //---------------------(declare view models )-----------//
    private val channelViewModel: ChannelViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val mediaViewModel: MediaViewModel by viewModels()

    //---------(hilt Injection to Component Class)-------//
    @Inject
    lateinit var component: Component
    @Inject
    lateinit var channelSection: ChannelSection

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //-----------------(hide the default action bar)-------------//
            val actionBar: ActionBar? = supportActionBar
            actionBar?.hide()
            //-----------------(initialize observed variables )---------------//
            var channelsList by remember {
                mutableStateOf(listOf<Channel>())
            }
            var mediaList by remember {
                mutableStateOf(listOf<Media>())
            }
            var categoriesList by remember {
                mutableStateOf(listOf<Category>())
            }
            var isLoding by remember {
                mutableStateOf(true)
            }
            var isError by remember {
                mutableStateOf(true)
            }
            //-----------------(snakbar configuration)---------------//
            val scope = rememberCoroutineScope()
            val scaffoldState = rememberScaffoldState()

            //------------------(routing)---------------------//

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "Home") {
                composable("SeeAll") {
                    component.allItemDesign(selectedList, selectedListTypeIsEpisode)
                }
                composable("Home") {
                    channelSection.Home(
                        scaffoldState = scaffoldState,
                        navController = navController,
                        isLoding = isLoding,
                        isError = isError,
                        categoriesList = categoriesList,
                        channelsList = channelsList,
                        mediaList = mediaList
                    )
                }
            }

            //-------------------(get data from repositories serially)---------------//
            lifecycleScope.launch {
                //-------------------(check connectivity)---------------//
                online = isOnline(context = applicationContext)
                async {
                    if (online) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Your Are Online, loading from Network")
                        }
                    } else {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Your Are Offline, loading from Cache")
                        }
                    }
                }
                //-------------------(get data from channel repository)---------------//
                async {
                    channelViewModel.setStateEvent(ChannelStateEvent.GetChannelEvent)
                    channelViewModel.dataState.collect {
                        when (it) {
                            is DataState.Success -> {
                                channelsList = it.data
                                isLoding = false
                                isError = false
                            }
                            is DataState.Loading -> {
                                isLoding = true
                            }
                            is DataState.Error -> isError = true

                            else -> {
                                //-------//
                            }
                        }
                    }
                }

                //-------------------(get data from media repository)---------------//
                async {
                    mediaViewModel.setStateEvent(MediaStateEvent.GetMediaEvent)
                    mediaViewModel.dataState.collect {
                        when (it) {
                            is DataState.Success -> {
                                mediaList = it.data
                                isLoding = false
                                isError = false
                            }
                            is DataState.Loading -> {
                                isLoding = true
                            }
                            is DataState.Error -> isError = true

                            else -> {
                                //-------//
                            }
                        }
                    }
                }

                //-------------------(get data from category repository)---------------//
                async {
                    categoryViewModel.setStateEvent(CategoryStateEvent.GetCategoryEvent)
                    categoryViewModel.dataState.collect {
                        when (it) {
                            is DataState.Success -> {
                                categoriesList = it.data
                                isLoding = false
                                isError = false
                            }
                            is DataState.Loading -> {
                                isLoding = true
                            }
                            is DataState.Error -> isError = true

                            else -> {
                                //-------//
                            }
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
        return false
    }


}

