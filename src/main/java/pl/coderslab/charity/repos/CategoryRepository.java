package pl.coderslab.charity.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
