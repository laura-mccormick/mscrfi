package CodeCreator;

import email.EmailSetup;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RFIGenerator.class)
public class TestRFIGenerator {

    @Autowired
    RFIGenerator rfiGenerator;

    @Test
    public void testThatTheRFIGeneratorCreatesACode15CharsLong(){
        String testRFI = rfiGenerator.generateRandom();
        Assert.assertEquals(testRFI.length(),17);
    }

    @Test
    public void testThatMultipleGeneraterdRFICodesAreUnique(){
        String testRFIOne = rfiGenerator.generateRandom();
        String testRFITwo = rfiGenerator.generateRandom();
        String testRFIThree = rfiGenerator.generateRandom();
        Assert.assertNotEquals(testRFIOne,testRFITwo);
        Assert.assertNotEquals(testRFIOne,testRFIThree);
        Assert.assertNotEquals(testRFITwo,testRFIThree);
    }


}
