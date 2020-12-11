package com.example.turecallerdialog.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turecallerdialog.model.ContactModel


@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<ContactModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: ContactModel)

    @Query("SELECT * FROM contact ORDER BY dummyName DESC")
    fun getNickNamedContact(): PagingSource<Int, ContactModel>





    @Query("SELECT * FROM contact WHERE phoneNumber LIKE '%' || :number || '%'")
    suspend fun getNickNamed(number : String): List<ContactModel>

    // @Query("SELECT * FROM Posts WHERE " +"name LIKE :queryString OR description LIKE :queryString " + "ORDER BY stars DESC, name ASC")
    /*@Query("SELECT * FROM Contact WHERE " +"type LIKE :queryString")
    fun reposByName(queryString: String): PagingSource<Int, ContactModel>*/

    @Query("DELETE FROM Contact")
    suspend fun clearRepos()
}