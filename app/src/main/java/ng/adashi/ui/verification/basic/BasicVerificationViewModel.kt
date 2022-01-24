package ng.adashi.ui.verification.basic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng.adashi.repository.VerificationRepository
import ng.adashi.ui.verification.basic.models.BasicInfo
import ng.adashi.ui.verification.basic.models.BasicInfoResponse
import ng.adashi.utils.DataState
import javax.inject.Inject

@HiltViewModel
class BasicVerificationViewModel
@Inject
constructor(val repo : VerificationRepository) : ViewModel() {

    private val _updateResponse = MutableLiveData<DataState<BasicInfoResponse>>()
    val updateResponse: LiveData<DataState<BasicInfoResponse>> get() = _updateResponse

    fun updateInfo(info : BasicInfo){
        repo.updateBasicInfo(info).onEach { data ->
            _updateResponse.value = data
        }.launchIn(viewModelScope)
    }

}