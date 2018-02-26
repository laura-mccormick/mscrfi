package objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ConversationDTO.class)
@TestPropertySource("classpath:test.properties")
public class ConversationDTOTest {

    @Autowired
    private ConversationDTO conversation;

    @Test
    public void testConversationDefaultConstructor() {

        ConversationDTO conversationTest = new ConversationDTO();

        assertNotNull(conversationTest);

    }

    @Test
    public void testConversationConstructorWithArgs() {

        ConversationDTO conversationTest = new ConversationDTO("Alert1", "Conversation1", "Subject1", "Sender1", "Recipient1", true, "yesterday", "today");

        assertNotNull(conversationTest.getAlertId(), conversationTest.getConversationId());
        assertNotNull(conversationTest.getSender(), conversationTest.getRecipient());
        assertNotNull(conversationTest.getDateClosed(), conversationTest.getDateOpened());
        assertEquals(conversationTest.isOpen(), true);
        assertNotNull(conversationTest);
    }



    @Test
    public void testAlertIDGetterAndSetter() {

        String expected = "Alert1";

        conversation.setAlertId(expected);

        String actual = conversation.getAlertId();

        assertEquals(actual, expected);
    }

    @Test
    public void testConversationIDGetterAndSetter() {

        String expected = "Conversation1";

        conversation.setConversationId(expected);

        String actual = conversation.getConversationId();

        assertEquals(actual, expected);
    }

    @Test
    public void testSubjectGetterAndSetter() {

        String expected = "Subject1";

        conversation.setSubject(expected);

        String actual = conversation.getSubject();

        assertEquals(actual, expected);
    }

    @Test
    public void testSenderGetterAndSetter() {

        String expected = "Sender1";

        conversation.setSender(expected);

        String actual = conversation.getSender();

        assertEquals(actual, expected);
    }

    @Test
    public void testRecipientGetterAndSetter() {

        String expected = "Recipient1";

        conversation.setRecipient(expected);

        String actual = conversation.getRecipient();

        assertEquals(actual, expected);
    }

    @Test
    public void testOpenGetterAndSetter() {

        boolean expected = true;

        conversation.setOpen(expected);

        boolean actual = conversation.isOpen();

        assertEquals(actual, expected);
    }

    @Test
    public void testDateOpenedGetterAndSetter() {

        String expected = "Yesterday1";

        conversation.setDateOpened(expected);

        String actual = conversation.getDateOpened();

        assertEquals(actual, expected);
    }

    @Test
    public void testDateClosedGetterAndSetter() {

        String expected = "Today1";

        conversation.setDateClosed(expected);

        String actual = conversation.getDateClosed();

        assertEquals(actual, expected);
    }


}
