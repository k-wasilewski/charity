package pl.coderslab.charity.security.services;

import pl.coderslab.charity.entities.VerificationToken;
import pl.coderslab.charity.security.entities.User;

public interface UserService {

    User findByUserName(String name);

    void saveUser(User user);

    void saveAdmin(User user);

    void changePwd(User user, String newPwd);

    boolean confirmPwd(String username, String password);

    VerificationToken getVerificationToken(String VerificationToken);

    void createVerificationToken(User user, String token);

    void activateUser(User user);

    void saveInstitution(User user);
}
