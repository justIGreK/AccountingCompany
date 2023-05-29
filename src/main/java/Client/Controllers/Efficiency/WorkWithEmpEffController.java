package Client.Controllers.Efficiency;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.Config.ConnectConfig;
import Server.Entity.EfficiencyEmployee;
import Server.Entity.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class WorkWithEmpEffController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add_hour;

    @FXML
    private Button add_permited;

    @FXML
    private Button delete_permited;

    @FXML
    private Label emp_info;

    @FXML
    private TextField id;

    @FXML
    private Button select_btn;

    @FXML
    private Spinner<Integer> select_hour;
    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,24,8);

    @FXML
    void initialize() {
        select_hour.setValueFactory(svf);
        select_btn.setOnAction(actionEvent -> selectEmpl());
        add_hour.setOnAction(actionEvent -> addHour());
        add_permited.setOnAction(actionEvent -> addPermited());
        delete_permited.setOnAction(actionEvent -> unsetPermited());
    }

    void addPermited(){
        String id = this.id.getText();
        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("setRebuke");writer.newLine();
            writer.write(id);writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void unsetPermited(){
        String id = this.id.getText();
        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("unsetRebuke");writer.newLine();
            writer.write(id);writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addHour(){
        Integer cout_hour = select_hour.getValue();
        String h = Integer.toString(cout_hour);
        String id = this.id.getText();
        System.out.println("hour = " + cout_hour);

        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("addHour");writer.newLine();
            writer.write(id);writer.newLine();
            writer.write(h); writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void selectEmpl(){
        String id = this.id.getText();
        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("getEmployeesEfficiency");writer.newLine();
            writer.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            try {
                Object object = objectInputStream.readObject();
                ArrayList<EfficiencyEmployee> efficiencyEmployees = (ArrayList<EfficiencyEmployee>) object;
                System.out.println(efficiencyEmployees.size());
                for(EfficiencyEmployee e: efficiencyEmployees){
                    if(e.getEmployee_id().equals(id)){
                        emp_info.setText(e.getEmployee_name()+" "+e.getEmployee_surname()+" "+
                                e.getEmployee_position()+ " кол-во часов: "+e.getEmployee_hour() + " выговор: "+e.isEmployee_reprimand() );
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}