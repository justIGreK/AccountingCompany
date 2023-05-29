package Server.Services.DataBase;

import Server.Config.ConstActivity;
import Server.Config.ConstEmployee;
import Server.Config.ConstReprimand;
import Server.Entity.Employee;
import Server.Entity.Reprimand;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseHandlerReprimand extends ConnectToDB{
    public void addReprimand(Integer id){

        ResultSet result = getReprimand(id);
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
        String insert = "INSERT INTO "+ ConstReprimand.TABLE + "(" + ConstReprimand.ID + ConstReprimand.EMPLOYEE_ID +
                "," + ConstReprimand.DESCRIPTION + ")" + " VALUES(?,?,?)";
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
    public ArrayList<Reprimand> getReprimands(){
        ArrayList<Reprimand> employees = new ArrayList<Reprimand>();
        String select = "SELECT * FROM " + ConstReprimand.TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resSet = prSt.executeQuery();
            while (resSet.next()) {
                Reprimand reprimand = new Reprimand();

                reprimand.setId(resSet.getInt(ConstReprimand.ID));
                reprimand.setEmployee_id(resSet.getString(ConstReprimand.EMPLOYEE_ID));
                reprimand.setDescription(resSet.getString(ConstReprimand.DESCRIPTION));
                employees.add(reprimand);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employees;
    }
    public ResultSet getReprimand(Integer employee_id) {
        ResultSet resSet = null;
        //String select = "SELECT employee.id, employee.name, employee.surname, employee.position, efficiency.hour, efficiency.reprimand FROM ItEmploee.employee JOIN efficiency ON employee.id=efficiency.employee_id";
        String select = "SELECT * FROM " + ConstReprimand.TABLE + " WHERE " +
                ConstReprimand.EMPLOYEE_ID + "=?";
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

    public void putReprimand(Reprimand reprimand) {
        String insert = "INSERT INTO "+ ConstReprimand.TABLE + "("+ ConstReprimand.ID + "," + ConstReprimand.EMPLOYEE_ID +
                ","+ConstReprimand.DESCRIPTION+")"+" VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);

            prSt.setString(1, reprimand.getId().toString());
            prSt.setString(2, reprimand.getEmployee_id());
            prSt.setString(3, reprimand.getDescription());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
