package Client.Controllers.Activity;

import Client.Config.ConnectConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class addActivityController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextField event_name_field;
    @FXML
    private TextField event_description;
    @FXML
    private Button add_activity_btn;
    @FXML
    void initialize() {
        add_activity_btn.setOnAction(actionEvent -> sendActivityData());
    }

    private void sendActivityData() {
        String eventName = event_name_field.getText();
        String eventDescription = event_description.getText();

        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("addActivity");writer.newLine();
            writer.write(eventName);writer.newLine();
            writer.write(eventDescription); writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
