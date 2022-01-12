package ng.adashi.ui.home

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng.adashi.repository.AuthRepository
import ng.adashi.repository.HomeRepository
import ng.adashi.ui.home.models.transactions.AgentTransactionsResponse
import ng.adashi.ui.home.models.transactions.Data
import ng.adashi.ui.home.models.wallet.AgentWalletResponse
import ng.adashi.utils.DataState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(val repo: HomeRepository) : ViewModel() {

    private val _transactions = MutableLiveData<DataState<Data>>()
    val transactions: LiveData<DataState<Data>> get() = _transactions

    private val _wallet_ballance = MutableLiveData<DataState<AgentWalletResponse>>()
    val wallet_ballance: LiveData<DataState<AgentWalletResponse>> get() = _wallet_ballance

    init {
        getAllTransactions()
    }


    fun getAllTransactions(){
       repo.GetAgentTransactions().onEach { data ->
           _transactions.value = data
       }.launchIn(viewModelScope)
    }

    fun getWalletDetails(id : String){
        repo.getWalletAgentDetails(id).onEach { data ->
            _wallet_ballance.value = data
        }.launchIn(viewModelScope)
    }


}