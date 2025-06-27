package booking.system.service.impl;

import booking.system.mapper.NotificationMapper;
import booking.system.modal.Notification;
import booking.system.payload.dto.BookingDTO;
import booking.system.payload.dto.NotificationDTO;
import booking.system.repository.NotificationRepository;
import booking.system.service.NotificationService;
import booking.system.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final BookingFeignClient bookingFeignClient;

    @Override
    public NotificationDTO createNotification(Notification notification) throws Exception {
        Notification savedNotification = notificationRepository.save(notification);
        BookingDTO bookingDTO = bookingFeignClient.getBookingsById(savedNotification.getBookingId()).getBody();

        NotificationDTO notificationDTO = NotificationMapper.toDTO(savedNotification, bookingDTO);
        return notificationDTO;
    }

    @Override
    public List<Notification> getAllNotificationByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getAllNotificationBySalonId(Long salonId) {
        return notificationRepository.findBySalonId(salonId);
    }

    @Override
    public Notification markNotificationAsRead(Long notificationId) {
        return notificationRepository.findById(notificationId).map(
            notification -> {
                notification.setIsRead(true);
                return notificationRepository.save(notification);
            }
        ).orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
    }
}
