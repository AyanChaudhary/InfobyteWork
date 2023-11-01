package com.example.infobyte.repositories


import android.os.Build
import android.os.ext.SdkExtensions
import android.util.Log
import com.bumptech.glide.load.HttpException
import com.example.infobyte.Others.Resource
import com.example.infobyte.data.models.stocksItem
import com.example.infobyte.data.remote.StocksApi
import com.example.infobyte.db.stocksDAO
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api : StocksApi,
    private val stocksdao : stocksDAO
) {
    suspend fun getAllStocksFromApi(user_content_key : String,lib : String) = flow{
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
    suspend fun insertStock(stocksItem: stocksItem)=stocksdao.insertStocks(stocksItem)
    suspend fun getAllStocksFromDb()=stocksdao.getAllStocks()
}