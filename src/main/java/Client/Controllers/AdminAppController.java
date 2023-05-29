package Client.Controllers;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.Config.ConnectConfig;
import Server.Entity.*;
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

public class AdminAppController implements Initializable {

    @FXML
    public TableColumn<Reprimand, Integer> reprimand__id;
    @FXML
    public TableColumn<Reprimand, String>  emp_id_column;
    @FXML
    public TableColumn<Reprimand, String>  description;
    @FXML
    public Button reload_reprimand;
    @FXML
    public Button add_reprimand;
    @FXML
    public TableView<Activity> table_activity;
    @FXML
    public TableColumn<Activity, Integer> activity__id;
    @FXML
    public TableColumn<Activity, String> activity_name;
    @FXML
    public TableColumn<Activity, String> activity_description;
    @FXML
    public Button reload_activity;
    @FXML
    public Button add_activity;
    @FXML
    private TableView<Reprimand> table_reprimand;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add_efficiency;
    @FXML
    private Button add_emploee_btn;
    @FXML
    private Button city_statistic_btn;
    @FXML
    private Button delete_employee_btn;
    @FXML
    private Button edit_employee_btn;
    @FXML
    private Button department_statistic_btn;

    @FXML
    private TextField id_for_delete;

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

        add_emploee_btn.setOnAction(actionEvent -> openAddWorkerWindow());

        add_activity.setOnAction(actionEvent -> openAddActivityWindow());
        add_reprimand.setOnAction(actionEvent -> openAddReprimandWindow());

        reload_activity.setOnAction(actionEvent -> reloadActivityInfo());
        reload_reprimand.setOnAction(actionEvent -> reloadReprimandInfo());

        reload_btn.setOnAction(actionEvent -> reloadInfoAboutEmployees());
        delete_employee_btn.setOnAction(actionEvent -> deleteEmployee());
        edit_employee_btn.setOnAction(actionEvent -> openEditEmployeeWindow());
        department_statistic_btn.setOnAction(actionEvent -> openDepartmentStatisticWindow());
        city_statistic_btn.setOnAction(actionEvent -> openCityStatisticWindow());
        add_efficiency.setOnAction(actionEvent -> openAddEfficiencyWindow());
        reload_efficiency.setOnAction(actionEvent -> reloadEfficiency());
        work_with_efficiency.setOnAction(actionEvent -> openWorkWithEmployeeEfficiencyWindow());
        reload_accounts.setOnAction(actionEvent -> reloadAccounts());
        delete_account_btn.setOnAction(actionEvent -> deleteAccount());
        add_admin_btn.setOnAction(actionEvent -> setAdmin());
    }

    private void reloadReprimandInfo() {
        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("getReprimandInfo");
            writer.newLine();
            writer.flush();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                try {
                    Object object = objectInputStream.readObject();
                    ArrayList<Reprimand> reprimands = (ArrayList<Reprimand>) object;

                    printReprimandsIntoTable(reprimands);
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

    private void printReprimandsIntoTable(ArrayList<Reprimand> reprimands) {
        for(Reprimand e:reprimands){
            System.out.println(e.toString());
        }
        ObservableList<Reprimand> observableList = FXCollections.observableArrayList(reprimands);
        table_reprimand.setItems(observableList);
        reprimand__id.setCellValueFactory(new PropertyValueFactory<Reprimand,Integer>("id"));
        emp_id_column.setCellValueFactory(new PropertyValueFactory<Reprimand,String>("employee_id"));
        description.setCellValueFactory(new PropertyValueFactory<Reprimand,String>("description"));
    }

    private void reloadActivityInfo() {
        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("getActivityInfo");
            writer.newLine();
            writer.flush();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                try {
                    Object object = objectInputStream.readObject();
                    ArrayList<Activity> activities = (ArrayList<Activity>) object;
                    printActivitiesIntoTable(activities);
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

    private void printActivitiesIntoTable(ArrayList<Activity> activities) {
        for(Activity e:activities){
            System.out.println(e.toString());
        }
        ObservableList<Activity> observableList = FXCollections.observableArrayList(activities);
        table_activity.setItems(observableList);
        activity__id.setCellValueFactory(new PropertyValueFactory<Activity,Integer>("id"));
        activity_name.setCellValueFactory(new PropertyValueFactory<Activity,String>("nameOfEvent"));
        activity_description.setCellValueFactory(new PropertyValueFactory<Activity,String>("descriptionOfEvent"));
    }

    private void openAddReprimandWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Client/addReprimand.fxml"));
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

    private void openAddActivityWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Client/addActivity.fxml"));
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

    void setAdmin(){
        String id = id_for_delete_account.getText();
        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("setAccountAdmin");writer.newLine();
            writer.write(id);
            writer.flush();
            id_for_delete_account.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void deleteAccount(){
        String id = id_for_delete_account.getText();
        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("deleteAccount");writer.newLine();
            writer.write(id);
            writer.flush();
            id_for_delete_account.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void reloadAccounts(){
        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("getAccounts");writer.newLine();
            writer.flush();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                try {
                    Object object = objectInputStream.readObject();
                    ArrayList<Account> accounts = (ArrayList<Account>) object;
                    printAccounts(accounts);
                    System.out.println(accounts.size());
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
    void openWorkWithEmployeeEfficiencyWindow(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Client/WorkWithEmpEff.fxml"));
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

    void openAddEfficiencyWindow(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Client/AddEmployeeEfficiency.fxml"));
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

    void openAddWorkerWindow(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Client/AddEmployee.fxml"));
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

    void deleteEmployee(){
        try(Socket clientSocket = new Socket(ConnectConfig.IP,ConnectConfig.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
                String id = id_for_delete.getText();
                writer.write("deleteEmployee");writer.newLine();
                writer.write(id);
                writer.flush();
                id_for_delete.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void openEditEmployeeWindow(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Client/EditEmployee.fxml"));
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
}
