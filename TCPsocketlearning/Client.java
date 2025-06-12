import java.io.*;
import java.net.Socket;
import java.util.Scanner;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 3003);
                Scanner scanner = new Scanner(System.in);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            System.out.println("Connected to server at port 3003");

            while (true) {
                System.out.print("You: ");
                String input = scanner.nextLine();
                bufferedWriter.write(input + "\n");
                bufferedWriter.flush();

                String response = bufferedReader.readLine();
                System.out.println("Server: " + response);

                if ("bye".equalsIgnoreCase(input)) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
