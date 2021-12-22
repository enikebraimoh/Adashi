package ng.adashi.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng.adashi.repository.HomeRepository
import ng.adashi.ui.home.models.wallet.AgentWalletResponse
import ng.adashi.ui.savers.models.Data
import ng.adashi.utils.DataState
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel
@Inject
constructor(val repo : HomeRepository) : ViewModel() {

    private val _transactions = MutableLiveData<DataState<ng.adashi.ui.home.models.transactions.Data>>()
    val transactions: LiveData<DataState<ng.adashi.ui.home.models.transactions.Data>> get() = _transactions

    init {
        getAllTransactions()
    }


    fun getAllTransactions(){
        repo.GetAgentTransactions().onEach { data ->
            _transactions.value = data
        }.launchIn(viewModelScope)
    }


}