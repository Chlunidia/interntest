package com.example.interntest.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.interntest.local.entity.Users
import com.example.interntest.ui.listuser.ListUserActivity
import com.example.interntest.ui.palindrome.PalindromeActivity
import com.example.interntest.databinding.ActivityMainBinding

import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        handleWindowInsets(binding.root)
        if (savedInstanceState != null) {
            binding.tvName.text = savedInstanceState.getString(EXTRA_NAME)
        }
        setupToolbar()
        getName()
        setupAction()
    }

    private fun getName() {
        val name = intent.getStringExtra(EXTRA_NAME)
        binding.tvName.text = name

        val selectedUser = intent.getParcelableExtra<Users>(EXTRA_DATA)
        if (selectedUser != null) {
            binding.tvSelectedUser.text = selectedUser.firstname
        }
    }

    private fun setupAction() {
        binding.btnChooseUser.setOnClickListener {
            val intent = Intent(this, ListUserActivity::class.java)
            intent.putExtra(EXTRA_NAME, binding.tvName.text.toString())
            startActivityForResult(intent, REQUEST_CODE_SELECT_USER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_USER && resultCode == RESULT_OK) {
            val name = data?.getStringExtra(EXTRA_NAME)
            val selectedUser = data?.getParcelableExtra<Users>(EXTRA_DATA)
            binding.tvName.text = name
            binding.tvSelectedUser.text = selectedUser?.firstname
        }
    }

    private fun setupToolbar() {
        toolbar = binding.appBar
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, PalindromeActivity::class.java)
            intent.putExtra(EXTRA_NAME, binding.tvName.text.toString())
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

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DATA = "extra_data"
        const val REQUEST_CODE_SELECT_USER = 1
    }
}
