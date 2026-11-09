package com.kaique.conversortemperatura

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ConversorTest {
    @Test fun celsiusParaFahrenheit() =
        assertEquals(212.0, converter(100.0, Unidade.CELSIUS, Unidade.FAHRENHEIT), 1e-9)

    @Test fun fahrenheitParaCelsius() =
        assertEquals(0.0, converter(32.0, Unidade.FAHRENHEIT, Unidade.CELSIUS), 1e-9)

    @Test fun celsiusParaKelvin() =
        assertEquals(273.15, converter(0.0, Unidade.CELSIUS, Unidade.KELVIN), 1e-9)

    @Test fun kelvinParaFahrenheit() =
        assertEquals(32.0, converter(273.15, Unidade.KELVIN, Unidade.FAHRENHEIT), 1e-9)

    @Test fun rejeitaAbaixoDoZeroAbsoluto() =
        assertTrue(validarEntrada("-1", Unidade.KELVIN) is Entrada.Invalida)

    @Test fun aceitaVirgulaDecimal() =
        assertEquals(Entrada.Valida(36.5), validarEntrada("36,5", Unidade.CELSIUS))

    @Test fun rejeitaTexto() =
        assertTrue(validarEntrada("abc", Unidade.CELSIUS) is Entrada.Invalida)

    @Test fun formataComVirgula() = assertEquals("98,6", formatar(98.6))
}
