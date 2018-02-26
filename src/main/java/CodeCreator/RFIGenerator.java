package CodeCreator;


import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Random;

@Component
public class RFIGenerator implements ICodeCreator {

    @Override
    public String generateRandom() {
        Calendar calendar = Calendar.getInstance();
        Random randomGenerator = new Random();
        int randomLong = randomGenerator.nextInt(15);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RFI:");
        stringBuilder.append(calendar.getTimeInMillis()+randomLong);
        return stringBuilder.toString();
    }
}
