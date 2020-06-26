package pl.coderslab.charity.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.entities.Donation;
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.security.entities.User;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Integer> {
    @Query("SELECT SUM(d.quantity) FROM Donation d")
    int customQuantitiesSum();
    List<Donation> findAllByInstitution(Institution institution);
    List<Donation> findAllByOwner(User user);
}
