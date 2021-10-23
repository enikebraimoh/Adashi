package ng.adashi.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.network.NetworkDataSource
import ng.adashi.utils.DataState

class LoginRepository ( private val networkDataSource: NetworkDataSource,) {

    suspend fun LogUserIn (loginDetails: LoginDetails) : Flow<DataState<LoginResponse>> =
        networkDataSource.login(loginDetails).map { state -> state }


}