package com.example.interntest.ui.listuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interntest.local.room.UsersDatabase
import com.example.interntest.retrofit.ApiService

class ListUserViewModelFactory constructor(
    private val apiService: ApiService,
    private val usersDatabase: UsersDatabase
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ListUserViewModel::class.java) -> {
                ListUserViewModel(apiService, usersDatabase) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }
}