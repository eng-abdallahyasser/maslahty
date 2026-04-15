package com.abdallahyasser.maslahty.presentaion.screens.Requests.RequestsList

import androidx.lifecycle.ViewModel
import com.abdallahyasser.maslahty.presentaion.screens.home.HomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RequestsListViewModel: ViewModel() {
    private val _screenState = MutableStateFlow(RequestsListState())
    val screenState = _screenState.asStateFlow()
}