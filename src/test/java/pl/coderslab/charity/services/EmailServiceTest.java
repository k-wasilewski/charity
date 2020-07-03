package pl.coderslab.charity.services;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.charity.extensions.CustomBeforeAll;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest extends CustomBeforeAll {
    @Resource
    private JavaMailSenderImpl emailSender;
    private GreenMail testSmtp;

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
    public void sendSimpleMessage() throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("test@sender.com");
        message.setTo("test@receiver.com");
        message.setSubject("test subject");
        message.setText("test message");
        emailSender.send(message);

        Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("test subject", messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals("test message", body);
    }

    @After
    public void cleanup(){
        testSmtp.stop();
    }
}
