package Client.Controllers;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.Config.ConnectConfig;
import Server.Entity.Account;
import Server.Entity.EfficiencyEmployee;
import Server.Entity.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UserAppController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button city_statistic_btn;

    @FXML
    private Button department_statistic_btn;

    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> employee_department;

    @FXML
    private TableColumn<Employee, Integer> employee_id;

    @FXML
    private TableColumn<Employee, String> employee_location;

    @FXML
    private TableColumn<Employee, String> employee_name;

    @FXML
    private TableColumn<Employee, String> employee_position;

    @FXML
    private TableColumn<Employee, Integer> employee_salary;

    @FXML
    private TableColumn<Employee, String> employee_surname;
    @FXML
    private TableColumn<Account, String> acc_email_column;

    @FXML
    private TableColumn<Account, Integer> acc_id_column;

    @FXML
    private TableColumn<Account, String> acc_login_column;

    @FXML
    private TableColumn<Account, String> acc_name_column;

    @FXML
    private TableColumn<Account, Integer> acc_status_column;

    @FXML
    private TableView<Account> accounts_table;
    @FXML
    private Label login_label;

    @FXML
    private Label name_label;

    @FXML
    private Button reload_btn;
    @FXML
    private Button reload_efficiency;

    @FXML
    private TableColumn<EfficiencyEmployee, Integer> eff_emp_hour_column;

    @FXML
    private TableColumn<EfficiencyEmployee, String> eff_emp_id_column;

    @FXML
    private TableColumn<EfficiencyEmployee, String> eff_emp_name_column;

    @FXML
    private TableColumn<EfficiencyEmployee, String> eff_emp_position_column;

    @FXML
    private TableColumn<EfficiencyEmployee, Integer> eff_emp_reprimant_column;

    @FXML
    private TableColumn<EfficiencyEmployee, Double> eff_emp_sum_column;

    @FXML
    private TableColumn<EfficiencyEmployee, String> eff_emp_surname_column;

    @FXML
    private TableView<EfficiencyEmployee> table_efficiency;

    @FXML
    private Button work_with_efficiency;

    @FXML
    private Button delete_account_btn;

    public static String account_name = "";
    public static String account_login ="";
    @FXML
    private Button reload_accounts;
    @FXML
    private TextField id_for_delete_account;

    @FXML
    private Button add_admin_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_label.setText(account_login);
        name_label.setText(account_name);
        reload_btn.setOnAction(actionEvent -> reloadInfoAboutEmployees());;
        department_statistic_btn.setOnAction(actionEvent -> openDepartmentStatisticWindow());
        city_statistic_btn.setOnAction(actionEvent -> openCityStatisticWindow());
        reload_efficiency.setOnAction(actionEvent -> reloadEfficiency());
    }


    void printAccounts(ArrayList<Account> accounts){
        for(Account a : accounts){
            System.out.println(a.getLogin());
        }
        ObservableList<Account> observableList = FXCollections.observableArrayList(accounts);
        accounts_table.setItems(observableList);
        acc_name_column.setCellValueFactory(new PropertyValueFactory<Account,String>("name"));
        acc_login_column.setCellValueFactory(new PropertyValueFactory<Account,String>("login"));
        acc_email_column.setCellValueFactory(new PropertyValueFactory<Account,String>("email"));
        acc_status_column.setCellValueFactory(new PropertyValueFactory<Account,Integer>("role"));
        acc_id_column.setCellValueFactory(new PropertyValueFactory<Account,Integer>("account_id"));
    }

    void reloadEfficiency(){
        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("getEmployeesEfficiency");writer.newLine();
            writer.flush();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                try {
                    Object object = objectInputStream.readObject();
                    ArrayList<EfficiencyEmployee> efficiencyEmployees = (ArrayList<EfficiencyEmployee>) object;

                    System.out.println(efficiencyEmployees.size());
                    //printEmployees(employees);
                    printEmployeeEfficiency(efficiencyEmployees);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void openDepartmentStatisticWindow(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Client/DepartmentStatistic.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    void openCityStatisticWindow(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Client/CityStatistic.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    void reloadInfoAboutEmployees(){
        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("getEmployees");writer.newLine();
            writer.flush();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                try {
                    Object object = objectInputStream.readObject();
                    ArrayList<Employee> employees = (ArrayList<Employee>) object;
                    System.out.println(employees.size());
                    printEmployees(employees);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void printEmployees(ArrayList<Employee> employees){
        for(Employee e:employees){
            System.out.println(e.toString());
        }
        ObservableList<Employee> observableList = FXCollections.observableArrayList(employees);
        employeeTable.setItems(observableList);
        employee_id.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("id"));
        employee_name.setCellValueFactory(new PropertyValueFactory<Employee,String>("name"));
        employee_surname.setCellValueFactory(new PropertyValueFactory<Employee,String>("surname"));
        employee_location.setCellValueFactory(new PropertyValueFactory<Employee,String>("location"));
        employee_department.setCellValueFactory(new PropertyValueFactory<Employee,String>("department"));
        employee_position.setCellValueFactory(new PropertyValueFactory<Employee,String>("position"));
        employee_salary.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("salary"));
    }

    void printEmployeeEfficiency(ArrayList<EfficiencyEmployee> efficiencyEmployees){
        for(EfficiencyEmployee e: efficiencyEmployees){

            if(e.isEmployee_reprimand()){
                e.setEmployee_salary((e.getEmployee_hour() * e.getEmployee_hour_salary())/2);
            }else{
                e.setEmployee_salary(e.getEmployee_hour() * e.getEmployee_hour_salary());
            }
            System.out.println(e.toString());

        }
        ObservableList<EfficiencyEmployee> observableList = FXCollections.observableArrayList(efficiencyEmployees);
        table_efficiency.setItems(observableList);
        eff_emp_id_column.setCellValueFactory(new PropertyValueFactory<EfficiencyEmployee,String>("employee_id"));
        eff_emp_name_column.setCellValueFactory(new PropertyValueFactory<EfficiencyEmployee,String>("employee_name"));
        eff_emp_surname_column.setCellValueFactory(new PropertyValueFactory<EfficiencyEmployee,String>("employee_surname"));
        eff_emp_position_column.setCellValueFactory(new PropertyValueFactory<EfficiencyEmployee,String>("employee_position"));
        eff_emp_hour_column.setCellValueFactory(new PropertyValueFactory<EfficiencyEmployee,Integer>("employee_hour"));
        eff_emp_reprimant_column.setCellValueFactory(new PropertyValueFactory<EfficiencyEmployee,Integer>("employee_reprimand"));
        eff_emp_sum_column.setCellValueFactory(new PropertyValueFactory<EfficiencyEmployee,Double>("employee_salary"));
    }

}
