package Server.Controllers;

import Server.Entity.Activity;
import Server.Entity.Employee;
import Server.Services.DataBase.DataBaseHandlerActivity;
import Server.Services.DataBase.DataBaseHandlerEmployee;

import java.util.ArrayList;

public class ActivityController {
    public static ArrayList<Activity> getActivities() {
        DataBaseHandlerActivity db = new DataBaseHandlerActivity();
        return db.getActivities();
    }
    public static void AddActivity(String eventName, String eventDescription){
        Activity activity = new Activity();
        activity.setId(0);
        activity.setNameOfEvent(eventName);
        activity.setDescriptionOfEvent(eventDescription);
        DataBaseHandlerActivity dbHandler = new DataBaseHandlerActivity();
        dbHandler.putActivity(activity);
    }
}
