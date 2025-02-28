package dev.pavila.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.pavila.model.HouseRent;

@Repository // ✅ Ensure this annotation is present
public interface HouseRentRepo extends JpaRepository<HouseRent, Long> {
}
