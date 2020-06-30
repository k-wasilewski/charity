package pl.coderslab.charity.controllers.auth;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.entities.Category;
import pl.coderslab.charity.entities.Donation;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AllDonationsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DonationRepository donationRepository;
    @Autowired
    InstitutionRepository institutionRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @ClassRule
    public static KafkaContainer kafkaContainer = new KafkaContainer();

    @BeforeClass
    public static void setKafkaContainerName() {
        kafkaContainer.setNetworkAliases(Arrays.asList("kafka"));
        KafkaConsumerConfig.setUrl(kafkaContainer.getBootstrapServers());
        KafkaProducerConfig.setUrl(kafkaContainer.getBootstrapServers());
        KafkaTopicConfig.setUrl(kafkaContainer.getBootstrapServers());
    }

    @Test
    @WithUserDetails("test@test.pl")
    public void testA_allDonationsView() throws Exception {
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
        donationRepository.save(donation);

        mockMvc.perform(get("/auth/all-donations"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/allDonations"))
                .andExpect(model().attribute("donations",
                        hasItem(hasProperty("owner",
                                hasProperty("username", equalTo("test@test.pl"))))))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("test@test.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("test@test.pl")
    public void testB_del() throws Exception {
        mockMvc.perform(get("/auth/donation/del?id=4"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/allDonations"))
                .andExpect(model().attribute("donations",
                        not(hasItem(hasProperty("owner",
                                hasProperty("username", equalTo("test@test.pl")))))))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("test@test.pl").getUsername()))
                .andReturn();
    }
}
