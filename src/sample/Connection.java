package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {

    public static void makeConnection(String day, String month, int year) throws IOException {

        Socket clientSocket = new Socket("127.0.0.1", 1065);
        String clientMessage = day + " " + month + " " + year;
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        writer.println(clientMessage);
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String serverMessage = reader.readLine();
        System.out.println(serverMessage);

    }
}
