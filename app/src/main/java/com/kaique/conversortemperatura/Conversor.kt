package com.kaique.conversortemperatura

/** Unidades de temperatura suportadas e seus limites físicos (zero absoluto). */
enum class Unidade(val rotulo: String, val simbolo: String, val zeroAbsoluto: Double) {
    CELSIUS("Celsius", "°C", -273.15),
    FAHRENHEIT("Fahrenheit", "°F", -459.67),
    KELVIN("Kelvin", "K", 0.0)
}

/** Converte qualquer unidade para Celsius. */
fun paraCelsius(valor: Double, origem: Unidade): Double = when (origem) {
    Unidade.CELSIUS -> valor
    Unidade.FAHRENHEIT -> (valor - 32) * 5 / 9
    Unidade.KELVIN -> valor - 273.15
}

/** Converte um valor em Celsius para a unidade de destino. */
fun deCelsius(celsius: Double, destino: Unidade): Double = when (destino) {
    Unidade.CELSIUS -> celsius
    Unidade.FAHRENHEIT -> celsius * 9 / 5 + 32
    Unidade.KELVIN -> celsius + 273.15
}

fun converter(valor: Double, origem: Unidade, destino: Unidade): Double =
    deCelsius(paraCelsius(valor, origem), destino)

/** Resultado da validação do texto digitado pelo usuário. */
sealed interface Entrada {
    data class Valida(val valor: Double) : Entrada
    data class Invalida(val mensagem: String) : Entrada
    data object Vazia : Entrada
}

fun validarEntrada(texto: String, origem: Unidade): Entrada {
    if (texto.isBlank()) return Entrada.Vazia
    val numero = texto.trim().replace(',', '.').toDoubleOrNull()
        ?: return Entrada.Invalida("Digite um número válido.")
    if (!numero.isFinite()) return Entrada.Invalida("Digite um número válido.")
    if (numero < origem.zeroAbsoluto) {
        return Entrada.Invalida(
            "Abaixo do zero absoluto (${formatar(origem.zeroAbsoluto)} ${origem.simbolo})."
        )
    }
    return Entrada.Valida(numero)
}

/** Formata com até 2 casas decimais, usando vírgula. */
fun formatar(valor: Double): String {
    val arredondado = Math.round(valor * 100) / 100.0
    val texto = if (arredondado == arredondado.toLong().toDouble()) {
        arredondado.toLong().toString()
    } else {
        arredondado.toString()
    }
    return texto.replace('.', ',')
}
