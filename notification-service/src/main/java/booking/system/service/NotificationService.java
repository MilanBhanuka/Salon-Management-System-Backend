package booking.system.service;

import booking.system.modal.Notification;
import booking.system.payload.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    NotificationDTO createNotification(Notification notification) throws Exception;

    List<Notification> getAllNotificationByUserId(Long userId);

    List<Notification> getAllNotificationBySalonId(Long salonId);

    Notification markNotificationAsRead(Long notificationId);
}
