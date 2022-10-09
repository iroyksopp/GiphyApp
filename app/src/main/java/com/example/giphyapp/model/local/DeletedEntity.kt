package com.example.giphyapp.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deleted_entity")
data class DeletedEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String
)
