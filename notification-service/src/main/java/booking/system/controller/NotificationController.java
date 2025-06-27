package booking.system.controller;

import booking.system.mapper.NotificationMapper;
import booking.system.modal.Notification;
import booking.system.payload.dto.BookingDTO;
import booking.system.payload.dto.NotificationDTO;
import booking.system.service.NotificationService;
import booking.system.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final BookingFeignClient bookingFeignClient;


    @PostMapping
    private ResponseEntity<NotificationDTO> createNotification(
            @RequestBody Notification notification
            ) throws Exception {
        return ResponseEntity.ok(notificationService.createNotification(notification));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationByUserId(
            @PathVariable Long userId
    ){
        List<Notification> notifications = notificationService.getAllNotificationByUserId(userId);

        List<NotificationDTO> notificationDTOs = notifications.stream()
                .map((notification -> {
                    BookingDTO bookingDTO = null;
                    try {
                        bookingDTO = bookingFeignClient.getBookingsById(notification.getBookingId()).getBody();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return NotificationMapper.toDTO(notification, bookingDTO);
                })).collect(Collectors.toList());
        return ResponseEntity.ok(notificationDTOs);
    }


    @PutMapping("{notificationId}/read")
    private ResponseEntity<NotificationDTO> markNotificationAsRead(
            @PathVariable Long notificationId
    ) throws Exception {
        Notification notification = notificationService.markNotificationAsRead(notificationId);
        BookingDTO bookingDTO = bookingFeignClient.getBookingsById(notification.getBookingId()).getBody();

        return ResponseEntity.ok(NotificationMapper.toDTO(notification, bookingDTO));
    }

}
