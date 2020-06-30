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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.controllers.instit.DonationsController;
import pl.coderslab.charity.entities.Category;
import pl.coderslab.charity.entities.Donation;
import pl.coderslab.charity.entities.VerificationToken;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;
import pl.coderslab.charity.repos.CategoryRepository;
import pl.coderslab.charity.repos.DonationRepository;
import pl.coderslab.charity.repos.InstitutionRepository;
import pl.coderslab.charity.repos.VerificationTokenRepository;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.repos.UserRepository;
import pl.coderslab.charity.security.services.UserService;

import javax.annotation.Resource;
import javax.mail.Message;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ForgottenPasswordControllerTest {
    @Resource
    private JavaMailSenderImpl emailSender;
    private GreenMail testSmtp;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VerificationTokenRepository tokenRepository;
    @Autowired
    UserService userService;
    static boolean setup = false;
    static VerificationToken verificationToken;

    @ClassRule
    public static KafkaContainer kafkaContainer = new KafkaContainer();

    @BeforeClass
    public static void setKafkaContainerName() {
        kafkaContainer.setNetworkAliases(Arrays.asList("kafka"));
        KafkaConsumerConfig.setUrl(kafkaContainer.getBootstrapServers());
        KafkaProducerConfig.setUrl(kafkaContainer.getBootstrapServers());
        KafkaTopicConfig.setUrl(kafkaContainer.getBootstrapServers());
    }

    @Before
    public void testSmtpInit(){
        if (!setup) {
            testSmtp = new GreenMail(ServerSetupTest.SMTP);
            testSmtp.start();

            emailSender.setPort(3025);
            emailSender.setHost("localhost");

            setup = true;
        }
    }

    @Test
    public void testA_sendEmail() throws Exception {
        final String subject = "\"Oddam w dobre ręce\" - Twój link do zmiany hasła";
        final String email = "test@test.pl";
        User user = userRepository.findByUsername(email);

        mockMvc.perform(post("/forgotten?email="+email))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("msg8", true))
                .andReturn();

        verificationToken = tokenRepository.findByUser(user);
        String token = verificationToken.getToken();
        String link = "http://localhost:8081" + "/forgottenConfirm?token=" + token;

        Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals(subject, messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals(link, body);
    }

    @Test
    public void testB_changePwd() throws Exception {
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        mockMvc.perform(get("/forgottenConfirm?token=" +
                verificationToken.getToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("login_register/changePwd"))
                .andExpect(model().attribute("username", user.getUsername()))
                .andReturn();

        assertNotNull(verificationToken);
        assertTrue((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime())>0);
    }

    @Test
    public void testC_doChangePwd() throws Exception {
        User user = userRepository.findByUsername("test@test.pl");
        String oldPwd = user.getPassword();

        mockMvc.perform(post("/changePassword?username=test@test.pl&" +
                "password=Abc^g54ss&password2=Abc^g54ss"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andReturn();

        assertNotEquals(oldPwd,
                userRepository.findByUsername("test@test.pl").getPassword());
    }
}
