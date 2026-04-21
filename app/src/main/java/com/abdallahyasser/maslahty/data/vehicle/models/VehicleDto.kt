package com.abdallahyasser.maslahty.data.vehicle.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VehicleDto(
    @SerialName("id")
    val id: String,
    @SerialName("owner_id")
    val ownerId: String,
    @SerialName("license_plate")
    val licensePlate: String,
    @SerialName("chassis_number")
    val chassisNumber: String,
    @SerialName("engine_number")
    val engineNumber: String,
    @SerialName("model")
    val model: String,
    @SerialName("manufacturing_year")
    val manufacturingYear: Int,
    @SerialName("color")
    val color: String,
    @SerialName("kilometers")
    val kilometers: Int,
    @SerialName("condition")
    val condition: String,
    @SerialName("license_image_url")
    val licenseImageUrl: String?,
    @SerialName("vehicle_image_url")
    val vehicleImageUrl: String?,
    @SerialName("chassis_image_url")
    val chassisImageUrl: String?,
    @SerialName("engine_image_url")
    val engineImageUrl: String?,
    @SerialName("created_at")
    val createdAt: Long,
    @SerialName("updated_at")
    val updatedAt: Long
)