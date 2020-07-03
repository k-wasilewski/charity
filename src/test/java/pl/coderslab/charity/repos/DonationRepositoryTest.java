package pl.coderslab.charity.repos;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.*;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.entities.Donation;
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.extensions.CustomBeforeAll;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.repos.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DonationRepositoryTest extends CustomBeforeAll {
    @Autowired
    DonationRepository donationRepository;
    @Autowired
    InstitutionRepository institutionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @Transactional
    public void customQuantitiesSum() {
        final int INITIAL_SUM = 5 + 2 + 7;

        assertEquals(INITIAL_SUM, donationRepository.customQuantitiesSum());
    }

    @Test
    @Transactional
    public void findAllByInstitution() {
        Institution institution1 = institutionRepository.findById(1);
        Institution institution2 = institutionRepository.findById(2);

        assertEquals(2,
                donationRepository.findAllByInstitution(institution1).size());
        assertEquals(1,
                donationRepository.findAllByInstitution(institution2).size());
    }

    @Test
    @Transactional
    public void findAllByOwner() {
        User testUser = new User();
        testUser.setUsername("test@testuser.com");
        userRepository.save(testUser);
        Donation donationWithOwner = new Donation();
        donationWithOwner.setOwner(testUser);
        donationWithOwner.setInstitution(institutionRepository.findById(1));
        donationWithOwner.setZipCode("666");
        donationWithOwner.setPickUpDate(LocalDate.of(2020, 07, 21));
        donationWithOwner.setCity("Warsaw");
        donationWithOwner.setPickUpTime(LocalTime.now());
        donationWithOwner.setStreet("Unknown");
        donationWithOwner.setCategories(Arrays.asList(categoryRepository.findById(1).get()));
        donationWithOwner.setQuantity(1);
        donationWithOwner.setCreated(LocalDate.now());
        donationRepository.save(donationWithOwner);

        assertEquals(donationWithOwner.getId(),
                donationRepository.findAllByOwner(testUser).get(0).getId());

        userRepository.delete(userRepository.findByUsername("test@testuser.com"));
        donationRepository.delete(donationRepository.
                findAllByOwner(testUser).get(0));
    }
}

