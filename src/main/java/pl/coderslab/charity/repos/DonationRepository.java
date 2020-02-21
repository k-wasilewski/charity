package pl.coderslab.charity.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Integer> {
    @Query("SELECT SUM(d.quantity) FROM Donation d")
    int customQuantitiesSum();
    List<Donation> findAllByInstitution(Institution institution);
}
