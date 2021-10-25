package ng.adashi.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.network.NetworkDataSource
import ng.adashi.ui.home.models.AgentWalletResponse
import ng.adashi.utils.DataState
import ng.adashi.utils.convertErrorBody
import retrofit2.HttpException
import java.io.IOException

class HomeRepository(private val networkDataSource: NetworkDataSource) {

    suspend fun getWalletAgentDetails (wallet_id: String) : Flow<DataState<AgentWalletResponse>> = flow {
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

}