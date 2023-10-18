package com.example.infobyte.Roomdatabase

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName ="stockitem" )
@Parcelize

class stockitementity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("Changeinprice")
    val ChangeInPRICE: Double,
    @ColumnInfo("Changeinprice")
    val ChangeInPrice: Double,
    @ColumnInfo("LTP")
    val LTP: Double,

    @ColumnInfo("NAME")

    val NAME: String,
    @ColumnInfo("P_D_CLOSE")

    val P_D_CLOSE: Double,
    @ColumnInfo("per_change")
    val Perc_change: Double,

    @ColumnInfo("perc_changeLong")
    val Perc_changeLong: Double,

    @ColumnInfo("SYMBOLE")
    val SYMBOLE: String,

    @ColumnInfo("TODAY_HIGH")
    val TODAY_HIGH: Double,

    @ColumnInfo("TODAY_LOW")
    val TODAY_LOW: Double,

    @ColumnInfo("TODAY_OPEN")
    val TODAY_OPEN: Double,

    @ColumnInfo("TODAT_VOLUME")
    val TODAY_VOLUME: Int
): Parcelable
