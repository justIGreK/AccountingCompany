package Server.Services.DataBase;

import Server.Config.ConstActivity;
import Server.Config.ConstEmployee;
import Server.Config.ConstReprimand;
import Server.Entity.Activity;
import Server.Entity.Reprimand;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseHandlerActivity extends ConnectToDB {

    public void addActivity(Integer id){

        ResultSet result = getActivity(id);
        int counter = 0;
        try{
            while(result.next()){
                counter++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        if(counter >= 1){
            return;
        }
        String insert = "INSERT INTO "+ ConstActivity.TABLE + "(" + ConstActivity.ID + ConstActivity.EVENTNAME +
                "," + ConstActivity.DESCRIPTION + ")" + " VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);

            prSt.setInt(1, id);
            prSt.setInt(2, 0);
            prSt.setInt(3, 0);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Activity> getActivities(){
        ArrayList<Activity> activities = new ArrayList<Activity>();
        String select = "SELECT * FROM " + ConstActivity.TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resSet = prSt.executeQuery();
            while (resSet.next()) {
                Activity activity = new Activity();

                activity.setId(resSet.getInt(ConstActivity.ID));
                activity.setNameOfEvent(resSet.getString(ConstActivity.EVENTNAME));
                activity.setDescriptionOfEvent(resSet.getString(ConstActivity.DESCRIPTION));
                activities.add(activity);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return activities;
    }
    public ResultSet getActivity(Integer employee_id) {
        ResultSet resSet = null;
        //String select = "SELECT employee.id, employee.name, employee.surname, employee.position, efficiency.hour, efficiency.reprimand FROM ItEmploee.employee JOIN efficiency ON employee.id=efficiency.employee_id";
        String select = "SELECT * FROM " + ConstActivity.TABLE + " WHERE " +
                ConstActivity.ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1,employee_id);

            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public void putActivity(Activity activity) {
        String insert = "INSERT INTO "+ ConstActivity.TABLE + "("+ ConstActivity.ID + "," + ConstActivity.EVENTNAME +
                ","+ConstActivity.DESCRIPTION+")"+" VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);

            prSt.setString(1, activity.getId().toString());
            prSt.setString(2, activity.getNameOfEvent());
            prSt.setString(3, activity.getDescriptionOfEvent());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
