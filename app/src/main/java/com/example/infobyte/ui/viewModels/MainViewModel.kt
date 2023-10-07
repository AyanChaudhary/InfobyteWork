package com.example.infobyte.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infobyte.Others.Resource
import com.example.infobyte.data.models.stocks
import com.example.infobyte.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel(){

    val uiStateForStocks: MutableLiveData<Resource<stocks>> = MutableLiveData()

    fun getAllStocks(user_content_key:String, lib : String){
        viewModelScope.launch {
            val result = repository.getAllStocks(user_content_key,lib)
            result.collectLatest { res->
                when(res){
                    is Resource.Success->{
                        uiStateForStocks.postValue(Resource.Success(res.data!!))
                    }
                    is Resource.Error -> {
                        uiStateForStocks.postValue(Resource.Error(res.message!!))
                    }
                    is Resource.Loading -> {
                        uiStateForStocks.postValue(Resource.Loading())
                    }
                    else ->{}
                }
            }
        }
    }
}