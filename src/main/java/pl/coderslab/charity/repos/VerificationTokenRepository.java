package pl.coderslab.charity.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.security.User;

public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}