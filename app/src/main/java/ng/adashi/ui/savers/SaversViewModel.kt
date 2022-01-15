package ng.adashi.ui.savers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng.adashi.repository.HomeRepository
import ng.adashi.ui.savers.addsaver.models.SingleSaver
import ng.adashi.ui.savers.models.Data
import ng.adashi.utils.DataState
import javax.inject.Inject

@HiltViewModel
class SaversViewModel
@Inject
constructor(val repo : HomeRepository) : ViewModel() {

    private val _savers = MutableLiveData<DataState<Data>>()
    val savers: LiveData<DataState<Data>> get() = _savers

    init {
        getAllSavers()
    }

    fun getAllSavers(){
        repo.GetAllSavers().onEach { data ->
            _savers.value = data
        }.launchIn(viewModelScope)
    }

}