package ng.adashi.ui.savers.addsaver

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
class AddSaverViewModel
@Inject
constructor(val repo : HomeRepository) : ViewModel() {

    private val _addSaverResponse = MutableLiveData<DataState<ng.adashi.ui.savers.addsaver.models.SaverResponse>>()
    val addSaverResponse: LiveData<DataState<ng.adashi.ui.savers.addsaver.models.SaverResponse>> get() = _addSaverResponse


    fun AddSaver(saver : SingleSaver){
        repo.AddSavers(saver).onEach { data ->
            _addSaverResponse.value = data
        }.launchIn(viewModelScope)
    }

}