package de.oose.gameservice.api;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import org.json.*;

import static de.oose.gameservice.gameservice_client.ClientApplication.api;

public class Api {
    private Socket socket;
    private DataOutputStream objectOutputStream;
    private DataInputStream objectInputStream;
    public Api(String hostname, int port) throws IOException {
        this.socket = new Socket(hostname, port);
        this.objectOutputStream = new DataOutputStream(socket.getOutputStream()); // (S)
        this.objectInputStream = new DataInputStream(socket.getInputStream()); //FUCKS SMTHING UP
    }

    public JSONObject updateWaitroom() {
        JSONObject response;
        try {
            JSONObject request = new JSONObject().put("command", "updateWaiting");
            response = sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public JSONObject updateGame() {
        JSONObject response;
        try {
            JSONObject request = new JSONObject().put("command", "updateGame");
            response = sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public boolean createGame(String username) throws Exception {
        JSONObject request = new JSONObject();
        request.put("command", "createRoom")
                .put("username", username);
        JSONObject response = null;
        try {
            response = sendRequest(request);
        } catch (SocketException e) {
            try {
                api = new Api("localhost", 8001);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (response.getString("status").equals("successful")) return true;
        throw new Exception(response.getString("status"));
    }

    public boolean joinGame(String gameID, String username) throws Exception {
        JSONObject request = new JSONObject();
        request.put("command", "joinRoom")
                .put("gameID", gameID)
                .put("username", username);
        JSONObject response = null;
        try {
            response = sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (response.getString("status").equals("successful")) return true;
        throw new Exception(response.getString("status"));
    }

    public boolean startGame() throws Exception {
        JSONObject response;
        try {
            JSONObject request = new JSONObject().put("command", "startGame");
            response = sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (response.getString("status").equals("successful")) return true;
        throw new Exception(response.getString("status"));
    }


    public boolean isGod() throws Exception {
        JSONObject response;
        try {
            JSONObject request = new JSONObject().put("command", "isGod");
            response = sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (response.getBoolean("isGod")) return true;
        if (!(response.getString("status").equals("successful"))) throw new Exception(response.getString("status"));
        return true;
    }
    public boolean isStarted() {
        JSONObject response;
        try {
            JSONObject request = new JSONObject().put("command", "isStarted");
            response = sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (response.getBoolean("isStarted")) return true;
        return false;
    }

    public int getFails() {
        JSONObject response;
        try {
            JSONObject request = new JSONObject().put("command", "updateGame");
            response = sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response.getInt("mistakesMade");
    }
    public void setWord(String text) {
        JSONObject request = new JSONObject().put("word", text)
                        .put("command", "setWord");
        try {
            sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
    }

    public boolean hasWord() {
        JSONObject response;
        JSONObject request = new JSONObject().put("command", "hasWord");
        try {
            response = sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response.getBoolean("hasWord");
    }

    public JSONObject sendRequest(JSONObject request) throws IOException, ClassNotFoundException {
        objectOutputStream.writeUTF(request.toString());
        return new JSONObject(objectInputStream.readUTF());
    }
}
