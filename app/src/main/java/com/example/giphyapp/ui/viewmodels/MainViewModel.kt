package com.example.giphyapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.giphyapp.domain.GifUiEntity
import com.example.giphyapp.model.repository.Repository
import com.example.giphyapp.model.utils.toGifRoomEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repository,
) : ViewModel() {

    val gifsFlow: Flow<PagingData<GifUiEntity>>

    private val searchBy = MutableLiveData("Hello")

    init {
        gifsFlow = searchBy.asFlow()
            .flatMapLatest {
                repo.getTrending(it)
            }
            .cachedIn(viewModelScope)
    }

    fun setSearchBy(value: String?) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
    }

    fun deleteItem(item: GifUiEntity) {
        viewModelScope.launch {
            repo.deleteFromCache(item.toGifRoomEntity())
        }
    }

}