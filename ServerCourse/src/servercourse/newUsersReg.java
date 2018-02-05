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
public class newUsersReg extends DataT implements ResTable{
   
    
    public newUsersReg(Statement stmt, DBConnection DB) {
        super(stmt,DB);
    }
    
    @Override
    public ResultSet getTableRes() {
        ResultSet rs = null;
        try {
            rs = stat.executeQuery("SELECT user,password FROM loginusers");
        } catch (SQLException e) {
        }
        return rs;
    }
    
        
    public String AddInTable(String[] info) {
        String insertStr = "";
        try {
            insertStr = "INSERT INTO loginusers (id,user,password) VALUES ("
                    + quotate(info[1]) + ","
                    + quotate(info[2]) + ","
                    + quotate(info[3]) + ")";
            int done = stat.executeUpdate(insertStr);
            return "success";
        } catch (Exception e) {
            System.err.println("Error. Data adding loginRegUsers");
            return "fail";
        }
    }
    
    
     public String checkLoginUser(String login, String password) {
        ResultSet rs = getTableRes();
        int flag = 0;
        String tableLogin = "";
        String tablePassword = "";
        try {
            while (rs.next()) {
                tableLogin = rs.getString("user");
                tablePassword = rs.getString("password");
                if (tableLogin.equals(login) && tablePassword.equals(password)) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                return "success";
            } else {
                return "fail";
            }
        } catch (Exception e) {
            System.out.println("Exception in Table of Users");
            return "";
        } finally {
            DB.close(rs);
        }
    }
}