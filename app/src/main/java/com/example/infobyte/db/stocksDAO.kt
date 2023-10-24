package com.example.infobyte.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.infobyte.data.models.stocks
import com.example.infobyte.data.models.stocksItem
import java.util.concurrent.Flow

@Dao
interface stocksDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(stocksItem: stocksItem)

    @Query("SELECT * FROM stocks_table")
    suspend fun getAllStocks() : MutableList<stocksItem>
}