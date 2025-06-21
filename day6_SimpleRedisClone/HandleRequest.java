package day6_SimpleRedisClone;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

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
                    case "incr":
                        if (extractedLine.length != 2){
                            bufferedWriter.write("invalid command " + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        key = extractedLine[1];
                        try {
                            int value = storeRedis.incr(key);
                            bufferedWriter.write("value: " + value + "\n");
                            bufferedWriter.flush();
                        } catch (Exception e) {
                            bufferedWriter.write("error: " + e.getMessage() + "\n");
                            bufferedWriter.flush();
                        }
                        continue;
                    case "decr":
                        if (extractedLine.length != 2){
                            bufferedWriter.write("invalid command " + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        key = extractedLine[1];
                        try {
                            int value = storeRedis.decr(key);
                            bufferedWriter.write("value: " + value + "\n");
                            bufferedWriter.flush();
                        } catch (Exception e) {
                            bufferedWriter.write("error: " + e.getMessage() + "\n");
                            bufferedWriter.flush();
                        }
                        continue;
                    case "exists":
                        if (extractedLine.length != 2){
                            bufferedWriter.write("invalid command " + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        key = extractedLine[1];
                        if (storeRedis.exist(key)){
                            bufferedWriter.write("key exists" + "\n");
                        } else {
                            bufferedWriter.write("key does not exist" + "\n");
                        }
                        bufferedWriter.flush();
                        continue;
                    case "mset":
                        if (extractedLine.length < 3 || extractedLine.length % 2 != 1){
                            bufferedWriter.write("invalid command " + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        HashMap<String, String> map = new HashMap<>();
                        for (int i = 1; i < extractedLine.length; i += 2) {
                            map.put(extractedLine[i], extractedLine[i + 1]);
                        }
                        String result = storeRedis.mSet(map);
                        bufferedWriter.write(result + "\n");
                        bufferedWriter.flush();
                        continue;
                    case "mget":
                        if (extractedLine.length < 2){
                            bufferedWriter.write("invalid command " + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        String[] keys = Arrays.copyOfRange(extractedLine, 1, extractedLine.length);
                        String mgetResult = storeRedis.mGet(keys);
                        bufferedWriter.write(mgetResult + "\n");
                        bufferedWriter.flush();
                        continue;
                    case "flushall":
                        if (extractedLine.length != 1){
                            bufferedWriter.write("invalid command " + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        storeRedis.deleteAll();
                        bufferedWriter.write("already deleted all " + "\n");
                        bufferedWriter.flush();
                        continue;
                    case "expire":
                        if (extractedLine.length != 3){
                            bufferedWriter.write("invalid command " + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        key = extractedLine[1];
                        long time = Long.parseLong(extractedLine[2]);
                        storeRedis.setExpiryDate(key, time);
                        bufferedWriter.write("already set the time up " + "\n");
                        bufferedWriter.flush();
                        continue;
                    case "ttl":
                        if (extractedLine.length != 2){
                            bufferedWriter.write("invalid command " + "\n");
                            bufferedWriter.flush();
                            continue;
                        }
                        key = extractedLine[1];
                        long resultTTL = storeRedis.checkTTL(key);
                        bufferedWriter.write("result :" + resultTTL + "\n");
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




