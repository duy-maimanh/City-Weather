package com.mmd.cityweather.common.data.database.model.cities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cities")
data class Cities(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val latitude: Float,
    val longitude: Float,
    val country: String
)
