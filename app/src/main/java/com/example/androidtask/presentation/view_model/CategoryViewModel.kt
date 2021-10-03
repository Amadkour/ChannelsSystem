package com.example.androidtask.presentation.view_model

import androidx.lifecycle.*
import com.example.androidtask.business.domain.model.category.Category
import com.example.androidtask.business.repository.CategoryRepository
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
class CategoryViewModel
@Inject
constructor(
    private val categoryRepository: CategoryRepository,
    private val saveStateHandle: SavedStateHandle
) : ViewModel() {
    //---------------------(private Channel Flow data state)--------------------//
    private val _dataState: MutableStateFlow<DataState<List<Category>>> = MutableStateFlow(DataState.Loading)

    //---------------------(public Channel Flow data state)--------------------//
    val dataState: MutableStateFlow<DataState<List<Category>>> get() = _dataState

    //---------------------(Set state Event)--------------------//
    fun setStateEvent(stateEvent: CategoryStateEvent) {
        viewModelScope.launch {
            when (stateEvent) {

                //---------------------(get Channel state Event)--------------------//
                is CategoryStateEvent.GetCategoryEvent -> {
                    categoryRepository.getCategoryFromApiAndSaveInDB().onEach {
                            dataState ->
                        _dataState.value = dataState
                    }.launchIn(viewModelScope)
                }

            }
        }
    }

}

sealed class CategoryStateEvent {
    object GetCategoryEvent : CategoryStateEvent()
}
