package booking.system.review_service.payload.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private UserDTO user;
    private SalonDTO salon;
    private String reviewText;
    private double rating;
    private LocalDateTime cratedAt;
}
