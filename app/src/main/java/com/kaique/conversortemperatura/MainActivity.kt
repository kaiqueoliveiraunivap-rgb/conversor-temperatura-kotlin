package com.kaique.conversortemperatura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TelaConversor()
                }
            }
        }
    }
}

@Composable
fun TelaConversor() {
    var texto by rememberSaveable { mutableStateOf("") }
    var origem by rememberSaveable { mutableStateOf(Unidade.CELSIUS) }
    var destino by rememberSaveable { mutableStateOf(Unidade.FAHRENHEIT) }

    val entrada = validarEntrada(texto, origem)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Conversor de Temperatura", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Temperatura (${origem.simbolo})") },
            singleLine = true,
            isError = entrada is Entrada.Invalida,
            supportingText = { if (entrada is Entrada.Invalida) Text(entrada.mensagem) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        SeletorUnidade("De", origem) { origem = it }
        SeletorUnidade("Para", destino) { destino = it }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = {
                val tmp = origem
                origem = destino
                destino = tmp
            }) { Text("Inverter unidades") }
            Button(onClick = { texto = "" }) { Text("Limpar") }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Resultado", style = MaterialTheme.typography.labelLarge)
                val resultado = if (entrada is Entrada.Valida) {
                    "${formatar(converter(entrada.valor, origem, destino))} ${destino.simbolo}"
                } else {
                    "—"
                }
                Text(resultado, style = MaterialTheme.typography.displaySmall)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeletorUnidade(titulo: String, selecionada: Unidade, aoSelecionar: (Unidade) -> Unit) {
    var aberto by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = aberto, onExpandedChange = { aberto = it }) {
        OutlinedTextField(
            value = "${selecionada.rotulo} (${selecionada.simbolo})",
            onValueChange = {},
            readOnly = true,
            label = { Text(titulo) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = aberto) },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = aberto, onDismissRequest = { aberto = false }) {
            Unidade.entries.forEach { unidade ->
                DropdownMenuItem(
                    text = { Text("${unidade.rotulo} (${unidade.simbolo})") },
                    onClick = {
                        aoSelecionar(unidade)
                        aberto = false
                    }
                )
            }
        }
    }
}
