package com.fleichtweis.unsplash.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Imagem(
    var descricao: String,
    var nomeUsuario: String,
    var resIdImagem: Int
) : Parcelable