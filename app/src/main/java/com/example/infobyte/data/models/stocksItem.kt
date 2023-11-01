package com.example.infobyte.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stocks_table")
data class stocksItem(
    var ChangeInPRICE: Double = 0.0,

    val LTP: Double=0.0,
    @PrimaryKey
    val NAME: String="",
    val P_D_CLOSE: Double=0.0,
    val Perc_change: Double=0.0,
    val Perc_changeLong: Double=0.0,
    val SYMBOLE: String="",
    val TODAY_HIGH: Double=0.0,
    val TODAY_LOW: Double=0.0,
    val TODAY_OPEN: Double=0.0,
    val TODAY_VOLUME: Int=0
)
