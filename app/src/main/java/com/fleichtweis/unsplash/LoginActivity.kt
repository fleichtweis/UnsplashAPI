package com.fleichtweis.unsplash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import com.fleichtweis.unsplash.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //Login teste -> ffl@gmail.com - 12345ffl*

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val usuarioAtual = auth.currentUser
        if (usuarioAtual != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.hide() //Esconder barra de título
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if (binding.editLoginEmail.text.isNullOrEmpty()) {
                exibirNotificacao("Necessário preencher o e-mail")
            } else if (binding.editLoginSenha.text.isNullOrEmpty()) {
                exibirNotificacao("Necessário preencher a senha")
            } else {
                logarUsuario()
            }
        }

        binding.btnLoginCadastro.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

    }

    private fun logarUsuario() {

        val email = binding.editLoginEmail.text.toString()
        val senha = binding.editLoginSenha.text.toString()

        auth.signInWithEmailAndPassword(email, senha)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                //exibirNotificacao("Login realizado com sucesso")
            }
            .addOnFailureListener {
                exibirNotificacao("Não foi possível logar usuário")
            }
    }

    private fun exibirNotificacao(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }
}