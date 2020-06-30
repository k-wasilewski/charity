package pl.coderslab.charity.controllers.instit;

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
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.controllers.auth.DonationController;
import pl.coderslab.charity.entities.Donation;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;
import pl.coderslab.charity.repos.CategoryRepository;
import pl.coderslab.charity.repos.DonationRepository;
import pl.coderslab.charity.repos.InstitutionRepository;
import pl.coderslab.charity.security.repos.UserRepository;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DonationsControllerTest {
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
    DonationController donationController;

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
    @WithUserDetails("dbamozdrowie@wp.pl")
    public void testA_myDonations() throws Exception {
        mockMvc.perform(get("/instit/donations"))
                .andExpect(status().isOk())
                .andExpect(view().name("instit/myDonations"))
                .andExpect(model().attribute("donations", hasSize(2)))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("dbamozdrowie@wp.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("dbamozdrowie@wp.pl")
    @Transactional
    public void testB_pickedupOn() throws Exception {
        Donation donation = donationRepository.findById(1).get();

        final String kafkaMsg = "institution:" + donation.getInstitution() + ", username: " +
                donation.getOwner() + ", quantity:" + donation.getQuantity() +
                ", things:"+donation.getCategories();

        mockMvc.perform(get("/instit/donation/pickedupOn?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("instit/myDonations"))
                .andExpect(model().attribute("donations", hasSize(2)))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("dbamozdrowie@wp.pl").getUsername()))
                .andReturn();

        assertEquals(1, donation.getPickedUp());
        Thread.sleep(1000);
        assertEquals(kafkaMsg, donationController.getMsg());
    }

    @Test
    @WithUserDetails("dbamozdrowie@wp.pl")
    @Transactional
    public void testC_pickedupOff() throws Exception {
        Donation donation = donationRepository.findById(1).get();

        mockMvc.perform(get("/instit/donation/pickedupOff?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("instit/myDonations"))
                .andExpect(model().attribute("donations", hasSize(2)))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("dbamozdrowie@wp.pl").getUsername()))
                .andReturn();

        assertEquals(0, donation.getPickedUp());
    }
}
