package converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileConverter {

    public static Map<String, String> run() {

        return filesToStringsToMap(fillFileArray());
    }


    public static Map<String, String> filesToStringsToMap(File[] files){

        Map<String, String> map = new HashMap<String, String>();

        for(int loop = 0; loop < files.length; loop++){

            map.put(files[loop].getName(), new String(fileToStringConverter(files[loop])));

        }

        return map;

    }


    public static File[] fillFileArray() {


        File file1 = new File("BackendTransfer/src/main/resources/laura/alertMain.css");
        File file2 = new File("BackendTransfer/src/main/resources/laura/alertList.html");
        File file3 = new File("BackendTransfer/src/main/resources/laura/index.html");
        File file4 = new File("BackendTransfer/src/main/resources/laura/conversation.html");
        File file5 = new File("BackendTransfer/src/main/resources/laura/newMessage.html");
        File file6 = new File("BackendTransfer/src/main/resources/laura/viewMessage.html");
        File file7 = new File("BackendTransfer/src/main/resources/laura/alert.js");

        File[] files = {file1,file2,file3,file4,file5,file6,file7};

        return files;



    }


    public static String fileToStringConverter(File file) {


        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }

        String content = contentBuilder.toString();

        return content;

    }
}
