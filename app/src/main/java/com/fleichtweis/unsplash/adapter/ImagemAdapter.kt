package com.fleichtweis.unsplash.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fleichtweis.unsplash.DadosImagens
import com.fleichtweis.unsplash.R
import com.fleichtweis.unsplash.model.Imagem
import com.fleichtweis.unsplash.model.UnsplashImagem
import com.fleichtweis.unsplash.model.UnsplashImagemItem
import com.squareup.picasso.Picasso

class ImagemAdapter(private val imagens: UnsplashImagem, private val onClick: (UnsplashImagemItem) -> Unit): RecyclerView.Adapter<ImagemAdapter.ImagemViewHolder>() {

    //private val imagens = DadosImagens.carregarImagens()


    inner class ImagemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imageItemRV: ImageView
        private val textDescricao: TextView
        private val textNomeUsuario: TextView

        init {
            imageItemRV = itemView.findViewById(R.id.imageItemRV)
            textDescricao = itemView.findViewById(R.id.textDescricao)
            textNomeUsuario = itemView.findViewById(R.id.textNomeUsuario)
        }

        fun bind(imagem: UnsplashImagemItem, onClick: (UnsplashImagemItem) -> Unit){

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
        val imagem = imagens[position]
        holder.bind(imagem, onClick)
    }

    override fun getItemCount(): Int {
        return imagens.size
    }
}