package MultipleClientsHandling;

import java.io.*;
import java.net.Socket;

public class LetMeHandleThisClient implements Runnable{
    private Socket clientSocket;

    public LetMeHandleThisClient(Socket clientSocket){
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            while (true) {
                String line = bufferedReader.readLine();
                if (line == null || line.equals("bye")) {
                    break;
                }
                System.out.println("server: " + line);
                bufferedWriter.write("ok received from client" + clientSocket.getPort() +"\n");

                bufferedWriter.flush();
            }

            clientSocket.close();
            bufferedReader.close();
            bufferedWriter.close();
        }catch (IOException e){

        }

    }
}
