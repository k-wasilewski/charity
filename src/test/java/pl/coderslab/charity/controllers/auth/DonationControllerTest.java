package pl.coderslab.charity.controllers.auth;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.controllers.instit.DonationsController;
import pl.coderslab.charity.entities.Category;
import pl.coderslab.charity.entities.Donation;
import pl.coderslab.charity.extensions.CustomBeforeAll;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;
import pl.coderslab.charity.repos.CategoryRepository;
import pl.coderslab.charity.repos.DonationRepository;
import pl.coderslab.charity.repos.InstitutionRepository;
import pl.coderslab.charity.security.repos.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DonationControllerTest extends CustomBeforeAll {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DonationRepository donationRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    InstitutionRepository institutionRepository;
    @Autowired
    DonationsController donationsController;

    @Test
    @WithUserDetails("test@test.pl")
    public void donationForm_withoutId() throws Exception {
        mockMvc.perform(get("/auth/donation"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/donationForm"))
                .andExpect(model().attribute("browser", notNullValue()))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("test@test.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("test@test.pl")
    @Transactional
    public void donationForm_withId() throws Exception {
        Optional<Donation> donation1 = donationRepository.findById(1);

        mockMvc.perform(get("/auth/donation?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/donationForm"))
                .andExpect(model().attribute("browser", notNullValue()))
                .andExpect(model().attribute("donation", donation1))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("test@test.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("test@test.pl")
    @Transactional
    public void donationAction() throws Exception {
        Category category = new Category();
        category.setName("test");
        categoryRepository.save(category);
        Donation donation = new Donation();
        donation.setOwner(userRepository.findByUsername("test@test.pl"));
        donation.setCategories(Arrays.asList(category));
        donation.setQuantity(2);
        donation.setPickUpTime(LocalTime.now());
        donation.setStreet("test");
        donation.setCity("test");
        donation.setZipCode("test");
        donation.setInstitution(institutionRepository.findById(1));
        donation.setPickUpDate(LocalDate.of(2021, 12, 12));

        final String kafkaMsg = "username:test@test.pl" +
                ", institution:" + donation.getInstitution() + ", quantity:" + donation.getQuantity() +
                ", things:"+donation.getCategories();

        assertTrue(donationRepository.findAllByOwner(
                userRepository.findByUsername("test@test.pl")).isEmpty());

        mockMvc.perform(post("/auth/donation")
                .flashAttr("donation", donation))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/form-confirmation"))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("test@test.pl").getUsername()))
                .andReturn();

        assertFalse(donationRepository.findAllByOwner(
                userRepository.findByUsername("test@test.pl")).isEmpty());
        Thread.sleep(1000);
        assertEquals(kafkaMsg, donationsController.getMsg());

        donationRepository.delete(donationRepository.findAllByOwner(
                userRepository.findByUsername("test@test.pl")).get(0));
    }
}
