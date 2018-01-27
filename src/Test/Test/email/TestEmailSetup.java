package email;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.Session;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = EmailSetup.class)
@TestPropertySource("classpath:test.properties")
public class TestEmailSetup {

    @Autowired
    private EmailSetup emailSetup;

    @Autowired
    private Environment resources;

    private GreenMail greenMail;
    private GreenMailUser testUser;

    @Before
    public void setUp (){
        greenMail = new GreenMail(ServerSetup.SMTP);
        testUser = greenMail.setUser(resources.getProperty("mail.username"), resources.getProperty("mail.username"),resources.getProperty("mail.password"));
        greenMail.start();

    }

    @Test
    public void testSendEmail() throws MessagingException {
        emailSetup.sendEmail(resources.getProperty("mail.username"),"testSubject","testMessageContent",null);
        Assert.assertEquals(greenMail.getReceivedMessages().length,1);
    }

    @Test
    public void testThatASendingSessionIsCreated(){
        Session session = emailSetup.getSmtpSessionForSendingMessages();
        Assert.assertNotNull(session);
    }

    @After
    public void cleanUp(){
        greenMail.stop();
    }
}
