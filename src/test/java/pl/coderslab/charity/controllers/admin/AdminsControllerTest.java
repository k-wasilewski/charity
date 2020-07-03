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
import pl.coderslab.charity.extensions.CustomBeforeAll;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.repos.UserRepository;
import pl.coderslab.charity.security.services.UserService;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminsControllerTest extends CustomBeforeAll {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testA_adminsView() throws Exception {
        mockMvc.perform(get("/admin/admins"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-admins"))
                .andExpect(model().attribute("admins", hasSize(2)))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testB_editView() throws Exception {
        mockMvc.perform(get("/admin/admins/edit?id=2"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-admins-edit"))
                .andExpect(model().attribute("admin",
                        hasProperty("username", is("admin2@admin.pl"))))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testC_edit() throws Exception {
        mockMvc.perform(post("/admin/admins/edit?id=2&username=newadmin@admin.pl&" +
                "password=abc&password2=abc"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-admins"))
                .andExpect(model().attribute("admins",
                        hasItem(hasProperty("username", equalTo("newadmin@admin.pl")))))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();

        mockMvc.perform(post("/admin/admins/edit?id=2&username=admin1@admin.pl&" +
                "password=admin&password2=admin"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testD_del() throws Exception {
        mockMvc.perform(get("/admin/admins/del?id=2"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-admins"))
                .andExpect(model().attribute("admins", hasSize(1)))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();

        User restoredAdmin = new User();
        restoredAdmin.setUsername("admin2@admin.pl");
        restoredAdmin.setPassword("admin");
        restoredAdmin.setId(2L);
        userService.saveAdmin(restoredAdmin);
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testE_add() throws Exception {
        mockMvc.perform(get("/admin/admins/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-admins-edit"))
                .andExpect(model().attribute("add", true))
                .andExpect(model().attribute("admin", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }
}
