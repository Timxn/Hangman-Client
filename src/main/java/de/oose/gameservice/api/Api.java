package de.oose.gameservice.api;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Api {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    public Api(String hostname, int port) {
        try {
            this.socket = new Socket(hostname, port);
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream()); // (S)
            this.objectInputStream = new ObjectInputStream(socket.getInputStream()); //FUCKS SMTHING UP
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String sendRequest(String command, String payload) throws IOException, ClassNotFoundException {
        Message request = new Message(command,payload);
        objectOutputStream.writeObject(request);
        Message response = (Message) objectInputStream.readObject();
        return response.getPayload();
    }
}
