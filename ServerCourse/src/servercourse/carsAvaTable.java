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
class carsAvaTable extends DataT implements ResTable{
    
    public carsAvaTable(Statement stmt, DBConnection DB)
    {
        super(stmt, DB);
    }
    
     @Override
    public ResultSet getTableRes() {
        ResultSet rs = null;
        try {
            rs = stat.executeQuery("SELECT * FROM carsava");
        } catch (SQLException e) {
        }
        return rs;
    }
    
   
    
    public String AddInAvaTable(String[] info) {
        String insertStr = "";
        try {
            insertStr = "INSERT INTO carsava (ID,mark,engine,transmission,km,price,color,date) VALUES ("
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
            System.err.println("Error. Data in CarsAva");
            return "fail";
        }
    }

    public String DeleteFromAvaTable(String ID) {
        String insertStr = "";
        try {
            insertStr = "DELETE FROM carsava WHERE ID=" + ID;
            int done = stat.executeUpdate(insertStr);
            return "success";
        } catch (Exception e) {
            System.err.println("Error.Deleting Data");
            return "fail";
        }
    }

     public String AddAvaTableP(String ID, String[] info) {
        String insertStr = "";
        try {
            insertStr = "UPDATE carsava (ID,mark,engine,transmission,km,price,color,date) VALUES("
                    + quotate(info[1]) + ","
                    + quotate(info[2]) + ","
                    + quotate(info[3]) + ","
                    + quotate(info[4]) + ","
                    + quotate(info[5]) + ","
                    + quotate(info[6]) + ","
                    + quotate(info[7]) + ","
                    + quotate(info[8])+ ")";
                    
            int done = stat.executeUpdate(insertStr);
            return "success";
        } catch (Exception e) {
            System.err.println("Error.Deleting Data");
            return "fail";
        }
    }
   
    public String EditInAvaTable(String[] info) {
        try {
            String insertStr = "UPDATE carsava SET mark="
                    + quotate(info[2]) + "WHERE ID =" + info[1];
            int done = stat.executeUpdate(insertStr);
            insertStr = "UPDATE carsava SET engine="
                    + quotate(info[3]) + "WHERE ID =" + info[1];
            done = stat.executeUpdate(insertStr);
            insertStr = "UPDATE carsava SET transmission="
                    + quotate(info[4]) + "WHERE ID =" + info[1];
            done = stat.executeUpdate(insertStr);
            insertStr = "UPDATE carsava SET km="
                    + quotate(info[5]) + "WHERE ID =" + info[1];
            done = stat.executeUpdate(insertStr);
            insertStr = "UPDATE carsava SET price="
                    + quotate(info[6]) + "WHERE ID =" + info[1];
            done = stat.executeUpdate(insertStr);
            insertStr = "UPDATE carsava SET color="
                    + quotate(info[7]) + "WHERE ID =" + info[1];
            done = stat.executeUpdate(insertStr);
            insertStr = "UPDATE carsava SET date="
                    + quotate(info[8]) + "WHERE ID =" + info[1];
            done = stat.executeUpdate(insertStr);
            return "success";
        } catch (Exception e) {
            System.err.println("Error redact data");
            return "fail";
        }
    }

    public String FilterInAvaTable (String FilterColumn, String FilterValue) {
        ResultSet rs = null;
        String message = "";
        try {
            rs = stat.executeQuery("SELECT * FROM carsava WHERE " + FilterColumn + " like '%" + FilterValue + "%'");
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
        } catch (SQLException e) {
            System.err.println("Exception in Table of CarsAva");
            return "";
        } finally {
            DB.close(rs);
        }
    }

    public String SortInAvaTable(String SortColumn, String ifDesc) {
        ResultSet rs = null;
        String message = "";
        try {
            if (ifDesc.equals("DESC")) {
                rs = stat.executeQuery("SELECT * FROM carsava ORDER BY " + SortColumn + " DESC");
            } else {
                rs = stat.executeQuery("SELECT * FROM carsava    ORDER BY " + SortColumn);
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
            System.err.println("Exception in Table of CarsAva");
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
                message += rs.getString("price") + ",";
                message += rs.getString("color") + ",";
                message += rs.getString("date") + ",";
                }
            return message;
        } catch (Exception e) {
            System.out.println("Exception in Table of CarsAva");
            return "";
        } finally {
            DB.close(rs);
        }
    }
    
    
}
