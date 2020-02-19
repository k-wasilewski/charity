package pl.coderslab.charity.auth;

public interface UserService {

    User findByUserName(String name);

    void saveUser(User user);

    void changePwd(User user, String newPwd);

    boolean confirmPwd(String username, String password);
}
