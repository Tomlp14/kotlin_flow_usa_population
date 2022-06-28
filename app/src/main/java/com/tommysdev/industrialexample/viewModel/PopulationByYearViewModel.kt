package com.tommysdev.industrialexample.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommysdev.industrialexample.repository.CountryNetworkBuilder
import com.tommysdev.industrialexample.repository.CountryPopulation
import com.tommysdev.industrialexample.repository.CountryResponse
import com.tommysdev.industrialexample.repository.CountryService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PopulationByYearViewModel : ViewModel() {

    private val countriesService = CountryNetworkBuilder.retrofitBuilder()

    //State flow is live data on steroids. They usually do the same thing, but stateflow has some extra functionality. State fow are better to use with Coroutines
    //because; it's part of the flow mechanish and it works very well with coroutines.
    private val _populationByYears = MutableStateFlow(emptyList<CountryPopulation>())
    private val _loadError = MutableStateFlow("")
    private val _loading = MutableStateFlow(false)

    val populationByYears: StateFlow<List<CountryPopulation>>
    get() = _populationByYears
    val loadError: StateFlow<String>
    get() = _loadError
    val loading : StateFlow<Boolean>
    get() = _loading




     fun fetchPopulationByYear(){
        _loading.value = true

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
             val response = mapToResult()
            withContext(Dispatchers.Main){
                response.fold(
                    onSuccess = { resultList ->
                        _populationByYears.emit(resultList.countriesList)
                        _loading.emit(false)
                    },
                    onFailure = {
                        _loadError.emit("Cannot be loaded")
                        _loading.emit(false)
                    }
                )
            }
        }
    }

    private suspend fun mapToResult(): Result<CountryResponse>{
        return kotlin.runCatching {
            val resp = countriesService.getPoulationByYear().body()
            resp ?: CountryResponse(arrayListOf(CountryPopulation("", "", "")))
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler{_, throwable ->
       onError("Exception: ${throwable.localizedMessage}")
    }

    private fun onError(error: String){
        _loadError.value = error
        _loading.value = false
    }

}