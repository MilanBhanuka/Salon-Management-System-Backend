package booking.system.review_service.service;

import booking.system.review_service.modal.Review;
import booking.system.review_service.payload.dto.ReviewRequest;
import booking.system.review_service.payload.dto.SalonDTO;
import booking.system.review_service.payload.dto.UserDTO;

import java.util.List;

public interface ReviewService {
    Review createReview(
            ReviewRequest reviewRequest,
            UserDTO userDTO,
            SalonDTO salonDTO
    );

    List<Review> getReviewsBySalonId(Long salonId);

    Review updateReview(
            ReviewRequest reviewRequest,
            Long reviewId,
            Long userId
    ) throws Exception;

    void deleteReview(Long reviewId, Long userId) throws Exception;
}
