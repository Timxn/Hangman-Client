package de.oose.gameservice.api;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

import static de.oose.gameservice.gameservice_client.ClientApplication.api;

public class Api {
    public String username;
    public String gameid;
    private Socket socket;
    private DataOutputStream objectOutputStream;
    private DataInputStream objectInputStream;
    public Api(String hostname, int port) throws IOException {
        this.socket = new Socket(hostname, port);
        this.objectOutputStream = new DataOutputStream(socket.getOutputStream());
        this.objectInputStream = new DataInputStream(socket.getInputStream());
    }

    public JSONObject updateLobby() throws Exception {
        JSONObject response;
        try {
            JSONObject request = new JSONObject().put("command", "updateWaiting");
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!(response.getString("status").equals("successful"))) throw new Exception(response.getString("status"));
        gameid = response.getString("gameID");
        return response;
    }

    public JSONObject updateGame() {
        JSONObject response;
        try {
            JSONObject request = new JSONObject().put("command", "updateGame");
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
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
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (response == null) throw new Exception("no Connection possible");
        if (!(response.getString("status").equals("successful"))) throw new Exception(response.getString("status"));
        this.username = username;
        return true;
    }

    public boolean joinGame(String gameID, String username) throws Exception {
        JSONObject request = new JSONObject();
        request.put("command", "joinRoom")
                .put("gameID", gameID)
                .put("username", username);
        JSONObject response;
        try {
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!(response.getString("status").equals("successful"))) throw new Exception(response.getString("status"));
        if (response.getString("status").equals("successful")) {
            this.username = username;
            return true;
        }
        return false;
    }

    public void startGame() throws Exception {
        JSONObject response;
        try {
            JSONObject request = new JSONObject().put("command", "startGame");
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!(response.getString("status").equals("successful"))) throw new Exception(response.getString("status"));
    }


    public boolean isGod() throws Exception {
        JSONObject response;
        try {
            JSONObject request = new JSONObject().put("command", "isGod");
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!(response.getString("status").equals("successful"))) throw new Exception(response.getString("status"));
        if (response.getBoolean("isGod")) return true;
        return false;
    }
    public void setWord(String text) throws Exception {
        JSONObject response;
        JSONObject request = new JSONObject().put("word", text)
                .put("command", "setWord");
        try {
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        throw new Exception(response.getString("status"));
    }

    public boolean isStarted() throws Exception {
        JSONObject response;
        try {
            JSONObject request = new JSONObject().put("command", "isStarted");
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!(response.getString("status").equals("successful"))) throw new Exception(response.getString("status"));
        if (response.getBoolean("isStarted")) return true;
        return false;
    }
    public boolean hasWord() throws Exception {
        JSONObject response;
        JSONObject request = new JSONObject().put("command", "hasWord");
        try {
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!(response.getString("status").equals("successful"))) throw new Exception(response.getString("status"));
        return response.getBoolean("hasWord");
    }

    public void guessLetter(String text) throws Exception {
        JSONObject response;
        try {
            JSONObject request = new JSONObject()
                    .put("command", "guess")
                    .put("character", text);
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!(response.getString("status").equals("successful"))) throw new Exception("Someone couldn't guess ain't it? (server error)");

    }


    public String getWinner() throws Exception {
        JSONObject response;
        try {
            JSONObject request = new JSONObject()
                    .put("command", "getWinner");
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!(response.getString("status").equals("successful"))) throw new Exception("Could not leave match, please contact god!");
        return response.getString("winner");
    }
    public void leaveGame() throws Exception {
        JSONObject response;
        try {
            JSONObject request = new JSONObject()
                    .put("command", "quitGame");
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!(response.getString("status").equals("successful"))) throw new Exception("Could not leave match, please contact god!");
        this.username = null;
        this.gameid = null;
    }
    public void close() throws Exception {
        JSONObject response;
        JSONObject request = new JSONObject().put("command", "close");
        try {
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception(e);
        }
        if (!(response.getString("status").equals("successful"))) throw new Exception(response.getString("status"));
    }

    public List<String> getScoreboard() throws Exception {
        JSONObject response;
        try {
            JSONObject request = new JSONObject().put("command", "getScoreboard");
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception(e);
        }
        List<String> tmp = new ArrayList<>();
        try {
            for (Object user:response.getJSONArray("scoreboard").toList()) {
                tmp.add(user.toString());
            }
        } catch (JSONException e) {
            throw new Exception(e);
        }
        return tmp;
    }

    public String getGameid() {
        JSONObject response;
        JSONObject request = new JSONObject().put("command", "getGameID");
        try {
            response = sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response.getString("gameid");
    }

    public JSONObject sendRequest(JSONObject request) throws IOException, ClassNotFoundException {
        objectOutputStream.writeUTF(request.toString());
        return new JSONObject(objectInputStream.readUTF());
    }
}
