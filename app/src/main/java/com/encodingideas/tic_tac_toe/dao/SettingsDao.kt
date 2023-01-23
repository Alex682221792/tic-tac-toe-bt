package com.encodingideas.tic_tac_toe.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.encodingideas.tic_tac_toe.common.AppConstants
import com.encodingideas.tic_tac_toe.model.Settings

@Dao
interface SettingsDao {
    @Query("SELECT * FROM Settings WHERE id = '"+AppConstants.DEFAULT_PLAYER_ID+"' LIMIT 1")
    fun getDefaultSetting(): LiveData<Settings>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(settings: Settings)

}