package com.example.giphyapp.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeletedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeletedGif(item: DeletedEntity)

    @Query("SELECT * FROM deleted_entity")
    suspend fun getDeletedList(): List<DeletedEntity>

}