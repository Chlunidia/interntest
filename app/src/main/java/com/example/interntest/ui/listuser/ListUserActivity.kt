package com.example.interntest.ui.listuser

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.interntest.adapter.ListUserAdapter
import com.example.interntest.adapter.LoadingStateAdapter
import com.example.interntest.local.room.UsersDatabase
import com.example.interntest.retrofit.ApiConfig
import com.example.interntest.databinding.ActivityListUserBinding
import com.example.interntest.ui.main.MainActivity

import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import com.example.interntest.local.entity.Users

class ListUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListUserBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var usersViewModel: ListUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        handleWindowInsets(binding.root)

        setupToolbar()
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        val apiService = ApiConfig.getApiService()
        val database = UsersDatabase.getDatabase(this)
        val factory = ListUserViewModelFactory(apiService, database)
        usersViewModel = ViewModelProvider(this, factory)[ListUserViewModel::class.java]
    }

    private fun setupRecyclerView() {
        val adapter = ListUserAdapter { selectedUser -> onUserSelected(selectedUser) }
        binding.rvListUser.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter { adapter.retry() }
        )

        usersViewModel.getUsers().observe(this) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }

        binding.rvListUser.layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(this, 2)
            } else {
                LinearLayoutManager(this)
            }
    }

    private fun onUserSelected(selectedUser: Users) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_NAME, intent.getStringExtra(MainActivity.EXTRA_NAME))
        intent.putExtra(MainActivity.EXTRA_DATA, selectedUser)
        startActivity(intent)
        finish()
    }

    private fun setupToolbar() {
        toolbar = binding.appBar
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        toolbar.setNavigationOnClickListener {
            val name = intent.getStringExtra(MainActivity.EXTRA_NAME)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_NAME, name)
            startActivity(intent)
        }
    }

    private fun handleWindowInsets(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                left = systemBarsInsets.left,
                top = systemBarsInsets.top,
                right = systemBarsInsets.right,
                bottom = systemBarsInsets.bottom
            )
            insets
        }
    }
}
