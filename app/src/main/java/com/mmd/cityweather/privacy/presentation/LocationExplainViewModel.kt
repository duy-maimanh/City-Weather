package com.mmd.cityweather.privacy.presentation

import androidx.lifecycle.ViewModel
import com.mmd.cityweather.privacy.domain.UserApproveLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationExplainViewModel @Inject constructor(private val userApproveLocation: UserApproveLocation) :
    ViewModel() {

    // save user decision
    fun approve() {
        userApproveLocation.set(true)
    }
}
