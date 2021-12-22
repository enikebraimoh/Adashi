package ng.adashi.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.adashi.network.NetworkDataSource
import ng.adashi.ui.home.models.transactions.AgentTransactionsResponse
import ng.adashi.ui.home.models.transactions.Data
import ng.adashi.ui.home.models.wallet.AgentWalletResponse
import ng.adashi.ui.savers.models.SaversResponse
import ng.adashi.utils.DataState
import ng.adashi.utils.convertErrorBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HomeRepository
@Inject
constructor(private val networkDataSource: NetworkDataSource) {

     fun getWalletAgentDetails (wallet_id: String) : Flow<DataState<AgentWalletResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.GetWallet(wallet_id)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            when (e){
                is IOException -> emit(DataState.Error(e))
                is HttpException -> {
                    val status = e.code()
                    val res = convertErrorBody(e)
                    emit(DataState.GenericError(status, res))
                }
            }
        }
    }

     fun GetAgentTransactions () : Flow<DataState<Data>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.GetAgentTransactions().data
            emit(DataState.Success(response))
        } catch (e: Exception) {
            when (e){
                is IOException -> emit(DataState.Error(e))
                is HttpException -> {
                    val status = e.code()
                    val res = convertErrorBody(e)
                    emit(DataState.GenericError(status, res))
                }
            }
        }
    }

    fun GetAllSavers() : Flow<DataState<ng.adashi.ui.savers.models.Data>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.GetAllSavers().data
            emit(DataState.Success(response))
        } catch (e: Exception) {
            when (e){
                is IOException -> emit(DataState.Error(e))
                is HttpException -> {
                    val status = e.code()
                    val res = convertErrorBody(e)
                    emit(DataState.GenericError(status, res))
                }
            }
        }
    }

}