package Server.Controllers;

import Server.Entity.Activity;
import Server.Entity.Employee;
import Server.Entity.Reprimand;
import Server.Services.DataBase.DataBaseHandlerActivity;
import Server.Services.DataBase.DataBaseHandlerEmployee;
import Server.Services.DataBase.DataBaseHandlerReprimand;

import java.util.ArrayList;

public class ReprimandController {
    public static ArrayList<Reprimand> getEmployers() {
        DataBaseHandlerReprimand db = new DataBaseHandlerReprimand();
        return db.getReprimands();
    }
    public static void AddReprimand(String employeeId, String reprimandDescription){
        Reprimand reprimand = new Reprimand();
        reprimand.setId(0);
        reprimand.setEmployee_id(employeeId);
        reprimand.setDescription(reprimandDescription);
        DataBaseHandlerReprimand dbHandler = new DataBaseHandlerReprimand();
        dbHandler.putReprimand(reprimand);
    }
}
