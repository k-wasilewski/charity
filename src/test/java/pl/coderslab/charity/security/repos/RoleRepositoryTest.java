package pl.coderslab.charity.security.repos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.extensions.CustomBeforeAll;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTest extends CustomBeforeAll {
    @Autowired
    RoleRepository roleRepository;

    @Test
    @Transactional
    public void findByName() {
        assertNotNull(roleRepository.findByName("ROLE_USER"));
    }
}

