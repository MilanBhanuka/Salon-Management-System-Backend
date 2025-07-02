package booking.system.messaging;

import booking.system.modal.Notification;
import booking.system.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NotificationEventConsumer {
    private final NotificationService notificationService;

    @RabbitListener(queues = "notification-queue")
    public void setNotificationEventConsumer(Notification notification) throws Exception {
        notificationService.createNotification(notification);
    }
}
