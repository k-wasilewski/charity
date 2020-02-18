package pl.coderslab.charity.repos;

import pl.coderslab.charity.repos.entities.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {
}
