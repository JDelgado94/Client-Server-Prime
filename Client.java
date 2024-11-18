/*
Project: Client-Server Prime Number Check System
Name: Jorge Delgado
Date: 11/17/2024
Course: CEN:3024
App Name: Client.java
Description: This application will receive a number from a user and
             then send a number to the server. The client will then
             receive a response from the server and output the response
             to the user.
*/
import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application {
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    @Override

    public void start(Stage primaryStage) {
        BorderPane txtFieldPane = new BorderPane();
        txtFieldPane.setPadding(new Insets(5, 5, 5, 5));
        txtFieldPane.setLeft(new Label("Enter a number: "));
        txtFieldPane.setStyle("-fx-background-color: #5EBDFF");


        TextField clientField = new TextField();
        clientField.setAlignment(Pos.BOTTOM_RIGHT);
        txtFieldPane.setCenter(clientField);

        BorderPane mainPane = new BorderPane();

        TextArea txtArea = new TextArea();
        mainPane.setTop(txtFieldPane);
        mainPane.setCenter(new ScrollPane(txtArea));


        Scene scene = new Scene(mainPane, 450, 250);
        primaryStage.setTitle("Prime Client");
        primaryStage.setScene(scene);
        primaryStage.show();

        clientField.setOnAction(e -> {
            try{
                //Send number to the server:
                int num = Integer.parseInt(clientField.getText().trim());
                toServer.writeInt(num);
                toServer.flush();

                //Receive response from the server:
                String Answer = fromServer.readUTF();
                txtArea.appendText("The number is: " + num + "\n");
                txtArea.appendText(Answer + "\n");

            } catch (IOException ex) {
                System.err.println(ex);
            }
        });

        // Connecting to the Server:
        try{

            Socket socket = new Socket("localhost", 8000);

            fromServer = new DataInputStream(socket.getInputStream());

            toServer = new DataOutputStream(socket.getOutputStream());

        } catch (IOException ex) {
            txtArea.appendText(ex.toString() + '\n');
        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}
