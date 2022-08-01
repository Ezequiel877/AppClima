package com.example.appclima.utils.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appclima.R
import com.example.appclima.databinding.ItemNotaBinding

import com.example.appclima.model.NotasEntity

class NotasCardAdapter(private var context: Context,
                       private var listaClientes: List<NotasEntity>, private val listener:OnModelClick
) :
    RecyclerView.Adapter<NotasCardAdapter.ViewHolder>() {

    interface OnModelClick {
        fun onmodelClick(model: NotasEntity)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false)
        val post=ViewHolder(view)
        post.binding.root
            .setOnClickListener {
                val positon = post.adapterPosition.takeIf {
                    it != DiffUtil.DiffResult.NO_POSITION

                } ?:return@setOnClickListener
                listener.onmodelClick(listaClientes[positon])
            }
        return post
    }

    override fun getItemCount(): Int = listaClientes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = listaClientes[position]
        holder.binding.TITLE.text = product.text
        holder.binding.text.text = product.title
        holder.binding.linearLayout.setOnClickListener {
            listener.onmodelClick(product)
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemNotaBinding.bind(view)

    }
}
