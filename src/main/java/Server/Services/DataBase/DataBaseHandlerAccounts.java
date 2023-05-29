package Server.Services.DataBase;

import Server.Config.Configs;
import Server.Config.ConstAccounts;
import Server.Config.ConstEfficiency;
import Server.Config.ConstEmployee;
import Server.Entity.Account;
import Server.Entity.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DataBaseHandlerAccounts extends ConnectToDB {


    public Integer putAccount(Account account){
        String insert = "INSERT INTO "+ ConstAccounts.TABLE + "(" + ConstAccounts.NAME +
                ","+ConstAccounts.LOGIN+","+ConstAccounts.EMAIL+","+ ConstAccounts.PASSWORD+","+
                ConstAccounts.SALT+","+ConstAccounts.ROLE+")"+" VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);

            prSt.setString(1, account.getName());
            prSt.setString(2, account.getLogin());
            prSt.setString(3, account.getEmail());
            prSt.setString(4, account.getPassword());
            prSt.setString(5, account.getSalt());
            prSt.setBoolean(6,account.getRole());
            prSt.executeUpdate();

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public ArrayList<Account> getAccounts(){
        ArrayList<Account> accounts = new ArrayList<Account>();
        String select = "SELECT * FROM " + ConstAccounts.TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resSet = prSt.executeQuery();
            while (resSet.next()) {
                Account account = new Account();
                account.setAccount_id(resSet.getInt(ConstAccounts.ACCOUNT_ID));
                account.setName(resSet.getString(ConstAccounts.NAME));
                account.setLogin(resSet.getString(ConstAccounts.LOGIN));
                account.setEmail(resSet.getString(ConstAccounts.EMAIL));
                account.setRole(resSet.getBoolean(ConstAccounts.ROLE));
                accounts.add(account);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public void deleteAccount(int id){
        String delete = "DELETE FROM " + ConstAccounts.TABLE + " WHERE " + ConstAccounts.ACCOUNT_ID + "='" + id + "'; ";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.executeUpdate();
            System.out.println("id принятое на сервер = " + id);
            System.out.println("account успешно удален");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setAdmin(int id){
        String update = "UPDATE " + ConstAccounts.TABLE +
                " SET " + ConstAccounts.ROLE + "=? WHERE " + ConstAccounts.ACCOUNT_ID + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, id);
            System.out.println("set Admin successfull");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Account getAccountForAuth(String login){
        ResultSet resSet = null;
        Account account = new Account();
        String select = "SELECT * FROM "+ConstAccounts.TABLE+" WHERE " +
                ConstAccounts.LOGIN + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,login);
            resSet = prSt.executeQuery();
            while (resSet.next()){
                account.setAccount_id(resSet.getInt("id"));
                account.setName(resSet.getString("name"));
                account.setLogin(resSet.getString("login"));
                account.setEmail(resSet.getString("email"));
                account.setPassword(resSet.getString("password"));
                account.setSalt(resSet.getString("salt"));
                account.setRole(resSet.getBoolean("role"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return account;
    }
}
