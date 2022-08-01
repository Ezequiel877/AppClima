package com.example.appclima

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.appclima.databinding.ActivityMainBinding
import com.example.appclima.presentatation.show
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val firebase = FirebaseAuth.getInstance()


        val nasHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_Container) as NavHostFragment
        val navControler = nasHostFragment.navController
        if (firebase.currentUser != null) {
            navControler.navigate(R.id.home2)
        }


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

    private fun loginIt() {
        val firebase = FirebaseAuth.getInstance()
        if (firebase.currentUser != null) {
            Navigation.findNavController(this, R.id.fragment_Container)
                .navigate(R.id.home2)
        }
    }
}