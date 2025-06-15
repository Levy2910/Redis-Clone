package day6_SimpleRedisClone;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class HandleRequest implements Runnable{
    private Socket clientSocket;
    private StoreRedis storeRedis;

    public HandleRequest(Socket clientSocket, StoreRedis storeRedis){
        this.clientSocket = clientSocket;
        this.storeRedis = storeRedis;
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
                if (line == null || (line.equals("bye"))) {
                    bufferedWriter.write("ok bye, see ya :3 "  + "\n");
                    break;
                }
                // extract line
                String[] extractedLine = line.toLowerCase().split(" ");
                if (!(extractedLine[0].equals("get") || extractedLine[0].equals("set") || extractedLine[0].equals("del"))){
                    bufferedWriter.write("invalid command "  + "\n");
                    bufferedWriter.flush();
                    continue;
                }
                if (extractedLine[0].equals("get") && extractedLine.length != 2){
                    bufferedWriter.write("invalid command "  + "\n");
                    bufferedWriter.flush();
                    continue;
                }
                if (extractedLine[0].equals("del") && extractedLine.length != 2){
                    bufferedWriter.write("invalid command "  + "\n");
                    bufferedWriter.flush();
                    continue;
                }
                if (extractedLine[0].equals("set") && extractedLine.length != 3){
                    bufferedWriter.write("invalid command " + "\n");
                    bufferedWriter.flush();
                    continue;
                }

                if(extractedLine[0].equals("get")){
                    if (storeRedis.get(extractedLine[1]) != null){
                        bufferedWriter.write("the value from key " + extractedLine[1] + "is :" + storeRedis.get(extractedLine[1]) + "\n");
                    }else{
                        bufferedWriter.write("key not exist "  + "\n");
                    }
                }
                if(extractedLine[0].equals("del")){
                    if (storeRedis.get(extractedLine[1]) != null){
                        storeRedis.del(extractedLine[1]);
                        bufferedWriter.write("deleted key " + extractedLine[1] + "\n");
                    }else{
                        bufferedWriter.write("key not exist "  + "\n");
                    }
                }
                if (extractedLine[0].equals("set")){
                    storeRedis.set(extractedLine[1], extractedLine[2]);
                    bufferedWriter.write("added key "+ storeRedis.get(extractedLine[1])  + "\n");
                }

                bufferedWriter.flush();
            }

            clientSocket.close();
            bufferedReader.close();
            bufferedWriter.close();
        }catch (IOException e){

        }

    }
}

