package pl.coderslab.charity.controllers.login_register;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.charity.entities.VerificationToken;
import pl.coderslab.charity.extensions.CustomBeforeAll;
import pl.coderslab.charity.repos.VerificationTokenRepository;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.repos.UserRepository;

import javax.annotation.Resource;
import javax.mail.Message;
import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterControllerTest extends CustomBeforeAll {
    @Resource
    private JavaMailSenderImpl emailSender;
    private GreenMail testSmtp;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VerificationTokenRepository tokenRepository;
    static VerificationToken verificationToken;

    @Test
    public void testA_registerView() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("login_register/register"))
                .andReturn();
    }

    @Test
    public void testB_doRegister() throws Exception {
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();
        emailSender.setPort(3025);
        emailSender.setHost("localhost");

        final String subject = "\"Pass in good hands\" - your activation link";
        final String messagePart = "\"Pass in good hands\" - this is your activation link: ";
        final String email = "test@name.com";

        mockMvc.perform(post("/register?username="+email+"&" +
                "password=Abc$54gdd&password2=Abc$54gdd"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("msg8", true))
                .andReturn();

        User user = userRepository.findByUsername(email);
        verificationToken = tokenRepository.findByUser(user);
        String token = verificationToken.getToken();
        String confirmationUrl
                = "/regitrationConfirm?token=" + token;
        String actualMessage = messagePart + "http://localhost:8081" + confirmationUrl;

        assertNotNull(userRepository.findByUsername(email));
        assertEquals(1, userRepository.findByUsername(email).getBlocked());
        assertEquals(0, userRepository.findByUsername(email).getEnabled());

        Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals(subject, messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals(actualMessage, body);

        testSmtp.stop();
    }

    @Test
    public void testC_confirmRegistration() throws Exception {
        String token = verificationToken.getToken();
        String username = verificationToken.getUser().getUsername();
        Calendar cal = Calendar.getInstance();

        mockMvc.perform(get("/regitrationConfirm?token="+token))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andReturn();

        assertEquals(1, userRepository.findByUsername(username).getEnabled());
        assertEquals(0, userRepository.findByUsername(username).getBlocked());
        assertNotNull(verificationToken);
        assertTrue((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime())>0);

        userRepository.delete(userRepository.findByUsername(username));
    }
}
