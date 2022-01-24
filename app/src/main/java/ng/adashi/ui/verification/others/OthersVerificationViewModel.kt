package ng.adashi.ui.verification.others

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
import ng.adashi.ui.verification.others.models.OthersInfo
import ng.adashi.ui.verification.others.models.OthersInfoResponse
import ng.adashi.utils.DataState
import javax.inject.Inject

@HiltViewModel
class OthersVerificationViewModel
@Inject
constructor(val repo : VerificationRepository) : ViewModel() {

    private val _updateResponse = MutableLiveData<DataState<OthersInfoResponse>>()
    val updateResponse: LiveData<DataState<OthersInfoResponse>> get() = _updateResponse

    fun updateInfo(info : OthersInfo){
        repo.updateOthersInfo(info).onEach { data ->
            _updateResponse.value = data
        }.launchIn(viewModelScope)
    }

}