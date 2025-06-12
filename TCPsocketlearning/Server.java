public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(3003);
    System.out.println("Server started on port 3003");

    while (true) {
        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

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
                bufferedWriter.write("ok received\n");
                bufferedWriter.flush();
            }

            clientSocket.close();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
