package ng.adashi.ui.payout

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
import ng.adashi.ui.payout.models.PayoutResponse
import ng.adashi.ui.savers.addsaver.models.SingleSaver
import ng.adashi.ui.savers.models.Data
import ng.adashi.utils.DataState
import javax.inject.Inject

@HiltViewModel
class PayoutViewModel
@Inject
constructor(val repo : HomeRepository) : ViewModel() {

    var ammount: String? = null
    var saver_id: String? = null

    private val _amountError = MutableLiveData<String?>()
    val amountError: LiveData<String?> get() = _amountError

    private val _saverIdError = MutableLiveData<String?>()
    val saverIdError: LiveData<String?> get() = _saverIdError


    private val _response = MutableLiveData<DataState<PayoutResponse>>()
    val response: LiveData<DataState<PayoutResponse>> get() = _response


    fun pay(save : SaveDetails){
        repo.payout(save).onEach { data ->
            _response.value = data
        }.launchIn(viewModelScope)
    }

     fun validateAmountFIeld(): Boolean {
        return if (ammount == "" || ammount.isNullOrEmpty()) {
            _amountError.value = "this field cannot be left blank"
            false
        }else if (ammount!!.toInt() > 100000) {
            _amountError.value = "Chief this amount is too much"
            false
        }  else {
            _amountError.value = null
            true
        }
    }

     fun validateSaverIdField(): Boolean {
        return if (saver_id == "" || saver_id.isNullOrEmpty()) {
            _saverIdError.value = "this field cannot be left blank"
            false
        } else if (saver_id!!.length < 8) {
            _saverIdError.value = "Account ID is too short"
            false
        }  else {
            _saverIdError.value = null
            true
        }
    }




}