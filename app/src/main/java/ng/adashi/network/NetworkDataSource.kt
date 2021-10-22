package ng.adashi.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.adashi.models.login.LoginDetails
import ng.adashi.models.login.LoginResponse
import ng.adashi.utils.DataState
import ng.adashi.utils.convertErrorBody
import retrofit2.HttpException
import java.io.IOException

class NetworkDataSource constructor(private val ApiSource: RetrofitInstance) {

    suspend fun login(details: LoginDetails): Flow<DataState<LoginResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = ApiSource.Api.Login(details)
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

    // suspend fun logMeIn(login : LoginDetails) = RetrofitInstance.Api.Login(login)
}