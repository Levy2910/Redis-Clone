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
                    bufferedWriter.flush();
                    break;
                }
                // extract line
                String[] extractedLine = line.toLowerCase().split(" ");

                String command = extractedLine[0];
                String key;
                switch (command){
                    case "get":
                        if (extractedLine.length != 2){
                            bufferedWriter.write("invalid command " + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        key = extractedLine[1];
                        if (storeRedis.get(key) == null){
                            bufferedWriter.write("there is no such key" + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        bufferedWriter.write("value: " + storeRedis.get(key) + "\n");
                        bufferedWriter.flush();
                        continue;
                    case "del":
                        if (extractedLine.length != 2){
                            bufferedWriter.write("invalid command " + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        key = extractedLine[1];
                        if (storeRedis.get(key) == null){
                            bufferedWriter.write("there is no such key " + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        storeRedis.del(key);
                        bufferedWriter.write("already deleted key " +"\n");
                        bufferedWriter.flush();
                        continue;
                    case "set":
                        if (extractedLine.length != 3){
                            bufferedWriter.write("invalid command " + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        key = extractedLine[1];
                        storeRedis.set(key, extractedLine[2]);
                        bufferedWriter.write("already added key "+ "\n");
                        bufferedWriter.flush();
                        continue;
                    default:
                        bufferedWriter.write("invalid command "+ "\n");
                        bufferedWriter.flush();
                }
            }

            clientSocket.close();
            bufferedReader.close();
            bufferedWriter.close();
        }catch (IOException e){

        }

    }
}

