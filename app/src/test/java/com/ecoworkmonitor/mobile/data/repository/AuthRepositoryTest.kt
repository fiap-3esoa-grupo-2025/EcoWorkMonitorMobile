package com.ecoworkmonitor.mobile.data.repository

import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.data.api.FakeAuthApi
import com.ecoworkmonitor.mobile.data.model.AuthTokenDto
import com.ecoworkmonitor.mobile.data.model.LoginRequestDto
import com.ecoworkmonitor.mobile.data.model.LoginResponseDto
import com.ecoworkmonitor.mobile.data.model.UserDto
import com.ecoworkmonitor.mobile.domain.model.AuthProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any

class AuthRepositoryTest {

    private val fakeAuthApi = mock(FakeAuthApi::class.java)
    private val authRepository = AuthRepositoryImpl(fakeAuthApi)

    @Test
    fun `login success returns Result Success`() = runBlocking {
        // Given
        val email = "test@example.com"
        val password = "password"
        val userDto = UserDto("id", email, "Test User", "EMAIL", null)
        val tokenDto = AuthTokenDto("access", "refresh", 3600)
        val responseDto = LoginResponseDto(userDto, tokenDto)

        `when`(fakeAuthApi.login(any())).thenReturn(responseDto)

        // When
        val result = authRepository.login(email, password).first()

        // Then
        assertTrue(result is Result.Success)
        val success = result as Result.Success
        assertEquals(email, success.data.user.email)
        assertEquals("access", success.data.token.accessToken)
    }

    @Test
    fun `login failure returns Result Error`() = runBlocking {
        // Given
        val email = "test@example.com"
        val password = "wrong_password"
        val exception = RuntimeException("Login failed")

        `when`(fakeAuthApi.login(any())).thenThrow(exception)

        // When
        val result = authRepository.login(email, password).first()

        // Then
        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertEquals(exception, error.exception)
    }
}
