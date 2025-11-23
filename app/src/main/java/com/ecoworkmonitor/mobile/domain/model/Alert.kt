package com.ecoworkmonitor.mobile.domain.model

data class Alert(
    val id: Long = 0,
    val type: AlertType,
    val value: Float,
    val threshold: Float,
    val timestamp: Long,
    val message: String
)
