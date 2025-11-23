package com.ecoworkmonitor.mobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String, // TEMP, NOISE, LIGHT
    val value: Float,
    val threshold: Float,
    val timestamp: Long,
    val message: String
) {
    fun toDomain() = com.ecoworkmonitor.mobile.domain.model.Alert(
        id = id,
        type = com.ecoworkmonitor.mobile.domain.model.AlertType.valueOf(type),
        value = value,
        threshold = threshold,
        timestamp = timestamp,
        message = message
    )

    companion object {
        fun fromDomain(alert: com.ecoworkmonitor.mobile.domain.model.Alert) = AlertEntity(
            id = alert.id,
            type = alert.type.name,
            value = alert.value,
            threshold = alert.threshold,
            timestamp = alert.timestamp,
            message = alert.message
        )
    }
}
