/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pizzastorect467;

import java.sql.Connection;
import java.sql.DriverManager;
public class JDBC_Connect {
    private String serverName = "localhost:3306";
    private String databaseName = "pizza_store_db";
    private String username = "root";
    private String password = "";
    
    public Connection connect(){
        Connection conn = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            
            conn = DriverManager.getConnection("jdbc:mysql://" + serverName + "/" + databaseName, username, password);
            System.out.println("Kết nối thành công !");
        } catch (Exception ex) { //Xử lí ngoại lệ
            System.out.println("Kết nối không thành công");
            ex.printStackTrace();
        }
        
        return conn;
    }
}
