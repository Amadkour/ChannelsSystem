package com.example.androidtask.di

import androidx.lifecycle.*
import com.example.androidtask.business.domain.model.media.Media
import com.example.androidtask.business.repository.MediaRepository
import com.example.androidtask.business.domain.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

//---------------------(Channel View Model Class)--------------------//
@ExperimentalCoroutinesApi
@HiltViewModel
class MediaViewModel
@Inject
 constructor(
    private val mediaRepository: MediaRepository,
    private val saveStateHandle: SavedStateHandle
) : ViewModel() {
    //---------------------(private Channel Flow data state)--------------------//
    private val _dataState: MutableStateFlow<DataState<List<Media>>> = MutableStateFlow(DataState.Loading)

    //---------------------(public Channel Flow data state)--------------------//
    val dataState: MutableStateFlow<DataState<List<Media>>> get() = _dataState

    //---------------------(Set state Event)--------------------//
    fun setStateEvent(stateEvent: MediaStateEvent) {
        viewModelScope.launch {
            when (stateEvent) {
                //---------------------(get Channel state Event)--------------------//
                is MediaStateEvent.GetMediaEvent -> {
                    mediaRepository.getMediaFromApiAndSaveInDB().onEach {
                            dataState ->
                        _dataState.value = dataState
                    }.launchIn(viewModelScope)
                }

            }
        }
    }

}

sealed class MediaStateEvent {
    object GetMediaEvent : MediaStateEvent()
}