package booking.system.review_service.mapper;

import booking.system.review_service.modal.Review;
import booking.system.review_service.payload.dto.ReviewDTO;
import booking.system.review_service.payload.dto.UserDTO;

public class ReviewMapper {
    public static ReviewDTO toDTO(Review review, UserDTO user){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setReviewText(review.getReviewText());
        reviewDTO.setUser(user);
        reviewDTO.setRating(review.getRating());
        reviewDTO.setCratedAt(review.getCreatedAt());

        return reviewDTO;
    }
}
