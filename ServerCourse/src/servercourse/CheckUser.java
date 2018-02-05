/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servercourse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckUser extends DataT implements ResTable{
    
    public CheckUser(java.sql.Statement stmt, DBConnection mdbc) {
        super(stmt, mdbc);
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
    public String checkLoginUser(String login, String password) {
        
        int pointer = 0;
        ResultSet rs = getTableRes();
        String tableLogin = "";
        String tablePassword = "";
        try {
            while (rs.next()) {
                tableLogin = rs.getString("user");
                tablePassword = rs.getString("password");
                if (tableLogin.equals(login) && tablePassword.equals(password)) {
                    pointer = 1;
                    break;
                }
            }
            if (pointer == 1) {
                return "success";
            } else 
            {
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
