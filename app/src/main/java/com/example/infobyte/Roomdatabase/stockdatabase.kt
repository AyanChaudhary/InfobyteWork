package com.example.infobyte.Roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
@Database(entities = [stockitementity::class], version = 1)
abstract class stockdatabase {
    abstract fun dao():Dataaccesobject

    companion object{
        @Volatile
        private var instances:stockdatabase?=null
        private val lock=Any()
        private fun invoke(context: Context)= instances?:
        synchronized(lock){
            instances?:
            createdatabase(context).also{
                instances=it

            }

        }

        private fun createdatabase(context: Context)=
            Room.
            databaseBuilder(context.
            applicationContext,
                stockdatabase::class.java,
                "stockitem").
            build()





    }

}