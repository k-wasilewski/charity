package pl.coderslab.charity.controllers;

import org.junit.Before;
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
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;
import pl.coderslab.charity.repos.DonationRepository;
import pl.coderslab.charity.repos.InstitutionRepository;
import pl.coderslab.charity.security.repos.UserRepository;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    InstitutionRepository institutionRepository;
    @Autowired
    DonationRepository donationRepository;
    @Autowired
    UserRepository userRepository;
    final String URL = "/";
    int INSTITUTIONS_SIZE;
    int DONATIONS_QUANTITIES;
    int DONATIONS_SUM;

    @Before
    public void init() {
        INSTITUTIONS_SIZE = institutionRepository.findAll().size();
        DONATIONS_QUANTITIES = donationRepository.customQuantitiesSum();
        DONATIONS_SUM = donationRepository.findAll().size();
    }

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
    public void homeAction_unauthorized() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeDoesNotExist("user"))
                .andExpect(model().attribute("institutions", hasSize(INSTITUTIONS_SIZE)))
                .andExpect(model().attribute("donationsQuantities", DONATIONS_QUANTITIES))
                .andExpect(model().attribute("donationsSum", DONATIONS_SUM))
                .andExpect(model().attribute("username", nullValue()))
                .andReturn();
    }

    @Test
    @WithUserDetails("test@test.pl")
    public void homeAction_authorized_user() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("institutions", hasSize(INSTITUTIONS_SIZE)))
                .andExpect(model().attribute("donationsQuantities", DONATIONS_QUANTITIES))
                .andExpect(model().attribute("donationsSum", DONATIONS_SUM))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("test@test.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void homeAction_authorized_admin() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin"))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("institutions", hasSize(INSTITUTIONS_SIZE)))
                .andExpect(model().attribute("donationsQuantities", DONATIONS_QUANTITIES))
                .andExpect(model().attribute("donationsSum", DONATIONS_SUM))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("dbamozdrowie@wp.pl")
    public void homeAction_authorized_instit() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(view().name("instit/instit"))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("institutions", hasSize(INSTITUTIONS_SIZE)))
                .andExpect(model().attribute("donationsQuantities", DONATIONS_QUANTITIES))
                .andExpect(model().attribute("donationsSum", DONATIONS_SUM))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("dbamozdrowie@wp.pl").getUsername()))
                .andReturn();
    }
}
