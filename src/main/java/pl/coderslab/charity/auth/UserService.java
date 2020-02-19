package pl.coderslab.charity.auth;

public interface UserService {

    User findByUserName(String name);

    void saveUser(User user);
}
