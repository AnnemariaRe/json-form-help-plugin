package com.solanteq.solar.plugin.converter

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

class BalloonNotifier {
    fun notifyError(project: Project, content: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("FileConversionNotification")
            .createNotification(content, NotificationType.ERROR)
            .notify(project)
    }

    fun notifySuccess(project: Project, content: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("FileConversionNotification")
            .createNotification(content, NotificationType.INFORMATION)
            .notify(project)
    }
}