package ng.adashi.ui.home

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.repository.HomeRepository
import ng.adashi.ui.home.models.AgentWalletResponse
import ng.adashi.utils.DataState

class HomeViewModel(app : Application,private val repo : HomeRepository) : ViewModel() {

    init {
       // getWalletDetails("6174be37175974f7ca2f3336")
    }

    private val _wallet_ballance = MutableLiveData<DataState<AgentWalletResponse>>()
    val wallet_ballance: LiveData<DataState<AgentWalletResponse>> get() = _wallet_ballance

    //call the function from the repository
    fun getWalletDetails(id : String) {
        viewModelScope.launch {
            repo.getWalletAgentDetails(id).onEach { state ->
                _wallet_ballance.value = state
            }.launchIn(viewModelScope)
        }
    }

}