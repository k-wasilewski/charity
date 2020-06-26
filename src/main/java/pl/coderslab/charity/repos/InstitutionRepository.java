package pl.coderslab.charity.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.entities.Institution;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {
    Institution findById(int id);
}
