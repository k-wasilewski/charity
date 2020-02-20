package pl.coderslab.charity.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("select u.password from User u where u.username=:username")
    public String getPassword(@Param("username") String username);
    List<User> findByRoles_Id(int id);
}
