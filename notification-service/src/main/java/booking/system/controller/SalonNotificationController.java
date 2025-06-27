package booking.system.controller;

import booking.system.mapper.NotificationMapper;
import booking.system.modal.Notification;
import booking.system.payload.dto.BookingDTO;
import booking.system.payload.dto.NotificationDTO;
import booking.system.service.NotificationService;
import booking.system.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.sql.DriverManager.println;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications/salon-owner")
public class SalonNotificationController {
    private final NotificationService notificationService;
    private final BookingFeignClient bookingFeignClient;


    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationBySalonId(
            @PathVariable Long salonId
    ){
        List<Notification> notifications = notificationService.getAllNotificationBySalonId(salonId);

        List<NotificationDTO> notificationDTOs = notifications.stream()
                .map((notification -> {
                    BookingDTO bookingDTO = null;
                    try {
                        bookingDTO = bookingFeignClient.getBookingsById(notification.getBookingId()).getBody();
                    } catch (Exception e) {
                        throw new RuntimeException(e+ " while fetching booking details for notification ID: " + notification.getBookingId());
                    }
                    return NotificationMapper.toDTO(notification, bookingDTO);
                })).collect(Collectors.toList());
        return ResponseEntity.ok(notificationDTOs);
    }
}
