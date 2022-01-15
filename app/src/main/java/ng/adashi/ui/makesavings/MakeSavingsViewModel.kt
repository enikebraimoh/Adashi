package ng.adashi.ui.makesavings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng.adashi.repository.HomeRepository
import ng.adashi.ui.makesavings.models.SaveDetails
import ng.adashi.ui.makesavings.models.SaveResponse
import ng.adashi.ui.savers.addsaver.models.SingleSaver
import ng.adashi.ui.savers.models.Data
import ng.adashi.utils.DataState
import javax.inject.Inject

@HiltViewModel
class MakeSavingsViewModel
@Inject
constructor(val repo : HomeRepository) : ViewModel() {

    private val _response = MutableLiveData<DataState<SaveResponse>>()
    val response: LiveData<DataState<SaveResponse>> get() = _response


    fun save(save : SaveDetails){
        repo.save(save).onEach { data ->
            _response.value = data
        }.launchIn(viewModelScope)
    }

}