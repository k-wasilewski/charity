package pl.coderslab.charity.security.config;

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
import pl.coderslab.charity.extensions.CustomBeforeAll;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest extends CustomBeforeAll {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("test@test.pl")
    public void authPathAccess() throws Exception {
        mockMvc.perform(get("/auth/donation"))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get("/logout"))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(get("/auth/donation"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUserDetails("dbamozdrowie@wp.pl")
    public void institPathAccess() throws Exception {
        mockMvc.perform(get("/instit/donations"))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get("/logout"))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(get("/instit/donations"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void adminPathAccess() throws Exception {
        mockMvc.perform(get("/admin/admins"))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get("/logout"))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(get("/admin/admins"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}
