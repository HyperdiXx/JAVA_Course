/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servercourse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author diXx
 */
public class carsTable extends DataT implements ResTable{

    public carsTable(Statement stmt, DBConnection DB) {
        super(stmt,DB);
    }
    
    @Override
    public ResultSet getTableRes() {
        ResultSet rs = null;
        try {
            rs = stat.executeQuery("SELECT * FROM cars");
        } catch (SQLException e) {
        }
        return rs;
    }
    
    
    
    public String AddInCarsTable(String[] info) {
        String insertStr = "";
        try {
            insertStr = "INSERT INTO cars (ID,mark,engine,transmission,km,color,price,date) VALUES ("
                    + quotate(info[1]) + ","
                    + quotate(info[2]) + ","
                    + quotate(info[3]) + ","
                    + quotate(info[4]) + ","
                    + quotate(info[5]) + ","
                    + quotate(info[6]) + ","
                    + quotate(info[7]) + ","
                    + quotate(info[8]) + ")";
            int done = stat.executeUpdate(insertStr);
            return "success";
        } catch (Exception e) {
            System.err.println("Error. Data adding Cars");
            return "fail";
        }
    }
    
    public String SortInCarsTable(String SortColumn, String ifDesc) {
        String message = "";
        ResultSet rs = null;
        try {
            if (ifDesc.equals("DESC")) {
                rs = stat.executeQuery("SELECT * FROM cars ORDER BY " + SortColumn + " DESC");
            } else {
                rs = stat.executeQuery("SELECT * FROM cars ORDER BY " + SortColumn);
            }
            while (rs.next()) {
                message += rs.getString("ID") + ",";
                message += rs.getString("mark") + ",";
                message += rs.getString("engine") + ",";
                message += rs.getString("transmission") + ",";
                message += rs.getString("km") + ",";
                message += rs.getString("price") + ",";
                message += rs.getString("color") + ",";
                message += rs.getString("date") + ",";
            }
            return message;
        } catch (SQLException ex) {
            System.err.println("Exception in Table of Cars");
            return "";
        } finally {
            DB.close(rs);
        }
    }
    

    public String DeleteFromCarsTable(String ID) {
        String insertStr = "";
        try {
            insertStr = "DELETE FROM cars WHERE ID=" + ID;
            int done = stat.executeUpdate(insertStr);
            return "success";
        } catch (Exception e) {
            System.err.println("Eror.Deleting Data");
            return "fail";
        }
    }

    public String EditInCarsTable(String[] info) {
        try {
            String insertStr = "UPDATE cars SET mark="
                    + quotate(info[2]) + "WHERE ID =" + info[1];
            int done = stat.executeUpdate(insertStr);
            insertStr = "UPDATE cars SET engine="
                    + quotate(info[3]) + "WHERE ID =" + info[1];
            done = stat.executeUpdate(insertStr);
            insertStr = "UPDATE cars SET transmission="
                    + quotate(info[4]) + "WHERE ID =" + info[1];
            done = stat.executeUpdate(insertStr);
            insertStr = "UPDATE cars SET km="
                    + quotate(info[5]) + "WHERE ID =" + info[1];
            done = stat.executeUpdate(insertStr);
            insertStr = "UPDATE cars SET color="
                    + quotate(info[6]) + "WHERE ID =" + info[1];
            done = stat.executeUpdate(insertStr);
            insertStr = "UPDATE cars SET price="
                    + quotate(info[7]) + "WHERE ID =" + info[1];
            done = stat.executeUpdate(insertStr);
            insertStr = "UPDATE cars SET date="
                    + quotate(info[8]) + "WHERE ID =" + info[1];
            done = stat.executeUpdate(insertStr);
            return "success";
        } catch (Exception e) {
            System.err.println("Error of editing");
            return "fail";
        }
    }

    public String FilterInCarsTable (String FilterColumn, String FilterValue) {
        ResultSet rs = null;
        String message = "";
        try {
            rs = stat.executeQuery("SELECT * FROM cars WHERE " + FilterColumn + " LIKE '%" + FilterValue + "%'");
            while (rs.next()) {
                message += rs.getString("ID") + ",";
                message += rs.getString("mark") + ",";
                message += rs.getString("engine") + ",";
                message += rs.getString("transmission") + ",";
                message += rs.getString("km") + ",";
                message += rs.getString("color") + ",";
                message += rs.getString("price") + ",";
                message += rs.getString("date") + ",";
                                
               }
            return message;
        } catch (SQLException e) {
            System.err.println("Exception in Table of carsTable");
            return "";
        } finally {
            DB.close(rs);
        }
    }
    
    public String ReadAllData() {
        ResultSet rs = getTableRes();
        String message = "";
        try {
            while (rs.next()) {
                message += rs.getString("ID") + ",";
                message += rs.getString("mark") + ",";
                message += rs.getString("engine") + ",";
                message += rs.getString("transmission") + ",";
                message += rs.getString("km") + ",";
                message += rs.getString("color") + ",";
                message += rs.getString("price") + ",";
                message += rs.getString("date") + ",";
             }
            return message;
        } catch (Exception e) {
            System.out.println("Exception in Table of CarsTable");
            return "";
        } finally {
            DB.close(rs);
        }
    }
    
    
    
}
