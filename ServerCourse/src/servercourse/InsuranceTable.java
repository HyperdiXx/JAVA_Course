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
public class InsuranceTable extends DataT implements ResTable{

    public InsuranceTable(Statement stmt, DBConnection DB) {
        super(stmt,DB);
    }
    
    @Override
    public ResultSet getTableRes() {
        ResultSet rs = null;
        try {
            rs = stat.executeQuery("SELECT * FROM insurance");
        } catch (SQLException e) {
        }
        return rs;
    }
    
    public String AddInTable(String[] info) {
        String insertStr = "";
        try {
            insertStr = "INSERT INTO insurance (IDi,type,money,time) VALUES ("
                    + quotate(info[1]) + ","
                    + quotate(info[2]) + ","
                    + quotate(info[3]) + ","
                    + quotate(info[4]) + ")";
            int done = stat.executeUpdate(insertStr);
            return "success";
        } catch (Exception e) {
            System.err.println("Error of adding date");
            return "fail";
        }
    }

    public String DeleteFromTable(String ID) {
        String insertStr = "";
        try {
            insertStr = "DELETE FROM insurance WHERE IDi=" + ID;
            int done = stat.executeUpdate(insertStr);
            return "success";
        } catch (Exception e) {
            System.err.println("Error.Check deleting data");
            return "fail";
        }
    }

    public String FilterInTable(String FilterColumn, String FilterValue) {
        ResultSet rs = null;
        String message = "";
        try {
            rs = stat.executeQuery("SELECT * FROM insurance WHERE IDi like '%" + FilterValue + "%'");
            while (rs.next()) {
                message += rs.getString("IDi") + ",";
                message += rs.getString("type") + ",";
                message += rs.getString("money") + ",";
                message += rs.getString("time") + ",";
            }
            if (message.equals("")) {
                return "Fail";
            } else {
                return message;
            }
        } catch (SQLException e) {
            System.err.println("Exception in Table of insurance");
            return "";
        } finally {
            DB.close(rs);
        }
    }

    public String ReadAllRecord() {
        ResultSet rs = getTableRes();
        String message = "";
        try {
            while (rs.next()) {
                message += rs.getString("IDi") + ",";
                message += rs.getString("type") + ",";
                message += rs.getString("money") + ",";
                message += rs.getString("time") + ",";
            }
            return message;
        } catch (Exception e) {
            System.out.println("Exception in Table of insurance");
            return "";
        } finally {
            DB.close(rs);
        }
    }
}
