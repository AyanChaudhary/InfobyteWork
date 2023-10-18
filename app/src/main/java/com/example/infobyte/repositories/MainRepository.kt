package com.example.infobyte.repositories


import android.os.Build
import android.os.ext.SdkExtensions
import android.util.Log
import com.bumptech.glide.load.HttpException
import com.example.infobyte.Others.Resource
import com.example.infobyte.data.remote.StocksApi
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api : StocksApi
) {
    suspend fun getAllStocks(user_content_key : String,lib : String) = flow{
        emit(Resource.Loading())
        val response = try {
                    api.getAllStocks(user_content_key,lib)
                }catch (e: IOException){
                    emit(Resource.Error(e.message?:""))
                    Log.d("Tag",e.message.toString())
                    return@flow
                }catch (e: HttpException) {
            emit(Resource.Error("server not reachable"))
            return@flow
        }
        emit(Resource.Success(response))
    }
}