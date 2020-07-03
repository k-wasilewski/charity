package pl.coderslab.charity.controllers.admin;

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
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.extensions.CustomBeforeAll;
import pl.coderslab.charity.security.repos.UserRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstitutionsControllerTest extends CustomBeforeAll {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testA_institsView() throws Exception {
        mockMvc.perform(get("/admin/institutions"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-institutions"))
                .andExpect(model().attribute("institutions", hasSize(4)))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testB_editView() throws Exception {
        mockMvc.perform(get("/admin/institutions/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-institution-edit"))
                .andExpect(model().attribute("inst",
                        hasProperty("name", is("Dbam o Zdrowie"))))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testC_edit() throws Exception {
        Institution instit = new Institution();
        instit.setName("Test institution");
        instit.setId(5);

        mockMvc.perform(post("/admin/instututions/edit")
                .flashAttr("inst", instit))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-institutions"))
                .andExpect(model().attribute("institutions",
                        hasItem(hasProperty("name", equalTo("Test institution")))))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testD_del() throws Exception {
        mockMvc.perform(get("/admin/institutions/del?id=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-institutions"))
                .andExpect(model().attribute("institutions",
                        not(hasItem(hasProperty("name", equalTo("Test institution"))))))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin1@admin.pl")
    public void testE_add() throws Exception {
        mockMvc.perform(get("/admin/institutions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-institution-edit"))
                .andExpect(model().attribute("add", true))
                .andExpect(model().attribute("inst", notNullValue()))
                .andExpect(model().attribute("username",
                        userRepository.findByUsername("admin1@admin.pl").getUsername()))
                .andReturn();
    }
}
