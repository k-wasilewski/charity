package pl.coderslab.charity.controllers.mail;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.charity.extensions.CustomBeforeAll;

import javax.annotation.Resource;
import javax.mail.Message;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SendMessageControllerTest extends CustomBeforeAll {
    @Resource
    private JavaMailSenderImpl emailSender;
    private GreenMail testSmtp;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void testSmtpInit() {
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();

        emailSender.setPort(3025);
        emailSender.setHost("localhost");
    }

    @After
    public void testSmtpDestr() {
        testSmtp.stop();
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
