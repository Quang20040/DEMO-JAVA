/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pizzastorect467;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author trant
 */
public class PizzaStoreCT467 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameDangNhap().setVisible(true);
            }
        });
        
        
        //Function procedure
//        JDBC_Connect connection = new JDBC_Connect();
//        Connection conn = (Connection) connection.connect();
//
//        CallableStatement cStmt = null;
//        
//        cStmt =  (CallableStatement)conn.prepareCall("{? = call get_gioitinhNV(?)}");
//        cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
//        cStmt.setInt(2, 5);
//        
//        
//        cStmt.execute();
//        System.out.println(cStmt.getInt(1));
//        ResultSet rs = cStmt.executeQuery();
//        
//        while(rs.next()){
//            String SDT = rs.getString("SDT");
//            System.out.println("hotennv"+SDT);
//              
//        }
//        
        
        
//          Store procedure
//        JDBC_Connect connection = new JDBC_Connect();
//        Connection conn = (Connection) connection.connect();
//
//        CallableStatement cStmt = null;
//
//        cStmt = (CallableStatement) conn.prepareCall("{call get_day_of_week(?,?)}");
//        cStmt.setInt(1, 6);
//        cStmt.registerOutParameter(2, java.sql.Types.CHAR);
//        cStmt.executeQuery(); //gọi thủ tục
//        String ketqua = cStmt.getString(2); //Lấy giá trị tham số OUT balance
//        System.out.print(ketqua);
    }
 }
    

