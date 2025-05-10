package com.joandev.embassamentscatalunya.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReservoirApiModel(
    @SerialName("nom_embassament")
    val name: String,
    @SerialName("volum_embassat")
    val currentVolumeHm3String: String,
    @SerialName("percentatge_volum_embassat")
    val percentageFullString: String,
    @SerialName("data")
    val lastUpdate: String
) {
    val currentVolumeHm3: Double
        get() = currentVolumeHm3String.replace(",", ".").toDoubleOrNull() ?: 0.0
    val percentageFull: Double
        get() = percentageFullString.replace(",", ".").toDoubleOrNull() ?: 0.0
}
