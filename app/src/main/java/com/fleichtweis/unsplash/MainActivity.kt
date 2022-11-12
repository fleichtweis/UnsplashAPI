package com.fleichtweis.unsplash

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fleichtweis.unsplash.adapter.ImagemAdapter
import com.fleichtweis.unsplash.adapter.ImagemAdapterPesquisa
import com.fleichtweis.unsplash.api.ImagemAPI
import com.fleichtweis.unsplash.api.RetrofitHelper
import com.fleichtweis.unsplash.databinding.ActivityMainBinding
import com.fleichtweis.unsplash.model.Imagem
import com.fleichtweis.unsplash.model.UnsplashImagem
import com.fleichtweis.unsplash.model.UnsplashPesquisa
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }

    private val TAG = "infoUnsplashAPI"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        CoroutineScope(Dispatchers.Main).launch {
            val lista = recuperarImagens()

            /*val listaMutable = mutableListOf<Imagem>()
            listaMutable.add(
                Imagem(
                    lista?.get(4)?.description.toString(),
                    lista?.get(4)?.user?.name.toString(),
                    0
                )
            )

            DadosImagens.listaDadosImagens = listaMutable
            Log.i(TAG, "Coroutine: ${(DadosImagens.listaDadosImagens as MutableList<Imagem>).get(0).descricao}")*/


            binding.rvImagens.adapter = lista?.let {
                ImagemAdapter(it, ){
                    //Log.i(TAG, "onCreate: lista1 $lista")
                    Toast.makeText(applicationContext, it.description, Toast.LENGTH_SHORT).show()
                }
            }
            //binding.rvImagens.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rvImagens.layoutManager = GridLayoutManager(parent, 2)

            //Log.i(TAG, "onCreate: lista2 $lista")
        }


        binding.btnPesquisar.setOnClickListener {
            if (binding.editPesquisa.text.isNullOrEmpty()){ //Faz a busca geral de fotos novamente.
                //Toast.makeText(this, "Você deve preencher uma palavra para pesquisar.", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.Main).launch {
                    val lista = recuperarImagens()

                    binding.rvImagens.recycledViewPool.clear()
                    binding.rvImagens.adapter = lista?.let {
                        ImagemAdapter(it, ){
                            //Log.i(TAG, "onCreate: lista1 $listaPesquisa")
                            Toast.makeText(applicationContext, it.description, Toast.LENGTH_SHORT).show()
                        }
                    }
                    binding.rvImagens.layoutManager = GridLayoutManager(parent, 2)
                }

            } else{ //Pesquisa pela palavra digitada
                CoroutineScope(Dispatchers.Main).launch {
                    val listaPesquisa = recuperarImagensPesquisa()

                    binding.rvImagens.recycledViewPool.clear()
                    binding.rvImagens.adapter = listaPesquisa?.let {
                        ImagemAdapterPesquisa(it, ){
                            //Log.i(TAG, "onCreate: lista1 $listaPesquisa")
                            Toast.makeText(applicationContext, it.description, Toast.LENGTH_SHORT).show()
                        }
                    }
                    //binding.rvImagens.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.rvImagens.layoutManager = GridLayoutManager(parent, 2)
                }
            }
        }
    }

    private suspend fun recuperarImagens(): UnsplashImagem? {
        var resposta: Response<UnsplashImagem>? = null
        val pagina = 1

        try {
            val imagemAPI = retrofit.create(ImagemAPI::class.java)
            resposta = imagemAPI.recuperarListaImagens(pagina)
        }catch (e:Exception){
            e.printStackTrace()
            Log.i(TAG, "recuperarImagens: Erro ao recuperar Imagens")
        }

        if (resposta != null){
            if (resposta.isSuccessful){
                val imagemResposta = resposta.body()
                val listaImagens = imagemResposta

                Log.i(TAG, "recuperarImagens: ${listaImagens?.get(4)?.description}")
                Log.i(TAG, "recuperarImagens: ${listaImagens?.get(4)?.user?.name}")
                Log.i(TAG, "recuperarImagens: ${listaImagens?.get(4)?.urls?.regular}")

                return listaImagens
            }else{
                Log.i(TAG, "recuperarImagens: Erro na requisição:\ncode: ${resposta.code()} msg: ${resposta.message()}")
            }
        }
        return null
    }

    private suspend fun recuperarImagensPesquisa(): UnsplashPesquisa?{
        var resposta: Response<UnsplashPesquisa>? = null
        val pesquisa = binding.editPesquisa.text.toString()

        try {
            val imagemAPI = retrofit.create(ImagemAPI::class.java)
            resposta = imagemAPI.recuperarImagensPesquisa(pesquisa)
        }catch (e:Exception){
            e.printStackTrace()
            Log.i(TAG, "recuperarImagensPesquisa: Erro ao recuperar Imagens")
        }

        if (resposta != null){
            if (resposta.isSuccessful){
                val imagemResposta = resposta.body()
                val listaImagens = imagemResposta

                Log.i(TAG, "recuperarImagensPesquisa: ${listaImagens?.results?.get(2)?.description}")
                Log.i(TAG, "recuperarImagensPesquisa: ${listaImagens?.results?.get(2)?.user?.name}")
                Log.i(TAG, "recuperarImagensPesquisa: ${listaImagens?.results?.get(2)?.urls?.regular}")

                return listaImagens
            }else{
                Log.i(TAG, "recuperarImagensPesquisa: Erro na requisição:\ncode: ${resposta.code()} msg: ${resposta.message()}")
            }
        }

        return null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuSair){
            val builder = AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Você realmente deseja deslogar da conta?")
                .setPositiveButton("Sim"){ _, _ ->
                    auth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .setNegativeButton("Não"){ dialog, _ ->
                    dialog.cancel()
                }
            val dialog: AlertDialog? = builder.create()
            dialog?.show()
        }

        return super.onOptionsItemSelected(item)
    }
}