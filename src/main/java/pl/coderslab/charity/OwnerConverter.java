package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.coderslab.charity.auth.User;
import pl.coderslab.charity.auth.UserRepository;

public class OwnerConverter implements Converter<String, User> {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User convert(String username) {
        User group = userRepository.findByUsername(username);
        return group;
    }
}
