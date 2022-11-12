package com.fleichtweis.unsplash

import com.fleichtweis.unsplash.model.Imagem

class DadosImagens {

    companion object{

        //var listaDadosImagens: List<Imagem>? = null

        fun carregarImagens(): List<Imagem>{

            val listaImagens = mutableListOf<Imagem>()

            with(listaImagens){
                add(
                    Imagem(
                    "Teste 1",
                    "User 1",
                    R.drawable.logo_unsplash
                )
                )
                add(
                    Imagem(
                        "Teste 2",
                        "User 2",
                        R.drawable.logo_unsplash
                    )
                )
                add(
                    Imagem(
                        "Teste 3",
                        "User 3",
                        R.drawable.logo_unsplash
                    )
                )
            }

            return listaImagens
        }

    }
}