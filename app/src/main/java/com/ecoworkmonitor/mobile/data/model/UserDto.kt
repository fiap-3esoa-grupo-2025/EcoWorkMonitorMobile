package com.ecoworkmonitor.mobile.data.model

data class UserDto(
    val id: String,
    val email: String,
    val name: String,
    val provider: String,
    val profilePictureUrl: String? = null
)
