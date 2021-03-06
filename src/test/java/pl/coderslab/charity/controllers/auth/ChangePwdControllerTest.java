package pl.coderslab.charity.controllers.auth;

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
import pl.coderslab.charity.extensions.CustomBeforeAll;
import pl.coderslab.charity.security.repos.UserRepository;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChangePwdControllerTest extends CustomBeforeAll {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Test
    @WithUserDetails("test@test.pl")
    public void testA_changePwdView() throws Exception {
        mockMvc.perform(get("/auth/changePwd"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/changePwd"))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("test@test.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("test@test.pl")
    public void testB_actualChangePwd() throws Exception {
        String oldPwd = userRepository.findByUsername("test@test.pl").getPassword();

        mockMvc.perform(post("/auth/changePwd?old-pwd=test&new-pwd=new&new-pwd2=new"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("test@test.pl").getUsername()))
                .andReturn();

        assertNotEquals(oldPwd, userRepository.findByUsername("test@test.pl").getPassword());

        mockMvc.perform(post("/auth/changePwd?old-pwd=new&new-pwd=test&new-pwd2=test"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
