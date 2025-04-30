package com.diphrogram.kolsa_test.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.diphrogram.kolsa_test.R
import com.diphrogram.kolsa_test.databinding.ActivityMainBinding
import com.diphrogram.kolsa_test.presentation.workouts.WorkoutsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        openWorkoutsFragment()
    }

    private fun openWorkoutsFragment() {
        val fragment = WorkoutsFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(binding.container.id, fragment)
            commit()
        }
    }
}