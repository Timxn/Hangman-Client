package de.oose.gameservice.api;

import java.io.Serializable;

public class Message implements Serializable {
    private String command;
    private String payload;

    @Override
    public String toString() {
        return "Message{" +
                "key='" + command + '\'' +
                ", value='" + payload + '\'' +
                '}';
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Message(String key, String value) {
        this.command = key;
        this.payload = value;
    }
}

