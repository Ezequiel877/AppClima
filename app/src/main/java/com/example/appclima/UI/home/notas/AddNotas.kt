package com.example.appclima.UI.home.notas

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.appclima.databinding.FragmentProductoAddBinding
import com.example.appclima.model.NotasEntity
import com.example.appclima.model.local.AppDataBase
import com.example.appclima.model.local.LocaDataImpl
import com.example.appclima.model.local.LocalDataSource
import com.example.appclima.presentatation.RoomViewModel
import com.example.appclima.utils.getStatus


class AddNotas : DialogFragment(), DialogInterface.OnShowListener {

    private lateinit var positiveButton: Button
    private lateinit var negativeButton: Button
    private lateinit var binding: FragmentProductoAddBinding
    private lateinit var viewmodel:AppDataBase
    private val roomview: RoomViewModel by activityViewModels<RoomViewModel> {
        RoomViewModel.RoomFactory(
            LocaDataImpl(LocalDataSource(AppDataBase.getDataBase(requireContext()).climadao()))
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let { activity ->
            viewmodel=AppDataBase.getDataBase(activity)
            binding = FragmentProductoAddBinding.inflate(LayoutInflater.from(context))
            binding.let {
                val builder = AlertDialog.Builder(activity)
                    .setTitle("Agregar Productos")
                    .setPositiveButton("Agregar", null)
                    .setNegativeButton("Cancelar", null)
                    .setView(it.root)
                val dialog = builder.create()
                dialog.setOnShowListener(this)

                return dialog
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onShow(dialogInterface: DialogInterface?) {

        val dialogAdd = dialog as? AlertDialog
        dialogAdd?.let { it ->
            positiveButton = it.getButton(Dialog.BUTTON_POSITIVE)
            negativeButton = it.getButton(Dialog.BUTTON_NEGATIVE)
            val ranodon=(1 ..10000).random()
            positiveButton.setOnClickListener {
                val notas = NotasEntity(
                    0,
                    binding.nameProducto.text.toString(),
                    binding.descripcion.text.toString()
                )
                roomview.save(notas).observe(this, Observer {
                    when (it) {
                        is getStatus.Loading -> {

                        }
                        is getStatus.Succes -> {
                            val navegacion=AddNotasDirections.actionAddNotasToNotas()
                            findNavController().navigate(navegacion)

                        }
                        is getStatus.Failure -> {

                            Toast.makeText(requireContext(),
                                "ocurrio un error:${it.exception}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                })

                Toast.makeText(requireContext(), "nota agregada", Toast.LENGTH_SHORT).show()
            }

        }

        negativeButton.setOnClickListener {
            dismiss()
        }
    }
}
