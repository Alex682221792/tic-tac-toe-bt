package com.encodingideas.tic_tac_toe.viewmodel

import androidx.lifecycle.ViewModel
import com.encodingideas.tic_tac_toe.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Optional

class RegisterViewModel(private val settingsRepository: SettingsRepository): ViewModel() {
    fun registerNickName(name: String, onSuccess: ()->Unit, onError: ()->Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = settingsRepository.saveNickName(name)
            if (Optional.ofNullable(result).isPresent){
                onSuccess.invoke()
            } else {
                onError.invoke()
            }
        }
    }
}