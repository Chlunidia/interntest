package com.example.interntest.ui.listuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.example.interntest.local.UsersRemoteMediator
import com.example.interntest.local.entity.Users
import com.example.interntest.local.room.UsersDatabase
import com.example.interntest.retrofit.ApiService

class ListUserViewModel(
    private val apiService: ApiService,
    private val usersDatabase: UsersDatabase
) : ViewModel() {

    fun getUsers(): LiveData<PagingData<Users>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            remoteMediator = UsersRemoteMediator(usersDatabase, apiService),
            pagingSourceFactory = { usersDatabase.userDao().getAllUsers() }
        ).liveData
    }
}