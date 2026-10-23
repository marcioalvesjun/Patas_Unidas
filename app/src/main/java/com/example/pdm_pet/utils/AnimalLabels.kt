package com.example.pdm_pet.utils

fun animalStatusLabel(status: String?): String = when (status) {
    "ON_STREET" -> "Na rua"
    "TEMP_HOME" -> "Lar temporário"
    "RESCUED_BY_ONG" -> "Resgatado por ONG"
    "AVAILABLE_FOR_ADOPTION" -> "Disponível para adoção"
    "ADOPTED" -> "Adotado"
    else -> "Desconhecido"
}
