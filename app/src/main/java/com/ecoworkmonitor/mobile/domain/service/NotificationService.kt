package com.ecoworkmonitor.mobile.domain.service

interface NotificationService {
    fun showNotification(title: String, message: String, notificationId: Int)
}
