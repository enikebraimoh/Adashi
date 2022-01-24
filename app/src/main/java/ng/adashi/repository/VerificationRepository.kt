package ng.adashi.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.adashi.network.NetworkDataSource
import ng.adashi.ui.verification.basic.models.BasicInfo
import ng.adashi.ui.verification.basic.models.BasicInfoResponse
import ng.adashi.utils.DataState
import ng.adashi.utils.convertErrorBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class VerificationRepository
@Inject
constructor(val networkDataSource: NetworkDataSource) {

    fun updateBasicInfo(info : BasicInfo): Flow<DataState<BasicInfoResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.UpdateBasicInfo(info)
            emit(DataState.Success(response))
        } catch (e: Exception) {
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