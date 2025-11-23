package com.ecoworkmonitor.mobile.ui.util

import com.ecoworkmonitor.mobile.domain.service.NotificationService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationServiceImpl @Inject constructor(
    private val notificationHelper: NotificationHelper
) : NotificationService {
    override fun showNotification(title: String, message: String, notificationId: Int) {
        notificationHelper.showNotification(title, message, notificationId)
    }
}
