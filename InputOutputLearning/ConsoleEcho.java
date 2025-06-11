package InputOutputLearning;

import java.io.*;

public class ConsoleEcho {

   public void getContentFromFile(String inputPath, String outputPath) throws FileNotFoundException {
       try (InputStream input = new FileInputStream(new File(inputPath));
            OutputStream output = new FileOutputStream(new File(outputPath), true);) {

           int byteRead;
           while ((byteRead = input.read()) != -1) {
               output.write(byteRead);
           }

       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }
}
