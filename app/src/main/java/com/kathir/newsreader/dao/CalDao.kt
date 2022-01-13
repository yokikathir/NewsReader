package com.kathir.newsreader.dao

import androidx.room.Dao
import com.kathir.newsreader.entity.Newsdb
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Created by KATHIR on 12-01-2022.
 */
@Dao
interface CalDao {

    @Insert
    fun insert(note: Newsdb?)

    @get:Query("SELECT * FROM Newsdb")
    val allNotes: LiveData<MutableList<Newsdb>>

    @Update
    fun update(pro: Newsdb?)

    @Query("DELETE FROM Newsdb")
    fun delete(): Int

}