package ng.adashi.repository

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.network.NetworkDataSource
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.network.SessionManager
import ng.adashi.network.retrofit.RetrofitInstance
import ng.adashi.utils.DataState
import ng.adashi.utils.convertErrorBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepository
@Inject
constructor(private val networkDataSource: NetworkDataSource) {

     fun LogUserNewIn(loginDetails: LoginDetails): Flow<DataState<LoginToken>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.login(loginDetails)
            emit(DataState.Success(response.data))
        } catch (e: Throwable) {
            when (e) {
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