package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Shared.Message;


import java.io.*;
import java.net.Socket;



public class ClientGUI extends Application
{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private TextArea chatArea;
    private TextField inputField;
    private String username = "babak";

    @Override
    public void start(Stage primaryStage)
    {
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        inputField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> sendMessage());
        HBox inputBox = new HBox(10, inputField, sendButton);
        VBox root = new VBox(10, chatArea, inputBox);
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Chat Client");
        primaryStage.setScene(scene);
        primaryStage.show();
        new Thread(this::connectAndReceive).start();
    }

    private void connectAndReceive()
    {
        try
        {
            socket = new Socket("localhost", 5003);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message("join", username, ""));
            while (true)
            {
                Message msg = (Message) in.readObject();
                Platform.runLater(() -> chatArea.appendText(msg.getSender() + ": " + msg.getContent() + "\n"));
            }
        }
        catch (Exception e)
        {
            Platform.runLater(() -> showError("Disconnected from server"));
        }
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;

        try {
            out.writeObject(new Message("chat", username, text));
            inputField.clear();
        } catch (IOException e) {
            showError("Error sending message: " + e.getMessage());
        }
    }

    private void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR, error, ButtonType.OK);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
