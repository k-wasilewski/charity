package pl.coderslab.charity.repos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.extensions.CustomBeforeAll;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InstitutionRepositoryTest extends CustomBeforeAll {
    @Autowired
    InstitutionRepository institutionRepository;

    @Test
    @Transactional
    public void findById() {
        final String INSTITUTION_1_NAME = "Dbam o Zdrowie";

        assertEquals(INSTITUTION_1_NAME,
                institutionRepository.findById(1).getName());
    }
}

