package objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AlertDTO.class)
@TestPropertySource("classpath:test.properties")
public class AlertDTOTest {

    @Autowired
    private AlertDTO alert;

    @Autowired
    private Environment resources;

    @Test
    public void testAlertDefaultConstructor() {

        AlertDTO alertTest = new AlertDTO();

        assertNotNull(alertTest);

    }

    @Test
    public void testAlertConstructorWithArgs() {

        AlertDTO alertTest = new AlertDTO("Alert1", "Desc1", true);

        assertNotNull(alertTest.getId(), alertTest.getDesc());
        assertEquals(alertTest.isHasRfi(), true);
        assertNotNull(alertTest);
    }



    @Test
    public void testAlertIDGetterAndSetter() {

        String expected = "Alert1";

        alert.setId(expected);

        String actual = alert.getId();

        assertEquals(actual, expected);
    }

    @Test
    public void testDescGetterAndSetter() {

        String expected = "Desc1";

        alert.setDesc(expected);

        String actual = alert.getDesc();

        assertEquals(actual, expected);
    }

    @Test
    public void testHasRfiGetterAndSetter() {

        boolean expected = true;

        alert.setHasRfi(expected);

        boolean actual = alert.isHasRfi();

        assertEquals(actual, expected);
    }
}
