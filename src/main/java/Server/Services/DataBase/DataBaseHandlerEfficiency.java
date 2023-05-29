package Server.Services.DataBase;

import Server.Config.Configs;
import Server.Config.ConstEfficiency;
import Server.Config.ConstEmployee;
import Server.Entity.Efficiency;
import Server.Entity.EfficiencyEmployee;
import Server.Entity.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseHandlerEfficiency extends ConnectToDB {
    public void addEfficiency(Integer id){

        ResultSet result =  getEfficiency(id);
        int counter = 0;
        try{
            while(result.next()){
                counter++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        if(counter >= 1){
            System.out.println("Работник уже добавлен!");
            return;
        }
        String insert = "INSERT INTO "+ ConstEfficiency.TABLE + "(" + ConstEfficiency.EMPLOYEE_ID +
                ","+ConstEfficiency.HOUR+","+ConstEfficiency.REPRIMAND+")"+" VALUES(?,?,?)";
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

    public void addHour(Integer id, Integer hour){
        Integer HOUR = 0;
        ResultSet resSet = null;
        String select = "SELECT "+ConstEfficiency.HOUR+ " FROM " + ConstEfficiency.TABLE + " WHERE " +
                ConstEfficiency.EMPLOYEE_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1,id);
            resSet = prSt.executeQuery();
            while (resSet.next()){
                HOUR = resSet.getInt(ConstEfficiency.HOUR);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("часы которые уже есть- "+HOUR);

        String update = "UPDATE " + ConstEfficiency.TABLE +
                " SET " + ConstEfficiency.HOUR + "=? WHERE " + ConstEfficiency.EMPLOYEE_ID + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
            preparedStatement.setInt(1, hour+HOUR);
            preparedStatement.setInt(2, id);
            System.out.println("edit efficiency hour successfull");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getEfficiency(Integer employee_id) {
        ResultSet resSet = null;
        //String select = "SELECT employee.id, employee.name, employee.surname, employee.position, efficiency.hour, efficiency.reprimand FROM ItEmploee.employee JOIN efficiency ON employee.id=efficiency.employee_id";
        String select = "SELECT * FROM " + ConstEfficiency.TABLE + " WHERE " +
                ConstEfficiency.EMPLOYEE_ID + "=?";
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

    public ArrayList<EfficiencyEmployee> getEmployeeEfficiency(){
        ArrayList<EfficiencyEmployee> efficiencyEmployees = new ArrayList<EfficiencyEmployee>();
         // SELECT employee.id, employee.name, employee.surname, employee.position, efficiency.hour, efficiency.reprimand FROM ItEmploee.employee JOIN efficiency ON employee.id=efficiency.employee_id
        String select_EffEmp = "SELECT "+ ConstEmployee.TABLE +"."+ConstEmployee.EMPLOYEE_ID +", "+
                ConstEmployee.TABLE +"."+ConstEmployee.NAME+", "+
                ConstEmployee.TABLE +"."+ConstEmployee.SALARY+", "+
                ConstEmployee.TABLE +"."+ConstEmployee.SURNAME+", "+
                ConstEmployee.TABLE +"."+ConstEmployee.POSITION+", "+
                ConstEfficiency.TABLE+"."+ConstEfficiency.HOUR+", "+
                ConstEfficiency.TABLE+"."+ConstEfficiency.REPRIMAND+
                " FROM "+dbName +"."+ConstEmployee.TABLE + " JOIN " + ConstEfficiency.TABLE +
                " ON " + ConstEmployee.TABLE + "."+ConstEmployee.EMPLOYEE_ID+
                "=" + ConstEfficiency.TABLE+ "."+ConstEfficiency.EMPLOYEE_ID;
        System.out.println(select_EffEmp);
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_EffEmp);
            ResultSet resSet = prSt.executeQuery();
            while (resSet.next()) {
                EfficiencyEmployee efficiencyEmployee = new EfficiencyEmployee();
                efficiencyEmployee.setEmployee_id(resSet.getString(ConstEmployee.EMPLOYEE_ID));
                efficiencyEmployee.setEmployee_name(resSet.getString(ConstEmployee.NAME));
                efficiencyEmployee.setEmployee_surname(resSet.getString(ConstEmployee.SURNAME));
                efficiencyEmployee.setEmployee_position(resSet.getString(ConstEmployee.POSITION));
                efficiencyEmployee.setEmployee_hour(resSet.getInt(ConstEfficiency.HOUR));
                efficiencyEmployee.setEmployee_reprimand(resSet.getBoolean(ConstEfficiency.REPRIMAND));
                efficiencyEmployee.setEmployee_hour_salary(resSet.getInt(ConstEmployee.SALARY));
                efficiencyEmployees.add(efficiencyEmployee);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return efficiencyEmployees;
    }

    public void setRebuke(String id){
        Integer _id = Integer.parseInt(id);
        String update = "UPDATE " + ConstEfficiency.TABLE +
                " SET " + ConstEfficiency.REPRIMAND + "=? WHERE " + ConstEfficiency.EMPLOYEE_ID + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, _id);
            System.out.println("edit efficiency hour successfull");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void unsetRebuke(String id){
        Integer _id = Integer.parseInt(id);
        String update = "UPDATE " + ConstEfficiency.TABLE +
                " SET " + ConstEfficiency.REPRIMAND + "=? WHERE " + ConstEfficiency.EMPLOYEE_ID + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, _id);
            System.out.println("edit efficiency hour successfull");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}