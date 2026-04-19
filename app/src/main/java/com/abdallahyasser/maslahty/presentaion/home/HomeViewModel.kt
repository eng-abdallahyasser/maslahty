package com.abdallahyasser.maslahty.presentaion.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel( ) {
    private val _screenState = MutableStateFlow(_root_ide_package_.com.abdallahyasser.maslahty.presentaion.home.HomeScreenState())
    val screenState = _screenState.asStateFlow()

    init {

    }
}