package com.encodingideas.tic_tac_toe.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Settings (
        @PrimaryKey val id: String,
        @ColumnInfo val name: String
)