package com.example.appclima.UI.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.appclima.databinding.FragmentProductoAddBinding
import com.example.appclima.model.NotasEntity
import com.example.appclima.model.local.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddNotas : DialogFragment(), DialogInterface.OnShowListener {

    private lateinit var positiveButton: Button
    private lateinit var negativeButton: Button
    private lateinit var binding: FragmentProductoAddBinding
    private lateinit var viewmodel:AppDataBase


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
                GlobalScope.launch(Dispatchers.IO) {
                    viewmodel.climadao().saveNotas(notas)

                }
                Toast.makeText(requireContext(), "nota agregada", Toast.LENGTH_SHORT).show()
            }

        }

        negativeButton.setOnClickListener {
            dismiss()
        }
    }
}
