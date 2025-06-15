import day5_threadRunaable.LetMeHandleThisClient;

public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(3003);
    System.out.println("Server started on port 3003");

    while (true) {
        try {
            Socket clientSocket = serverSocket.accept();
            LetMeHandleThisClient letMeHandleThisClient = new LetMeHandleThisClient(clientSocket);
            Thread newThread = new Thread(letMeHandleThisClient);
            newThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
