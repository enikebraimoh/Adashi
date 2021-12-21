package ng.adashi.ui.home

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.repository.HomeRepository
import ng.adashi.ui.home.models.AgentWalletResponse
import ng.adashi.utils.DataState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor() : ViewModel() {

    private val _wallet_ballance = MutableLiveData<DataState<AgentWalletResponse>>()
    val wallet_ballance: LiveData<DataState<AgentWalletResponse>> get() = _wallet_ballance

    //call the function from the repository
    fun getWalletDetails(id: String) {
        //repo.getWalletAgentDetails(id).onEach { state ->
         //   _wallet_ballance.value = state
      //  }.launchIn(viewModelScope)
    }

}