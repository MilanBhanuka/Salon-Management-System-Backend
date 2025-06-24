package com.booking.system.repository;

import com.booking.system.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Set<Category> findBySalonId(Long salonId);

    Category findByIdAndSalonId(Long id,Long salonId);
}
