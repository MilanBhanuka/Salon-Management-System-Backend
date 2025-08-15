package booking.system.repository;

import booking.system.modal.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;


public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering,Long> {
    Set<ServiceOffering> findBySalonId(Long salonId);
    Optional<ServiceOffering> findByIdAndSalonId(Long id, Long salonId);

}
