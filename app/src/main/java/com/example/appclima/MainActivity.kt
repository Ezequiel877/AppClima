package com.example.appclima

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.appclima.databinding.ActivityMainBinding
import com.example.appclima.presentatation.show

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val nasHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_Container) as NavHostFragment
        val navControler = nasHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navControler)
        navControler.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.notas -> {
                    binding.bottomNavigationView.show()
                    Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show()
                }
                R.id.home2 -> {
                    binding.bottomNavigationView.show()
                }

                else -> {
                    binding.bottomNavigationView.visibility = View.GONE

                }
            }
        }

    }


}