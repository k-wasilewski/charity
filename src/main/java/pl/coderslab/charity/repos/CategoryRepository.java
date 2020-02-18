package pl.coderslab.charity.repos;

import pl.coderslab.charity.repos.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
