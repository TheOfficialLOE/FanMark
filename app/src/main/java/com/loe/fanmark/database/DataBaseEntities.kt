package com.loe.fanmark.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Customers constructor(
    val CustomerFullName: String,
    val CustomerMobileNumber: Int,
    val CustomerHomeNumber: Int,
    val CustomerLocation: CustomerLocationSCH,
    val CustomerAddress: String,
    val CustomerPhoto: String,
    val AddedDate: String
)

@Entity
data class CustomerLocationSCH constructor(
    val type: String,
    val coordinates: List<Int>
)