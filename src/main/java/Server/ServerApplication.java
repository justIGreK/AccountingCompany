package Server;

import Server.Controllers.*;
import Server.Entity.*;
import Server.Services.DataBase.DataBaseHandlerAccounts;
import Server.Services.DataBase.DataBaseHandlerEfficiency;
import Server.Services.DataBase.DataBaseHandlerEmployee;

import java.io.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerApplication {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(8081)){
            System.out.println("Сервер запущен...");
            while (true)
                try{
                    Socket socket = serverSocket.accept();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try(BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));
                                BufferedWriter writer = new BufferedWriter(
                                        new OutputStreamWriter(socket.getOutputStream())))
                            {
                                System.out.println("work");
                                String menu = reader.readLine(); // принимаем пункт меню
                                System.out.println(menu); // выводим пункт меню в консоль
                                switch (menu){
                                    case "registration":{
                                        System.out.println("addUser case");
                                        String name = reader.readLine();
                                        String login = reader.readLine();
                                        String email = reader.readLine();
                                        String password = reader.readLine();
                                        System.out.println(name + " "+ login+" "+email+" "+password);
                                        RegistrationController reg = new RegistrationController();
                                        writer.write(reg.registration(name,login,email,password));writer.newLine();
                                        writer.flush();
                                    }break;
                                    case "getReprimandInfo":{
                                        ArrayList<Reprimand> emp = ReprimandController.getEmployers();
                                        for(Reprimand e: emp){
                                            System.out.println(e.toString());
                                        }
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(emp);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    }
                                    case "getActivityInfo":{
                                        ArrayList<Activity> emp = ActivityController.getActivities();
                                        for(Activity e: emp){
                                            System.out.println(e.toString());
                                        }
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(emp);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    }
                                    case "authentication":{
                                        System.out.println("authentication case");
                                        String login = reader.readLine();
                                        String password = reader.readLine();
                                        System.out.println(login+ " " +password);
                                        AuthController auth = new AuthController();
                                        Account account = auth.authentication(login, password);
                                        String role = "";
                                        System.out.println("Роль этого пользователя " + account.getRole());
                                        if(account.getRole()){
                                            role = "admin";
                                        }else{
                                            role = "user";
                                        }
                                        if(account==null){
                                            writer.write("not_success");writer.newLine();
                                            writer.flush();
                                        }else {
                                            writer.write("success");writer.newLine();
                                            writer.write(account.getName());writer.newLine();
                                            writer.write(account.getLogin());writer.newLine();
                                            writer.write(account.getEmail());writer.newLine();
                                            writer.write(role);writer.newLine();
                                            writer.flush();
                                        }
                                    }break;
                                    case "addActivity":{
                                        String eventName = reader.readLine();
                                        String eventDescription = reader.readLine();
                                        ActivityController.AddActivity(eventName, eventDescription);
                                        break;
                                    }
                                    case "addReprimand":{
                                        String employeeId = reader.readLine();
                                        String description = reader.readLine();
                                        ReprimandController.AddReprimand(employeeId, description);
                                        break;
                                    }
                                    case "addEmployee":{
                                        System.out.println("addEmployee case");
                                        String name = reader.readLine();
                                        String surname = reader.readLine();
                                        String location = reader.readLine();
                                        String department = reader.readLine();
                                        String position = reader.readLine();
                                        String salary = reader.readLine();
                                        System.out.println("Данные с клиента: ");
                                        System.out.println(name+" "+surname+" "+location+" "+department+" "+ position+" "+salary);
                                        EmployeeController.AddEmployee(name, surname,location,department,position,salary);
                                    }break;
                                    case "getEmployees":{
                                        ArrayList<Employee> emp = EmployeeController.getEmployers();
                                        for(Employee e: emp){
                                            System.out.println(e.toString());
                                        }
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(emp);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }break;
                                    case "getEmployeesEfficiency":{
                                        ArrayList<EfficiencyEmployee> efficiencyEmployees = EfficiencyController.getEfficiencyEmployees();
                                        for(EfficiencyEmployee e: efficiencyEmployees){
                                            System.out.println(e.toString());
                                        }
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(efficiencyEmployees);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }break;
                                    case "addHour":{
                                        System.out.println("addHour case");
                                        String id = reader.readLine();
                                        String hour = reader.readLine();
                                        System.out.println("serverapp: "+ id + " "+ hour);
                                        EfficiencyController.addHour(id, hour);
                                    }break;
                                    case "deleteEmployee":{
                                        String id = reader.readLine();
                                        EmployeeController.deleteEmployee(Integer.parseInt(id));
                                    }break;
                                    case "getEmployee":{
                                        System.out.println("getEmployee case");
                                        String id = reader.readLine();
                                        Employee employee = EmployeeController.getEmployee(id);
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(employee);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }break;
                                    case "editEmployee":{
                                        System.out.println("editEmployee case");
                                        Employee employee = new Employee();
                                        String id = reader.readLine();
                                        String name = reader.readLine();
                                        String surname = reader.readLine();
                                        String location = reader.readLine();
                                        String department = reader.readLine();
                                        String position = reader.readLine();
                                        String salary = reader.readLine();

                                        employee.setId(Integer.parseInt(id));
                                        employee.setName(name);
                                        employee.setSurname(surname);
                                        employee.setLocation(location);
                                        employee.setDepartment(department);
                                        employee.setPosition(position);
                                        employee.setSalary(Integer.parseInt(salary));
                                        EmployeeController.editEmployee(employee);
                                    }break;
                                    case "addEfficiency":{
                                        System.out.println("addEfficiency case");
                                        String id = reader.readLine();
                                        EfficiencyController.addEfficiency(id);

                                    }break;
                                    case "setRebuke":{
                                        System.out.println("setRebuke case");
                                        String id = reader.readLine();
                                        DataBaseHandlerEfficiency db = new DataBaseHandlerEfficiency();
                                        db.setRebuke(id);
                                    }break;
                                    case "unsetRebuke":{
                                        System.out.println("unsetRebuke case");
                                        String id = reader.readLine();
                                        DataBaseHandlerEfficiency db = new DataBaseHandlerEfficiency();
                                        db.unsetRebuke(id);
                                    }break;
                                    case "getAccounts":{
                                        System.out.println("getAccounts case");
                                        ArrayList<Account> accounts = new ArrayList<Account>();
                                        DataBaseHandlerAccounts db = new DataBaseHandlerAccounts();
                                        accounts = db.getAccounts();
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(accounts);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }break;
                                    case "deleteAccount":{
                                        System.out.println("deleteAccounts case");
                                        String id = reader.readLine();
                                        DataBaseHandlerAccounts db = new DataBaseHandlerAccounts();
                                        db.deleteAccount(Integer.parseInt(id));
                                    }break;
                                    case "setAccountAdmin":{
                                        System.out.println("setAccountsAdmin case");
                                        String id = reader.readLine();
                                        DataBaseHandlerAccounts db = new DataBaseHandlerAccounts();
                                        db.setAdmin(Integer.parseInt(id));
                                    }
                                    default:
                                        System.out.println("Error choose");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
