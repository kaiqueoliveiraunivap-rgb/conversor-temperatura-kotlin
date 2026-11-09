# Conversor de Temperatura (Android / Kotlin)

Projeto 03 do 4º bimestre de PDM (Android Dev Lab) — aplicativo em **Kotlin** com **Jetpack Compose** e **Material 3**.

## Funcionalidades

- Entrada da temperatura (aceita vírgula ou ponto decimal)
- Seleção da unidade de origem e de destino (Celsius, Fahrenheit, Kelvin)
- Resultado calculado automaticamente
- Validação: texto inválido e valores abaixo do zero absoluto
- Botões para limpar o campo e inverter as unidades

## Fórmulas

- F = (C × 9/5) + 32
- C = (F − 32) × 5/9
- K = C + 273,15

Toda conversão passa por Celsius (`Conversor.kt`).

## Como executar

1. Abra a pasta no Android Studio e aguarde a sincronização do Gradle.
2. Rode (**Run ▶**) em um emulador ou dispositivo.

Pela linha de comando: `./gradlew :app:assembleDebug` e `./gradlew :app:testDebugUnitTest`.

## Estrutura

- `app/src/main/java/.../Conversor.kt` — regras de conversão e validação
- `app/src/main/java/.../MainActivity.kt` — interface em Compose
- `app/src/test/java/.../ConversorTest.kt` — testes unitários
