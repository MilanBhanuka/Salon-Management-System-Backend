package booking.system.review_service.repository;

import booking.system.review_service.modal.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findBySalonId(Long salonId);
}
