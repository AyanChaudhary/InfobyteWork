package com.example.infobyte.Roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dataaccesobject {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun stockiteminsert (stockitem:stockitementity)
    @Update
    suspend fun updatestock(stockitem:stockitementity)
    @Delete
    suspend fun deletestock(stockitem:stockitementity)
    @Query("SELECT * FROM stockitem order by id desc")
    fun getallnote(): LiveData<List<stockitementity>>

    //put the required query here for search the search is beign initailize with name or change in price for now
    @Query("SELECT * FROM stockitem where NAME Like :query or ChangeInPRICE  like:query")
    fun searchstock(query:String?): LiveData<List<stockitementity>>

}
