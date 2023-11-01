package com.example.infobyte.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.infobyte.data.models.stocksItem

@Database(entities = [stocksItem::class], version = 1)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stocksDAO() : stocksDAO
}