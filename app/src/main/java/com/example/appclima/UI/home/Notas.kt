package com.example.appclima.UI.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.appclima.R
import com.example.appclima.utils.adapters.NotasCardAdapter
import com.example.appclima.databinding.FragmentNotasBinding
import com.example.appclima.model.NotasEntity
import com.example.appclima.model.local.AppDataBase
import com.example.appclima.model.local.LocaDataImpl
import com.example.appclima.presentatation.RoomViewModel
import com.example.appclima.presentatation.hide
import com.example.appclima.presentatation.show
import com.example.appclima.model.local.LocalDataSource
import com.example.appclima.utils.getStatus
import com.google.firebase.auth.FirebaseAuth


class Notas : Fragment(R.layout.fragment_notas), NotasCardAdapter.OnModelClick {

    private lateinit var binding: FragmentNotasBinding
    var notasmemos = listOf<NotasEntity>()
     private val roomview: RoomViewModel by activityViewModels<RoomViewModel> {
        RoomViewModel.RoomFactory(
            LocaDataImpl(LocalDataSource(AppDataBase.getDataBase(requireContext()).climadao()))
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotasBinding.bind(view)
        roomview.getMemos().observe(viewLifecycleOwner, Observer {
            when (it) {
                is getStatus.Loading -> {
                    binding.deltaRelative.show()

                }
                is getStatus.Succes -> {
                    binding.deltaRelative.hide()
                    binding.recyclerView.adapter=NotasCardAdapter(requireContext(),it.data,this )

                }
                is getStatus.Failure -> {
                    binding.deltaRelative.hide()
                    Toast.makeText(requireContext(),
                        "ocurrio un error:${it.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        })

        binding.flotingNextBoton.setOnClickListener {
            AddNotas().show(
                requireActivity().supportFragmentManager,
                AddNotas::class.java.simpleName
            )
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
        Toast.makeText(requireContext(), "Cerraste secion", Toast.LENGTH_SHORT).show()

    }

    override fun onmodelClick(model: NotasEntity) {
        val navegacion=NotasDirections.actionNotasToDetailsNotas(model.id, model.text, model.title )
        findNavController().navigate(navegacion)
    }

}