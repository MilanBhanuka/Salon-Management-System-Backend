package booking.system.review_service.controller;

import booking.system.review_service.modal.Review;
import booking.system.review_service.payload.dto.ApiResponse;
import booking.system.review_service.payload.dto.ReviewRequest;
import booking.system.review_service.payload.dto.SalonDTO;
import booking.system.review_service.payload.dto.UserDTO;
import booking.system.review_service.service.ReviewService;
import booking.system.review_service.service.client.SalonFeignClient;
import booking.system.review_service.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserFeignClient userFeignClient;
    private final SalonFeignClient salonFeignClient;


    @PostMapping("/salon/{salonId}")
    public ResponseEntity<Review> createReview(
            @PathVariable Long salonId,
            @RequestBody ReviewRequest reviewRequest,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();

        Review review = reviewService.createReview(reviewRequest, user, salon);

        return ResponseEntity.ok(review);
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<Review>> getReviews(
            @PathVariable Long salonId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();

        List<Review> reviews = reviewService.getReviewsBySalonId(salon.getId());

        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest reviewRequest,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        Review reviews = reviewService.updateReview(reviewRequest, reviewId, user.getId());

        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        reviewService.deleteReview(reviewId, user.getId());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Review deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }
}
