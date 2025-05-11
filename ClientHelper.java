import java.io.*;
import java.net.Socket;
import java.util.Objects;

class ClientHelper extends Thread {
    private final Socket clientSocket;
    BufferedReader reader = null;
    PrintWriter writer = null;

    public ClientHelper(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            //Setup
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            writer = new PrintWriter(outputStream, true);

            String clientMessage;

            while (true) {
                clientMessage = reader.readLine();

                //Checks if the exit message has been received and exits the loop if true
                if (Objects.equals(clientMessage, "exit")) {
                    System.out.println("Exit request received. Closing server...");
                    break;
                }

                //Prints the received names from the client
                System.out.println("Message received: " + clientMessage + "\nModifying...\n");

                //Modifies the names
                String modifiedMessage = "Hello " + clientMessage.toUpperCase();

                //Writes the names
                writer.println(modifiedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                clientSocket.close();

                //Closes the Server
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
