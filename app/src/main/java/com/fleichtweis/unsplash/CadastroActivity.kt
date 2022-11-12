package com.fleichtweis.unsplash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fleichtweis.unsplash.databinding.ActivityCadastroBinding
import com.fleichtweis.unsplash.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCadastrar.setOnClickListener {
            if (binding.editCadastroEmail.text.isNullOrEmpty()) {
                exibirNotificacao("Necessário preencher o e-mail")
            } else if (binding.editCadastroSenha.text.isNullOrEmpty()) {
                exibirNotificacao("Necessário preencher a senha")
            } else {
                cadastrarUsuario()
            }
        }

    }

    private fun cadastrarUsuario() {
        val email = binding.editCadastroEmail.text.toString()
        val senha = binding.editCadastroSenha.text.toString()

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                //exibirNotificacao("Cadastro realizado com sucesso")
            }
            .addOnFailureListener {
                exibirNotificacao("Não foi possível cadastrar usuário")
            }
    }

    private fun exibirNotificacao(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }
}