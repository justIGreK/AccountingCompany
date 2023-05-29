package Server.Entity;

import java.io.Serializable;

public class EfficiencyEmployee implements Serializable {
    private String employee_id;
    private String employee_name;
    private String employee_surname;
    private String employee_position;
    private Integer employee_hour;
    private boolean employee_reprimand;
    private double employee_salary;
    private Integer employee_hour_salary;

    public Integer getEmployee_hour_salary() {
        return employee_hour_salary;
    }

    public EfficiencyEmployee(String employee_id, String employee_name, String employee_surname, String employee_position, Integer employee_hour, boolean employee_reprimand, Integer employee_hour_salary) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.employee_surname = employee_surname;
        this.employee_position = employee_position;
        this.employee_hour = employee_hour;
        this.employee_reprimand = employee_reprimand;
        this.employee_hour_salary = employee_hour_salary;
    }

    public EfficiencyEmployee(String employee_id, String employee_name, String employee_surname, String employee_position, Integer employee_hour, boolean employee_reprimand, double employee_salary, Integer employee_hour_salary) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.employee_surname = employee_surname;
        this.employee_position = employee_position;
        this.employee_hour = employee_hour;
        this.employee_reprimand = employee_reprimand;
        this.employee_salary = employee_salary;
        this.employee_hour_salary = employee_hour_salary;
    }

    public void setEmployee_hour_salary(Integer employee_hour_salary) {
        this.employee_hour_salary = employee_hour_salary;
    }

    public EfficiencyEmployee(String employee_id, String employee_name, String employee_surname, String employee_position, Integer employee_hour, boolean employee_reprimand, double employee_salary) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.employee_surname = employee_surname;
        this.employee_position = employee_position;
        this.employee_hour = employee_hour;
        this.employee_reprimand = employee_reprimand;
        this.employee_salary = employee_salary;
    }

    public EfficiencyEmployee(String employee_id, String employee_name, String employee_surname, String employee_position, Integer employee_hour, boolean employee_reprimand) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.employee_surname = employee_surname;
        this.employee_position = employee_position;
        this.employee_hour = employee_hour;
        this.employee_reprimand = employee_reprimand;
    }

    public EfficiencyEmployee() {

    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_surname() {
        return employee_surname;
    }

    public void setEmployee_surname(String employee_surname) {
        this.employee_surname = employee_surname;
    }

    public String getEmployee_position() {
        return employee_position;
    }

    public void setEmployee_position(String employee_position) {
        this.employee_position = employee_position;
    }

    public Integer getEmployee_hour() {
        return employee_hour;
    }

    public void setEmployee_hour(Integer employee_hour) {
        this.employee_hour = employee_hour;
    }

    public boolean isEmployee_reprimand() {
        return employee_reprimand;
    }

    public void setEmployee_reprimand(boolean employee_reprimand) {
        this.employee_reprimand = employee_reprimand;
    }

    public double getEmployee_salary() {
        return employee_salary;
    }

    public void setEmployee_salary(double employee_salary) {
        this.employee_salary = employee_salary;
    }

    @Override
    public String toString() {
        return "EfficiencyEmployee{" +
                "employee_id='" + employee_id + '\'' +
                ", employee_name='" + employee_name + '\'' +
                ", employee_surname='" + employee_surname + '\'' +
                ", employee_position='" + employee_position + '\'' +
                ", employee_hour=" + employee_hour +
                ", employee_reprimand=" + employee_reprimand +
                ", employee_salary=" + employee_salary +
                ", employee_hour_salary=" + employee_hour_salary +
                '}';
    }
}
