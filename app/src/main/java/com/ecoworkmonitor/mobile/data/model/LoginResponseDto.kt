package com.ecoworkmonitor.mobile.data.model

data class LoginResponseDto(
    val user: UserDto,
    val token: AuthTokenDto
)
