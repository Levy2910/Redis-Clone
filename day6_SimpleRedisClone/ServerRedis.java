import day5_threadRunaable.LetMeHandleThisClient;
import day6_SimpleRedisClone.AOFHandler;
import day6_SimpleRedisClone.ExpiryThread;
import day6_SimpleRedisClone.HandleRequest;
import day6_SimpleRedisClone.StoreRedis;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(3003);
    System.out.println("Server started on port 3003");
    StoreRedis storeRedis = new StoreRedis();
    AOFHandler aofHandler = new AOFHandler();
    replay(storeRedis);
    ExpiryThread expiryThread = new ExpiryThread(storeRedis, aofHandler);
    expiryThread.start();

    Runtime.getRuntime().addShutdownHook(new Thread(expiryThread::shutdown));

    while (true) {
        try {
            Socket clientSocket = serverSocket.accept();
            HandleRequest handleRequest = new HandleRequest(clientSocket, storeRedis, aofHandler);
            Thread newThread = new Thread(handleRequest);
            newThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

private static void replay(StoreRedis storeRedis) {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data.aof"))){
        String line;
        while( (line = bufferedReader.readLine()) != null){
            String[] extractedLine = line.split(" ");
            String command = extractedLine[0];
            if (command.equals("set")){
                 storeRedis.set(extractedLine[1], extractedLine[2]);
            }else if (command.equals("del")){
                storeRedis.del(extractedLine[1]);
            }else if (command.equals("incr")){
                storeRedis.incr(extractedLine[1]);
            }else if (command.equals("decr")){
                storeRedis.decr(extractedLine[1]);
            }else if (command.equals("mset")){
                HashMap<String, String> map = new HashMap<>();
                for (int i = 1; i < extractedLine.length; i += 2) {
                    map.put(extractedLine[i], extractedLine[i + 1]);
                }
                storeRedis.mSet(map);
            }else if (command.equals("flushall")){
                storeRedis.deleteAll();
            }else if (command.equals("expire")){
                storeRedis.setExpiryDate(extractedLine[1], Long.parseLong(extractedLine[2]));
            }
        }
    }catch (FileNotFoundException e){

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
