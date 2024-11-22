/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pizzastorect467;


import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
/**
 *
 * @author trant
 */
public class JFrameThemNV extends javax.swing.JFrame {

    /**
     * Creates new form JFrameThemNV
     */
    public JFrameThemNV() {
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
                        try {
                            new JFrameNhanVien().setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(JFrameThemNV.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        });
         
        //Định dạng ngày
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        
        ngaytaohoso.setText(date); 
         
        btnthemnv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NhanVien nv = new NhanVien();
                String ten_nv = txthotenNV.getText();
                String sdt = txtsdtNV.getText();

                nv.setHoTen(ten_nv);
                nv.setSoDienThoai(sdt);

                if (sdt.length() != 10) {
                    JOptionPane.showMessageDialog(rootPane, "Số điện thoại chỉ được nhập 10 số !");
                } else {
                    //Định dạng ngày
                    String pattern = "yyyy-MM-dd";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String date = simpleDateFormat.format(new Date());

                    nv.setNgayTaoHoSo(date);

                    //1 là nam,  0 là nữ
                    String gioitinh = "";
                    if (gt_nam.isSelected()) {
                        gioitinh = "1";
                    }

                    if (gt_nu.isSelected()) {
                        gioitinh = "0";
                    }

                    nv.setGioiTinh(Integer.parseInt(gioitinh));

                    if (!ten_nv.equals("") && !sdt.equals("") && !gioitinh.equals("")) {
                        int check_can_add = ThemNV(nv);

                        if (check_can_add == 1) {
                            JOptionPane.showMessageDialog(rootPane, "Thêm nhân viên thành công !");
                            setVisible(false);
                            java.awt.EventQueue.invokeLater(new Runnable() {
                                public void run() {
                                    try {
                                        new JFrameNhanVien().setVisible(true);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(JFrameThemNV.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Thêm thất bại");
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập đầy đủ thông tin !");
                    }

                }

            }
        });
    }
    
    public int ThemNV(NhanVien nv){
        //Lấy dữ liệu nhân viên từ DB show ra bảng
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();
        
        String query_add_nv = "INSERT INTO nhanvien(HoTenNV, SDT, NgayTaoHoSo, GioiTinh) VALUES (?,?,?,?)";

        PreparedStatement pstm = null;
        
        
        
        try {
            pstm = conn.prepareStatement(query_add_nv);

            pstm.setString(1, nv.getHoTen());
            pstm.setString(2, nv.getSoDienThoai());
            String ngay_nhanviec = String.valueOf(nv.getNgayTaoHoSo());
            pstm.setString(3, ngay_nhanviec);
            
            pstm.setInt(4, nv.getGioiTinh());

            //Khi thực hiện các lệnh insert/update/delete sử dụng executeUpdate(), nó sẽ trả về số hàng bị tác động
            int row = pstm.executeUpdate();
            if(row != 0){
                return 1;
            }

            //Đóng kết nối
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
          
        }
        return 0;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnback = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txthotenNV = new javax.swing.JTextField();
        txtsdtNV = new javax.swing.JTextField();
        gt_nam = new javax.swing.JRadioButton();
        gt_nu = new javax.swing.JRadioButton();
        btnthemnv = new javax.swing.JButton();
        ngaytaohoso = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnback.setText("Trở Về");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/nhanvienpizza.png"))); // NOI18N
        jLabel2.setText("Thêm Nhân Viên");

        jLabel1.setText("Họ Tên:");

        jLabel3.setText("Số điện thoại:");

        jLabel4.setText("Ngày tạo hồ sơ:");

        jLabel5.setText("Giới tính:");

        buttonGroup1.add(gt_nam);
        gt_nam.setText("Nam");

        buttonGroup1.add(gt_nu);
        gt_nu.setText("Nữ");

        btnthemnv.setText("Thêm Nhân Viên");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnback)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txthotenNV)
                            .addComponent(txtsdtNV)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(gt_nam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gt_nu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnthemnv, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ngaytaohoso, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnback))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txthotenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtsdtNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(ngaytaohoso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(gt_nam)
                    .addComponent(gt_nu))
                .addGap(29, 29, 29)
                .addComponent(btnthemnv, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(157, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(JFrameThemNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameThemNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameThemNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameThemNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameThemNV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnback;
    private javax.swing.JButton btnthemnv;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton gt_nam;
    private javax.swing.JRadioButton gt_nu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField ngaytaohoso;
    private javax.swing.JTextField txthotenNV;
    private javax.swing.JTextField txtsdtNV;
    // End of variables declaration//GEN-END:variables
}
