/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pizzastorect467;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;

/**
 *
 * @author trant
 */
public class JFrameDoiMatKhau extends javax.swing.JFrame {

    /**
     * Creates new form JFrameDoiMatKhau
     */
    public JFrameDoiMatKhau() {
        initComponents();
        setTitle("Quản lý của hàng bánh Pizza");
        setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.ORANGE); //Set màu cho background giao diện
        
        btnback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                         new JFrameHome().setVisible(true);
                    }
                });
            }
        });
        
        btnupdate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                update();
            }
        });
    }
    
    public void update() {
        char[] passnow = txtpassnow.getPassword();
        char[] passnew = txtpassnew.getPassword();
        char[] passnewagain = txtpassnewagain.getPassword();

        String passnow_input = new String(passnow);
        String passnew_input = new String(passnew);
        String passnewagain_input = new String(passnewagain);

        if (passnow_input.equals("") || passnew_input.equals("") || passnewagain_input.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập đủ thông tin !");
        } else {
            if (!passnew_input.equals(passnewagain_input)) {
                JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập lại mật khẩu do không trùng khớp!");
            } else {
                JDBC_Connect connection = new JDBC_Connect();
                Connection conn = connection.connect();

                String query_get_pass = "SELECT * from account where matkhau = ?";

                String query_update = "UPDATE account SET matkhau = ? WHERE matkhau = ?";

                PreparedStatement pstm = null;

                try {
                    pstm = conn.prepareStatement(query_get_pass);
                    pstm.setString(1, getMd5(passnow_input));

                    ResultSet rs = pstm.executeQuery();
                    String old_pass = new String();

                    int check = 0;
                    //Lấy mật khẩu cũ từ bảng dữ liệu
                    while (rs.next()) {
                        check = 1;
                        old_pass = rs.getString("matkhau");
                    }

                    if (check == 0) {
                        JOptionPane.showMessageDialog(rootPane, "Mật khẩu hiện tại không chính xác !");
                    } else {
                        //Thực hiện update mật khẩu mới
                        pstm = conn.prepareStatement(query_update);

                        pstm.setString(1, getMd5(passnew_input));
                        pstm.setString(2, old_pass);

                        //Trả về số hàng khi thực hiện truy vấn
                        int row = pstm.executeUpdate();
                        if (row != 0) {
                            JOptionPane.showMessageDialog(rootPane, "Cập nhật mật khẩu mới thành công ! Hãy đăng nhập lại ");
                            setVisible(false);
                            java.awt.EventQueue.invokeLater(new Runnable() {
                                public void run() {
                                    new JFrameDangNhap().setVisible(true);
                                }
                            });
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Cập nhật không thành công !");
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    
    public static String getMd5(String input) 
    { 
        try { 
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        btnback = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnupdate = new javax.swing.JButton();
        txtpassnow = new javax.swing.JPasswordField();
        txtpassnew = new javax.swing.JPasswordField();
        txtpassnewagain = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/pass-icon.png"))); // NOI18N
        jLabel2.setText("   Đổi Mật Khẩu");

        btnback.setText("Trở về");

        jLabel1.setText("Nhập mật khẩu hiện tại:");

        jLabel3.setText("Nhập mật khẩu mới:");

        jLabel4.setText("Nhập lại mật khẩu mới:");

        btnupdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnupdate.setText("Cập Nhật");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnback))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(txtpassnow, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(txtpassnew, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(txtpassnewagain, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(268, 268, 268)
                        .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(btnback)
                .addGap(7, 7, 7)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel1))
                    .addComponent(txtpassnow, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel3))
                    .addComponent(txtpassnew, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel4))
                    .addComponent(txtpassnewagain, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameDoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameDoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameDoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameDoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameDoiMatKhau().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnback;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField txtpassnew;
    private javax.swing.JPasswordField txtpassnewagain;
    private javax.swing.JPasswordField txtpassnow;
    // End of variables declaration//GEN-END:variables
}
