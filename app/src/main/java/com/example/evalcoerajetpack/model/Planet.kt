package com.example.evalcoerajetpack.model

data class Planet(
    val climate: String = "a",
    val created: String = "b",
    val diameter: String = "c",
    val edited: String = "d",
    val films: List<String> = listOf(),
    val gravity: String = "e",
    val name: String = "f",
    val orbital_period: String = "g",
    val population: String = "h",
    val residents: List<String> = listOf(),
    val rotation_period: String = "i",
    val surface_water: String = "j",
    val terrain: String = "k",
    val url: String = "l"

) {
    override fun toString(): String {
        return "Planet(climate='$climate', created='$created', diameter='$diameter', edited='$edited', films=$films, gravity='$gravity', name='$name', orbital_period='$orbital_period', population='$population', residents=$residents, rotation_period='$rotation_period', surface_water='$surface_water', terrain='$terrain', url='$url')"
    }
}