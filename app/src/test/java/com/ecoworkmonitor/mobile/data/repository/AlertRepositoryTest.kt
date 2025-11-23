package com.ecoworkmonitor.mobile.data.repository

import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.data.local.dao.AlertDao
import com.ecoworkmonitor.mobile.data.local.entity.AlertEntity
import com.ecoworkmonitor.mobile.domain.model.Alert
import com.ecoworkmonitor.mobile.domain.model.AlertType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any

class AlertRepositoryTest {

    private val alertDao = mock(AlertDao::class.java)
    private val alertRepository = AlertRepositoryImpl(alertDao)

    @Test
    fun `insertAlert success returns Result Success`() = runBlocking {
        // Given
        val alert = Alert(AlertType.TEMPERATURE, 30f, 25f, 1000L, "Hot")
        `when`(alertDao.insertAlert(any())).thenReturn(Unit)

        // When
        val result = alertRepository.insertAlert(alert).first()

        // Then
        assertTrue(result is Result.Success)
    }

    @Test
    fun `getAllAlerts success returns Result Success`() = runBlocking {
        // Given
        val alertEntity = AlertEntity(1, "TEMPERATURE", 30f, 25f, 1000L, "Hot")
        `when`(alertDao.getAllAlerts()).thenReturn(flowOf(listOf(alertEntity)))

        // When
        val result = alertRepository.getAllAlerts().first()

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals(1, data.size)
        assertEquals(AlertType.TEMPERATURE, data[0].type)
    }

    @Test
    fun `clearAlerts success returns Result Success`() = runBlocking {
        // Given
        `when`(alertDao.clearAlerts()).thenReturn(Unit)

        // When
        val result = alertRepository.clearAlerts().first()

        // Then
        assertTrue(result is Result.Success)
    }
}
