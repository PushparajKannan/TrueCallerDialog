package com.example.turecallerdialog.database

import android.content.Context
import androidx.room.*
import com.example.turecallerdialog.database.dao.ContactDao
import com.example.turecallerdialog.model.ContactModel

@Database(
    entities = [ContactModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "conatactApp.db")
                .build()
    }


}
