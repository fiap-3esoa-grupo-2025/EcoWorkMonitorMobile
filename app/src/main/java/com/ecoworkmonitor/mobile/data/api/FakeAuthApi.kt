package com.ecoworkmonitor.mobile.data.api

import com.ecoworkmonitor.mobile.data.model.AuthTokenDto
import com.ecoworkmonitor.mobile.data.model.LoginRequestDto
import com.ecoworkmonitor.mobile.data.model.LoginResponseDto
import com.ecoworkmonitor.mobile.data.model.ProviderLoginRequestDto
import com.ecoworkmonitor.mobile.data.model.UserDto
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class FakeAuthApi @Inject constructor() {
    
    // Simulated test accounts
    private val testAccounts = mapOf(
        "test@ecowork.com" to "password123",
        "admin@ecowork.com" to "admin123",
        "user@example.com" to "user123"
    )
    
    // Simulated user database (email -> UserDto)
    private val userDatabase = mutableMapOf<String, UserDto>()
    
    // Active sessions (token -> UserDto)
    private val activeSessions = mutableMapOf<String, UserDto>()
    
    /**
     * Simulate email/password login
     * Returns success, invalid credentials, or network error
     */
    suspend fun login(request: LoginRequestDto): LoginResponseDto {
        // Simulate network delay (100-500ms)
        delay(Random.nextLong(100, 500))
        
        // Randomly simulate network error (10% chance)
        if (Random.nextFloat() < 0.1f) {
            throw NetworkException("Erro de rede: não foi possível conectar ao servidor")
        }
        
        // Check credentials
        val expectedPassword = testAccounts[request.email]
        if (expectedPassword == null || expectedPassword != request.password) {
            throw InvalidCredentialsException("Credenciais inválidas")
        }
        
        // Get or create user
        val user = userDatabase.getOrPut(request.email) {
            UserDto(
                id = UUID.randomUUID().toString(),
                email = request.email,
                name = request.email.substringBefore("@").replaceFirstChar { it.uppercase() },
                provider = "EMAIL",
                profilePictureUrl = null
            )
        }
        
        // Generate tokens
        val token = generateAuthToken()
        
        // Store session
        activeSessions[token.accessToken] = user
        
        return LoginResponseDto(
            user = user,
            token = token
        )
    }
    
    /**
     * Simulate OAuth provider login (Google, GitHub, Microsoft)
     */
    suspend fun loginWithProvider(request: ProviderLoginRequestDto): LoginResponseDto {
        // Simulate network delay
        delay(Random.nextLong(150, 600))
        
        // Randomly simulate network error (5% chance)
        if (Random.nextFloat() < 0.05f) {
            throw NetworkException("Erro de rede: não foi possível conectar ao servidor")
        }
        
        // Validate provider
        val validProviders = listOf("GOOGLE", "GITHUB", "MICROSOFT")
        if (request.provider !in validProviders) {
            throw InvalidProviderException("Provedor inválido: ${request.provider}")
        }
        
        // Simulate provider-specific errors (5% chance)
        if (Random.nextFloat() < 0.05f) {
            throw ProviderAuthException("Erro ao autenticar com ${request.provider}")
        }
        
        // Simulate successful OAuth flow
        // In real implementation, we would validate the provider token
        val email = generateProviderEmail(request.provider)
        
        // Get or create user
        val user = userDatabase.getOrPut(email) {
            UserDto(
                id = UUID.randomUUID().toString(),
                email = email,
                name = generateProviderName(request.provider),
                provider = request.provider,
                profilePictureUrl = generateProviderProfilePicture(request.provider)
            )
        }
        
        // Generate tokens
        val token = generateAuthToken()
        
        // Store session
        activeSessions[token.accessToken] = user
        
        return LoginResponseDto(
            user = user,
            token = token
        )
    }
    
    /**
     * Simulate token refresh
     */
    suspend fun refreshToken(refreshToken: String): AuthTokenDto {
        delay(Random.nextLong(50, 200))
        
        // Randomly simulate expired refresh token (10% chance)
        if (Random.nextFloat() < 0.1f) {
            throw TokenExpiredException("Refresh token expirado")
        }
        
        // Generate new tokens
        return generateAuthToken()
    }
    
    /**
     * Simulate logout
     */
    suspend fun logout(accessToken: String) {
        delay(Random.nextLong(50, 150))
        
        // Remove session
        activeSessions.remove(accessToken)
    }
    
    /**
     * Validate access token (for future use)
     */
    fun validateToken(accessToken: String): Boolean {
        return activeSessions.containsKey(accessToken)
    }
    
    // Helper functions
    
    private fun generateAuthToken(): AuthTokenDto {
        return AuthTokenDto(
            accessToken = "access_${UUID.randomUUID()}",
            refreshToken = "refresh_${UUID.randomUUID()}",
            expiresIn = 3600 // 1 hour
        )
    }
    
    private fun generateProviderEmail(provider: String): String {
        val randomId = Random.nextInt(1000, 9999)
        return when (provider) {
            "GOOGLE" -> "user$randomId@gmail.com"
            "GITHUB" -> "user$randomId@github.com"
            "MICROSOFT" -> "user$randomId@outlook.com"
            else -> "user$randomId@example.com"
        }
    }
    
    private fun generateProviderName(provider: String): String {
        val randomId = Random.nextInt(1000, 9999)
        return when (provider) {
            "GOOGLE" -> "Google User $randomId"
            "GITHUB" -> "GitHub User $randomId"
            "MICROSOFT" -> "Microsoft User $randomId"
            else -> "User $randomId"
        }
    }
    
    private fun generateProviderProfilePicture(provider: String): String {
        return when (provider) {
            "GOOGLE" -> "https://lh3.googleusercontent.com/a/default-user"
            "GITHUB" -> "https://avatars.githubusercontent.com/u/default"
            "MICROSOFT" -> "https://graph.microsoft.com/v1.0/me/photo/\$value"
            else -> null
        } ?: ""
    }
}

// Custom exceptions
class NetworkException(message: String) : Exception(message)
class InvalidCredentialsException(message: String) : Exception(message)
class InvalidProviderException(message: String) : Exception(message)
class ProviderAuthException(message: String) : Exception(message)
class TokenExpiredException(message: String) : Exception(message)
