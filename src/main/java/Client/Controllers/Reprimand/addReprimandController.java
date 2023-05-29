package Client.Controllers.Reprimand;

import Client.Config.ConnectConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class addReprimandController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextField id_empl_field;
    @FXML
    private TextField pison_description;
    @FXML
    private Button add_reprimand_btn;
    @FXML
    void initialize() {
        add_reprimand_btn.setOnAction(actionEvent -> sendReprimandData());
    }

    private void sendReprimandData() {
        String impId = id_empl_field.getText();
        String description = pison_description.getText();

        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("addReprimand");writer.newLine();
            writer.write(impId);writer.newLine();
            writer.write(description); writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
