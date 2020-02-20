package pl.coderslab.charity.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(@Lazy UserRepository userRepository, @Lazy RoleRepository roleRepository,
                           @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        for (Role r : user.getRoles()) System.out.println(r.getName());
        userRepository.save(user);
    }

    @Override
    public void changePwd(User user, String newPwd) {
        user.setPassword(passwordEncoder.encode(newPwd));
        userRepository.save(user);
    }

    @Override
    public boolean confirmPwd(String username, String password) {
        return passwordEncoder.matches(password, userRepository.getPassword(username));
    }
}