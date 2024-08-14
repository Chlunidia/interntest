package com.example.interntest.ui.palindrome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.interntest.databinding.ActivityPalindromeBinding
import com.example.interntest.ui.main.MainActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding

class PalindromeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPalindromeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPalindromeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        handleWindowInsets(binding.root)
        supportActionBar?.hide()
        setupAction()
    }

    private fun checkPalindrome() {
        val check = binding.edtPalindrome.text.toString()
        when {
            check.isEmpty() -> {
                binding.edtPalindrome.error = "Palindrome tidak boleh kosong"
            }

            else -> {
                val sentences = check.split(", ").map { it.trim() }
                val results = sentences.map { if (isPalindrome(it)) "$it is Palindrome" else "$it is not Palindrome" }
                showResultDialog(results.joinToString("\n"))
            }
        }
    }

    private fun showResultDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Palindrome Check Result")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun nextScreen() {
        val name = binding.edtName.text.toString().trim()
        when {
            name.isEmpty() -> {
                binding.edtName.error = "Nama tidak boleh kosong"
            }

            else -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_NAME, name)
                startActivity(intent)
            }
        }
    }

    private fun setupAction() {
        binding.btnCheck.setOnClickListener {
            checkPalindrome()
        }

        binding.btnNext.setOnClickListener {
            nextScreen()
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val cleaned = text.replace("\\s+".toRegex(), "").toLowerCase()
        return cleaned == cleaned.reversed()
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
