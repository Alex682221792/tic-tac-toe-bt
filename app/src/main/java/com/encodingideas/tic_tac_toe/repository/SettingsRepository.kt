package com.encodingideas.tic_tac_toe.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.encodingideas.tic_tac_toe.common.AppConstants
import com.encodingideas.tic_tac_toe.common.AppDatabase
import com.encodingideas.tic_tac_toe.model.Settings

class SettingsRepository(private val context: Context) {
    val settings: LiveData<Settings>
        get() {
            return AppDatabase.getInstance(context = context).settingsDao().getDefaultSetting()
        }

    suspend fun saveNickName(name: String){
        this.saveSettings(
            Settings(AppConstants.DEFAULT_PLAYER_ID, name)
        )
    }

    suspend fun saveSettings(settings: Settings) {
        AppDatabase.getInstance(context = context).settingsDao().insertSetting(settings)
    }
}