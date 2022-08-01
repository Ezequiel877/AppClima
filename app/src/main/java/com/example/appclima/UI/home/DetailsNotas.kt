package com.example.appclima.UI.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appclima.R
import com.example.appclima.databinding.FragmentDetailsNotasBinding
import com.example.appclima.model.local.AppDataBase
import com.example.appclima.model.local.LocaDataImpl
import com.example.appclima.model.local.LocalDataSource
import com.example.appclima.presentatation.RoomViewModel
import com.example.appclima.utils.getStatus


class DetailsNotas : Fragment(R.layout.fragment_details_notas) {

    private lateinit var binding: FragmentDetailsNotasBinding
    private val args by navArgs<DetailsNotasArgs>()
    private val roomview: RoomViewModel by activityViewModels<RoomViewModel> {
        RoomViewModel.RoomFactory(
            LocaDataImpl(LocalDataSource(AppDataBase.getDataBase(requireContext()).climadao()))
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsNotasBinding.bind(view)

        binding.descripcion.setText(args.title)
        binding.titlememo.setText(args.text)

        binding.eliminar.setOnClickListener {
            roomview.delete(args.id).observe(viewLifecycleOwner, Observer {
                when (it) {
                    is getStatus.Loading -> {

                    }
                    is getStatus.Succes -> {

                        Toast.makeText(requireContext(),
                            "se elimino ${it}",
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                    is getStatus.Failure -> {

                        Toast.makeText(requireContext(),
                            "ocurrio un error:${it.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            })
            Toast.makeText(context, "se elimino esta nota", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.notas)
        }

        binding.actualizar.setOnClickListener {
            roomview.update(args.id, binding.descripcion.text.toString(), binding.titlememo.text.toString()).observe(viewLifecycleOwner, Observer {
                when (it) {
                    is getStatus.Loading -> {

                    }
                    is getStatus.Succes -> {

                        findNavController().navigate(R.id.notas)

                    }
                    is getStatus.Failure -> {

                        Toast.makeText(requireContext(),
                            "ocurrio un error:${it.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            })
        }

    }
}