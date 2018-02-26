package converter;



import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileConverter {

//    public static void main(String [] args) throws IOException {
//        File fileRaw = new File("C:\\Users\\jt65062\\Documents\\Disser\\msc_rfi\\BackendTransfer\\src\\main\\resources\\test.txt");
//        System.out.println(fileToStringConverter(fileRaw));
//
//        String fileName = fileRaw.getName();
//        byte[] test = fileToByteArrayConverter(fileRaw);
//
//        File fileTransformed = byteArrayToFile(test, fileName);
//
//        System.out.println(fileTransformed.getName());
//        System.out.println(fileTransformed.getAbsolutePath());
//        System.out.println(fileTransformed.toString());
//
//        String fileContents = fileToStringConverter(fileTransformed);
//        System.out.println(fileContents.toString());
//        System.out.println(fileContents);
//    }

    public byte[] fileToByteArrayConverter(File file) throws IOException {

        InputStream input = new FileInputStream(file);

        byte[] filesAsBytes = IOUtils.toByteArray(input);

        return filesAsBytes;


    }

    public File byteArrayToFile(byte [] byteArray, String fileName) throws IOException {

        File file = new File(fileName);

        FileUtils.writeByteArrayToFile(file, byteArray);

        return file;

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
