package pl.coderslab.charity.controllers.admin;

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
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.repos.UserRepository;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

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
    @WithUserDetails("admin1@admin.pl")
    public void testA_usersView() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-users"))
                .andExpect(model().attribute("users", hasSize(1)))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testB_editView() throws Exception {
        mockMvc.perform(get("/admin/users/edit?id=4"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-users-edit"))
                .andExpect(model().attribute("user",
                        hasProperty("username", is("test@test.pl"))))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testC_edit() throws Exception {
        mockMvc.perform(post("/admin/users/edit?id=4&username=test2@test.pl&" +
                "password=abc&password2=abc"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-users"))
                .andExpect(model().attribute("users",
                        hasItem(hasProperty("username", equalTo("test2@test.pl")))))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testD_detailsView() throws Exception {
        mockMvc.perform(get("/admin/users/details?id=4"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-users-details"))
                .andExpect(model().attribute("user",
                        hasProperty("username", equalTo("test2@test.pl"))))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testE_block() throws Exception {
        mockMvc.perform(get("/admin/users/block?id=4"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-users"))
                .andExpect(model().attribute("users",
                        hasItem(hasProperty("blocked", equalTo(1)))))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testF_del() throws Exception {
        mockMvc.perform(get("/admin/users/del?id=4"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-users"))
                .andExpect(model().attribute("users",
                        not(hasProperty("username", equalTo("test2@test.pl")))))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testG_add() throws Exception {
        mockMvc.perform(get("/admin/users/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-users-edit"))
                .andExpect(model().attribute("add", true))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }
}
