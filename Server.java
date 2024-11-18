/*
Project: Client-Server Prime Number Check System
Name: Jorge Delgado
Date: 11/17/2024
Course: CEN:3024
App Name: Server.java
Description: This application will receive a number from the client
             and check to see if it is a prime number. The server
             will then send the response back to the client .
*/

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application {
    @Override
    public void start(Stage primaryStage) {
        TextArea textArea = new TextArea();

        Scene scene = new Scene(new ScrollPane(textArea), 450, 250);
        primaryStage.setTitle("Prime Server");
        primaryStage.setScene(scene);
        primaryStage.show();


        new Thread(() -> {

            try{
                ServerSocket serverSocket = new ServerSocket(8000);

                Platform.runLater(() ->
                    textArea.appendText("Server started at " +new Date() +'\n')
                );
                Socket socket = serverSocket.accept();
                DataInputStream clientInput = new DataInputStream(socket.getInputStream());
                DataOutputStream clientOutput = new DataOutputStream(socket.getOutputStream());


                while(true){
                    boolean isPrime = true;
                    String message;

                    //Receive number for client:
                    int number = clientInput.readInt();

                    //Check if number is prime:
                    if(number <= 1){
                        isPrime = false;
                    }
                    for (int i=2;i<= Math.sqrt(number);i++){
                        if(number % i == 0){
                             isPrime = false;
                             break;
                        }

                    }
                    //Response for Client:
                    if (isPrime){
                        message = "Response from the server: " + number +" is a prime number!" +"\n";
                    } else {
                        message = "Response from the server: " + number +" is NOT a prime number!" + '\n';

                    }
                    clientOutput.writeUTF(message);
                    Platform.runLater(()-> {
                        textArea.appendText("Number received from the client: " + number + '\n');
                        textArea.appendText( message);
                    });
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }

        }).start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
