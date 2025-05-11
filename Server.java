import java.io.*;
import java.net.*;

public class Server {
    //Setup
    private static int port = 2222;
    static Socket clientSocket = null;
    static ServerSocket serverSocket = null;

    public static void main(String[] args) throws IOException {
        try {
            //Setup for user entry
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);

            System.out.println("This is the Server Side");
            System.out.println("Enter server port (default: 2222): ");

            //Reads users entered port number
            String userInput = br.readLine();

            //If the user enters a port number, it updates the default value
            if (!userInput.isEmpty()) {
                port = Integer.parseInt(userInput);
            }

            System.out.println("Server is waiting. Please enter data on the Client Side\n");

            //Initialises the Server Socket
            serverSocket = new ServerSocket(port);

            while (true) {
                //Accepts the clients connection
                clientSocket = serverSocket.accept();

                System.out.println("Client Request received...\n");

                //Starts the socket
                new ClientHelper(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
            //Closing sockets
            clientSocket.close();
            serverSocket.close();
        }
    }
}
