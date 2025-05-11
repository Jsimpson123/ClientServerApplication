import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Client {
    public static void main(String[] args) throws IOException {

        //Student names ArrayList
        ArrayList<String> studentNames = new ArrayList<>();
        studentNames.add("Michael");
        studentNames.add("Calum");
        studentNames.add("Justin");
        studentNames.add("Conor");

        //Setup for user entry
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        Socket socket = null;

        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            System.out.println("This is the Client Side. Please enter the port number on the server side.\n" +
                    "The default host name is localhost.");

            //Reads user input
            final String HOSTNAME = "localhost";

            System.out.println("\nEnter the port number of the server host (default: 2222):");

            //Reads user input
            String portNum = br.readLine();

            //Automatically enters 2222 if nothing entered by user
            if (portNum.isEmpty()) {
                portNum = "2222";
                System.out.println(portNum + " chosen.");
            }

            //Converts the entered port number to an integer
            int port = Integer.parseInt(portNum);

            try {
                //Initialises the socket
                socket = new Socket(HOSTNAME, port);
                System.out.println("\nConnected to server at " + HOSTNAME + ":" + port + "\n");
            } catch (Exception e) {
                System.out.println("Incorrect port number");
            }

            //Further setup for receiving/sending data
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            writer = new PrintWriter(outputStream, true);

            //Writes to the server for each student and receives the result after
            for (int i = 0; i < studentNames.size(); i++) {
                String student = studentNames.get(i);
                writer.println(student);

                String response = reader.readLine();
                System.out.println("Received from server: " + response);
            }

            //Notifies server to exit and close connection
            writer.println("exit");
            System.out.println("\nEnd of names. Client closing...");

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //Assertations and closing of connections
            assert reader != null;
            reader.close();

            assert writer != null;
            writer.close();

            socket.close();
        }
    }
}
