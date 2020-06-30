package pl.coderslab.charity.controllers.mail;

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
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.entities.VerificationToken;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;
import pl.coderslab.charity.repos.VerificationTokenRepository;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.repos.UserRepository;

import javax.annotation.Resource;
import javax.mail.Message;
import java.util.Arrays;
import java.util.Calendar;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SendMessageControllerTest {
    @Resource
    private JavaMailSenderImpl emailSender;
    private GreenMail testSmtp;
    @Autowired
    private MockMvc mockMvc;

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
    public void testSmtpInit() {
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();

        emailSender.setPort(3025);
        emailSender.setHost("localhost");
    }

    @Test
    public void doSendMsg() throws Exception {
        final String name = "Kuba";
        final String surname = "Wasilewski";
        final String message = "test message content";

        mockMvc.perform(post("/msg?name="+name+"&" +
                "surname="+surname+"&message="+message))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/"))
                .andReturn();

        Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals(name+" "+surname, messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals(message, body);
    }
}
