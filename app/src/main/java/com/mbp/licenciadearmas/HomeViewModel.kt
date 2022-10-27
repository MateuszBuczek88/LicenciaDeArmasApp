package com.mbp.licenciadearmas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _showCredits = MutableLiveData(false)
    val showCredits: LiveData<Boolean>
        get() = _showCredits

    fun showCredits() {
        _showCredits.value = true
    }

    fun hideCredits() {
        _showCredits.value = false
    }
}