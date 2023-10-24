package com.example.infobyte.di
import android.content.Context
import androidx.room.Room
import com.example.infobyte.Others.Constants
import com.example.infobyte.data.remote.StocksApi
import com.example.infobyte.db.StockDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):StocksApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StocksApi::class.java)
    }

    @Singleton
    @Provides
    fun provideStocksDatabase(
        @ApplicationContext app: Context
    )= Room.databaseBuilder(
        app,StockDatabase::class.java,
        "stocks_table"
    ).build()

    @Singleton
    @Provides
    fun provideStocksDao(db:StockDatabase)=db.stocksDAO()

}