package Shared;

import java.io.Serializable;

public class Message implements Serializable {
    private final String from;
    private final String type;
    private final String content;

    public Message(String from, String type, String content) {
        this.from = from;
        this.type = type;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
