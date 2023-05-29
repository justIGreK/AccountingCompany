package Server.Controllers;

import Server.Entity.EfficiencyEmployee;
import Server.Services.DataBase.DataBaseHandlerEfficiency;

import java.util.ArrayList;

public class EfficiencyController {
    static public void addEfficiency(String id_){
        Integer id = Integer.parseInt(id_);
        DataBaseHandlerEfficiency db = new DataBaseHandlerEfficiency();
        db.addEfficiency(id);
    }

    static public ArrayList<EfficiencyEmployee> getEfficiencyEmployees(){
        DataBaseHandlerEfficiency db = new DataBaseHandlerEfficiency();
        return db.getEmployeeEfficiency();
    }

    static public void addHour(String id_, String hour_){
        Integer hour = Integer.parseInt(hour_);
        Integer id = Integer.parseInt(id_);
        DataBaseHandlerEfficiency db = new DataBaseHandlerEfficiency();
        db.addHour(id, hour);
    }
}
