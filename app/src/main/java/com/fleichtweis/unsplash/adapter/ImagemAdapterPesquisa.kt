package com.fleichtweis.unsplash.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fleichtweis.unsplash.R
import com.fleichtweis.unsplash.model.Result
import com.fleichtweis.unsplash.model.UnsplashPesquisa
import com.squareup.picasso.Picasso

class ImagemAdapterPesquisa (private val imagens: UnsplashPesquisa, private val onClick: (Result) -> Unit): RecyclerView.Adapter<ImagemAdapterPesquisa.ImagemViewHolder>() {


    inner class ImagemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imageItemRV: ImageView
        private val textDescricao: TextView
        private val textNomeUsuario: TextView

        init {
            imageItemRV = itemView.findViewById(R.id.imageItemRV)
            textDescricao = itemView.findViewById(R.id.textDescricao)
            textNomeUsuario = itemView.findViewById(R.id.textNomeUsuario)
        }

        fun bind(imagem: Result, onClick: (Result) -> Unit){

            itemView.setOnClickListener {
                onClick(imagem)
            }


            textDescricao.text = imagem.description
            textNomeUsuario.text = imagem.user.name
            Picasso.get()
                .load(imagem.urls.regular)
                .into(imageItemRV)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_imagem, parent, false)
        return ImagemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImagemViewHolder, position: Int) {
        val imagem = imagens.results[position]
        holder.bind(imagem, onClick)
    }

    override fun getItemCount(): Int {
        return imagens.results.size
    }
}