package com.example.primeiraaplicacao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.Px
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.primeiraaplicacao.ui.theme.PrimeiraAplicacaoTheme
import com.example.primeiraaplicacao.viewModel.Repository
import com.example.primeiraaplicacao.viewModel.ViewModelPessoa
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {

    val db = Firebase.firestore

    @Composable
    fun Primeiraaplicacao(content: @Composable () -> Unit) {
        MaterialTheme {
            content()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Primeiraaplicacao{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(db)
                }
            }
        }
    }

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun App(db : FirebaseFirestore) {
        var nome by remember {
            mutableStateOf("")
        }
        var telefone by remember {
            mutableStateOf("")
        }
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
            }
            Row(
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center
            ) {
                Text(text = "App Firebase Firestore")
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
            }
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.3f)
                ) {
                    Text(text = "Nome:")
                }
                Column(
                ) {
                    TextField(
                        value = nome,
                        onValueChange = { nome = it }
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.3f)
                ) {
                    Text(text = "Telefone:")
                }
                Column(
                ) {
                    TextField(
                        value = telefone,
                        onValueChange = { telefone = it }
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
            }
            Row(
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center
            ) {
                Button(onClick = {

                    val city = hashMapOf(
                        "nome" to nome,
                        "telefone" to telefone
                    )

                    db.collection("Clientes").document("PrimeiroCliente")
                        .set(city)
                        .addOnSuccessListener { Log.d(ContentValues.TAG, "Succefully written") }
                        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Errow writing doc", e)}
                }) {
                    Text(text = "Cadastrar")
                }
                Row(
                    Modifier
                        .fillMaxWidth(0.5f)
                ) {
                    Text(text = "Nome:")
                }
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                ) {
                    Text(text = "Telefone:")
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.3f)
                ) {
                    val clientes = mutableStateListOf<HashMap<String, String>>()
                    db.collection("Clientes")
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                val lista = hashMapOf(
                                    "nome" to "${document.data.get("nome")}",
                                    "telefone" to "${document.data.get("telefone")}"
                                )
                                clientes.add(lista)
                                Log.d(TAG, "${document.id} => ${document.data}")
                            }
                        }
                        .addOnFailureListener{ exception ->
                            Log.w(TAG, "Error getting documents: ", exception)
                        }
                }
            }
        }
    }
}
