package com.example.appclima.UI.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appclima.R
import com.example.appclima.utils.adapters.NotasCardAdapter
import com.example.appclima.databinding.FragmentNotasBinding
import com.example.appclima.model.NotasEntity
import com.example.appclima.model.local.AppDataBase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Notas : Fragment(R.layout.fragment_notas), NotasCardAdapter.OnModelClick {

    private lateinit var binding: FragmentNotasBinding
    var selectProducto = listOf<NotasEntity>()
    private lateinit var adapter: NotasCardAdapter
    private lateinit var viewmodel: AppDataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotasBinding.bind(view)
        viewmodel=AppDataBase.getDataBase(requireContext())
        GlobalScope.launch(Dispatchers.IO) {
            selectProducto=viewmodel.climadao().getNotas()
            binding.let {
                adapter = NotasCardAdapter(requireContext(), selectProducto)
                binding.recyclerView.adapter = adapter
            }
        }
        Log.d("TAGdatosint", "onViewCreated: $selectProducto")


        binding.flotingNextBoton.setOnClickListener {
            AddNotas().show(
                requireActivity().supportFragmentManager,
                AddNotas::class.java.simpleName
            )
            Toast.makeText(context, "estas navegando", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.drawer_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_manu) {
            logout()
        }
        return super.onOptionsItemSelected(item)
    }

    fun logout() {
        val firebase = FirebaseAuth.getInstance()
        firebase.signOut()
        findNavController().navigate(R.id.login)
     requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
        Toast.makeText(requireContext(), "estas navegando", Toast.LENGTH_SHORT).show()

    }

    override fun onmodelClick(model: NotasEntity) {

    }

}