package pl.coderslab.charity.security.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.charity.extensions.CustomBeforeAll;
import pl.coderslab.charity.security.repos.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends CustomBeforeAll {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Test
    public void createUser() throws Exception {
        final String username = "customtest@name.pl";

        mockMvc.perform(post("/create-user?username="+username+"&" +
                "password=abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"))
                .andReturn();

        assertNotNull(userRepository.findByUsername(username));

        userRepository.delete(userRepository.findByUsername(username));
    }

    @Test
    @WithUserDetails("test@test.pl")
    public void currentUserName() throws Exception {
        final String username = "test@test.pl";

        mockMvc.perform(get("/auth/username"))
                .andExpect(status().isOk())
                .andExpect(content().string(username))
                .andReturn();
    }
}
