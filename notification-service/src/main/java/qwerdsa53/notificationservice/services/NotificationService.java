package qwerdsa53.notificationservice.services;


import qwerdsa53.shared.model.notifications.NotificationMessage;

import java.util.List;

public interface NotificationService {
    NotificationMessage saveNotification(NotificationMessage notification);

    List<NotificationMessage> getNotificationsForUser(Long userId);
}
