package Shared;

import java.io.Serializable;

public class Message implements Serializable
{
    private String sender;
    private String type;       // CHAT, LOGIN, UPLOAD, DOWNLOAD, etc.
    private String content;    // main message or file name
    private byte[] fileData;   // optional for file transfer
    // Constructors
    public Message(String sender, String type, String content)
    {
        this.sender = sender;
        this.type = type;
        this.content = content;
    }
    public Message(String sender, String type, String content, byte[] fileData)
    {
        this.sender = sender;
        this.type = type;
        this.content = content;
        this.fileData = fileData;
    }
    // Getters
    public String getSender()
    {
        return sender;
    }
    public String getType()
    {
        return type;
    }
    public String getContent()
    {
        return content;
    }
    public byte[] getFileData()
    {
        return fileData;
    }
}
