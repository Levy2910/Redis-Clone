import day5_threadRunaable.LetMeHandleThisClient;
import day6_SimpleRedisClone.HandleRequest;
import day6_SimpleRedisClone.StoreRedis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(3003);
    System.out.println("Server started on port 3003");
    StoreRedis storeRedis = new StoreRedis();
    while (true) {
        try {
            Socket clientSocket = serverSocket.accept();
            HandleRequest handleRequest = new HandleRequest(clientSocket, storeRedis);
            Thread newThread = new Thread(handleRequest);
            newThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
