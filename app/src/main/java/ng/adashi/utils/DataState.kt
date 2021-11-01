package ng.adashi.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException

sealed class DataState<out T> {
    data class Success<out R>(val data: R) : DataState<R>()
    data class Error(val error: Throwable) : DataState<Nothing>()
    data class GenericError(
        val code: Int? = null,
        val error: ErrorResponse? = null
    ) : DataState<Nothing>()

    object Loading : DataState<Nothing>()

}

data class ErrorResponse(val status: String, val message: String)

fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.charStream()?.let {
            val type = object : TypeToken<ErrorResponse>() {}.type
            Gson().fromJson(it, type)
        }
    } catch (exception: Exception) {
        null
    }
}