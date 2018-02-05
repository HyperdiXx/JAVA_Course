
package servercourse;

import java.io.*;
import java.net.*;
import java.sql.*;

public class ServerCourse {

    public static void main(String[] args) {
        try {
            int counterUsers = 0;
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("Server created");
            while (true)
            {

                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostName() + " connected");
                counterUsers++;
               
                System.out.println("Client №" + counterUsers);
                ServerThread thread = new ServerThread(socket, counterUsers);
                thread.start();
                System.out.println("Done!");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

class ServerThread extends Thread {

    private ObjectOutputStream out;
    private ObjectInputStream in; 
    private DBConnection DB;
    private InetAddress adres; 
    private String clientMessage;
    private java.sql.Statement stmt;
    private int counter;
    private CheckAdmin adminTable;
    private CheckUser userTable;
    private carsTable carsTable;
    private InsuranceTable insuranceTable;
    private newUsersReg newUser;
    private carsAvaTable carsAvaTable;
    

    public ServerThread(Socket s, int counter) throws IOException {
        this.counter = counter;

        out = new ObjectOutputStream(s.getOutputStream());
        in = new ObjectInputStream(s.getInputStream());

        adres = s.getInetAddress();
        DB = new DBConnection();
        DB.init();
        Connection conn = DB.getMyConnection();
        try {
            stmt = conn.createStatement();
            adminTable = new CheckAdmin(stmt, DB);
            userTable = new CheckUser(stmt, DB);
            carsTable = new carsTable(stmt, DB);
            insuranceTable = new InsuranceTable(stmt, DB);
            newUser = new newUsersReg(stmt,DB);
            carsAvaTable = new carsAvaTable(stmt, DB);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void writeObj(String str) {
        clientMessage = str;
        try {
            out.writeObject(clientMessage);
        } catch (IOException e) {
            System.err.println("Error I/О thread" + e);
        }
    }

    public void run() {
        String messageToClient = "";
        String str;
        String Exit = "";
        try {
            System.out.println("Server is waiting...");
            while (!Exit.equals("Exit")) {
                str = (String) in.readObject();
                String messageSend[] = str.split(",");
                switch (messageSend[0]) {
                    case "checkAdminLogin":
                        String AdminLogin = messageSend[1];
                        String AdminPassword = messageSend[2];
                        messageToClient = adminTable.checkLoginAdmin(AdminLogin, AdminPassword);
                        writeObj(messageToClient);
                        break;
                    case "checkUserLogin":
                        String UserLogin = messageSend[1];
                        String UserPassword = messageSend[2];
                        messageToClient = userTable.checkLoginUser(UserLogin, UserPassword);
                        writeObj(messageToClient);
                        break;
                    case "initTableCars":
                        messageToClient = carsTable.ReadAllData();
                        writeObj(messageToClient);
                        break;
                    case "initTableP":
                        messageToClient = carsAvaTable.ReadAllData();
                        writeObj(messageToClient);
                        break;
                    case "addInCarsTable":
                        messageToClient = carsTable.AddInCarsTable(messageSend);
                        writeObj(messageToClient);
                        break;
                    case "addInAvaTable":
                        String ID1 = messageSend[1];
                        messageToClient = carsAvaTable.AddAvaTableP(ID1, messageSend);
                        writeObj(messageToClient);
                        break;
                    case "addInRegTable":
                        messageToClient = newUser.AddInTable(messageSend);
                        writeObj(messageToClient);
                        break;
                    case "deleteFromCarsTable":
                        String ID = messageSend[1];
                        messageToClient = carsTable.DeleteFromCarsTable(ID);
                        writeObj(messageToClient);
                        break;
                    case "editInCarsTable":
                        messageToClient = carsTable.EditInCarsTable(messageSend);
                        writeObj(messageToClient);
                        break;
                    case "filterInCarsTable":
                        String FilterColumn = messageSend[1];
                        String FilterValue = messageSend[2];
                        messageToClient = carsTable.FilterInCarsTable(FilterColumn, FilterValue);
                        writeObj(messageToClient);
                        break;
                    case "sortInCarsTable":
                        String sortColumn = messageSend[1];
                        String ifDesc = messageSend[2];
                        messageToClient = carsTable.SortInCarsTable(sortColumn, ifDesc);
                        writeObj(messageToClient);
                        break;
                    case "addInInsuranceTable":
                        messageToClient = insuranceTable.AddInTable(messageSend);
                        writeObj(messageToClient);
                        break;
                    case "showUserInsurance":
                        String loginValue = messageSend[1];
                        messageToClient = insuranceTable.FilterInTable("", loginValue);
                        writeObj(messageToClient);
                        break;
                    case "initTableInsurance":
                        messageToClient = insuranceTable.ReadAllRecord();
                        writeObj(messageToClient);
                        break;
                    case "deleteFromInsuranceTable":
                        String id = messageSend[1];
                        messageToClient = insuranceTable.DeleteFromTable(id);
                        writeObj(messageToClient);
                        break;
                    case "Exit":
                        Exit = "Exit";
                        break;
                    default:
                        System.err.println("UnknownComand");
                }
            }
        } catch (Exception e) {
            System.err.println("Closed connection");
        } finally {
            disconnect(); 
        }
    }

    public void disconnect() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            System.out.println(adres.getHostName() + " Finish " + counter + " client");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }
}

