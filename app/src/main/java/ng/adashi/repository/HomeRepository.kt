package ng.adashi.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.adashi.domain_models.agent.AgentWalletDetails
import ng.adashi.network.NetworkDataSource
import ng.adashi.ui.home.models.transactions.AgentTransactionsResponse
import ng.adashi.ui.home.models.transactions.Data
import ng.adashi.ui.home.models.wallet.AgentWalletResponse
import ng.adashi.ui.makesavings.models.SaveDetails
import ng.adashi.ui.makesavings.models.SaveResponse
import ng.adashi.ui.savers.addsaver.models.SingleSaver
import ng.adashi.ui.savers.models.SaversResponse
import ng.adashi.utils.DataState
import ng.adashi.utils.convertErrorBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

class HomeRepository
@Inject
constructor(private val networkDataSource: NetworkDataSource) {

     fun getWalletAgentDetails () : Flow<DataState<AgentWalletDetails>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.GetAgentWallet()
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

    fun AddSavers( saver : SingleSaver) : Flow<DataState<ng.adashi.ui.savers.addsaver.models.SaverResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.AddSaver(saver)
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

    fun save( saver : SaveDetails) : Flow<DataState<SaveResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.save(saver)
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