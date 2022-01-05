package com.loe.fanmark.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

interface CustomersDao {
    @Query("SELECT * FROM Customers")
    fun getCustomers(): LiveData<List<Customers>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCustomers(customers: List<Customers>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewCustomer(customers: Customers)
}

@Database(entities = [Customers::class], version = 1)
abstract class CustomersDB: RoomDatabase() {
    abstract val customersDao: CustomersDao
}

private lateinit var INSTANCE: CustomersDB

fun getDatabase(context: Context): CustomersDB {
    synchronized(CustomersDB::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, CustomersDB::class.java, "customers").build()
        }
    }
    return INSTANCE
}