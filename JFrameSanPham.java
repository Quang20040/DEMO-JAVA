/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pizzastorect467;

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
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author trant
 */
public class JFrameSanPham extends javax.swing.JFrame {

    /**
     * Creates new form JFrameSanPham
     */
    private DefaultTableModel Model;
    public JFrameSanPham() {
        initComponents();
        setTitle("Quản lý của hàng bánh Pizza");
        setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.ORANGE); //Set màu cho background giao diện
        
        Model = (DefaultTableModel) tableResult.getModel();
        showTable();
        
        
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
        
        btnxoaSP.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //Xóa hàng
                if(tableResult.getSelectedRowCount() == 1){
                    //Xác định index hàng cần xóa
                    int row = tableResult.getSelectedRow();
                    xoaSP(row);
                    Model.removeRow(tableResult.getSelectedRow());
                }else if(tableResult.getSelectedRowCount() == 0){
                    JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn dữ liệu cần xóa!");
                }                
                else{
                    if(tableResult.getRowCount() == 0){
                        //Nếu không tồn tại hàng nào trong bảng
                        JOptionPane.showMessageDialog(rootPane, "Bảng không có dữ liệu !");
                    }else {
                        //Nếu bảng có dữ liệu và hàng không được chọn hoặc có quá nhiều hàng chọn cùng lúc
                        JOptionPane.showMessageDialog(rootPane, "Chỉ có thể xóa 1 hàng dữ liệu !");
                    }
                }
            }
        });
    
        btnthemSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new JFrameThemSP().setVisible(true);
                    }
                });
            }
        });
         
         btnupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Cập nhật hàng
                if (tableResult.getSelectedRowCount() == 1) {
                    int row = tableResult.getSelectedRow();
                    
                    //Lấy giá trị trong ô nhập liệu
                    String id = txtid.getText();
                    String tenSP = txtten_SP.getText();
                    String giaSP = txtgia_SP.getText();
                    
                    //Cập nhật vào bảng dữ liệu
                    Model.setValueAt(id, tableResult.getSelectedRow(), 0);
                    Model.setValueAt(tenSP, tableResult.getSelectedRow(), 1);
                    Model.setValueAt(giaSP, tableResult.getSelectedRow(), 2);
                    
                    updateSP(row);
                    
                } else if (tableResult.getSelectedRowCount() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn dữ liệu cần cập nhật!");
                } else {
                    if (tableResult.getRowCount() == 0) {
                        //Nếu không tồn tại hàng nào trong bảng
                        JOptionPane.showMessageDialog(rootPane, "Bảng không có dữ liệu !");
                    } else {
                        //Nếu bảng có dữ liệu và hàng không được chọn hoặc có quá nhiều hàng chọn cùng lúc
                        JOptionPane.showMessageDialog(rootPane, "Chỉ có thể cập nhật 1 hàng dữ liệu !");
                    }
                }
            }
        });
    }
    
     public void xoaSP(int row){
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        //Lấy dữ liệu của cột ID trong hàng cần xóa - Xóa SP dựa theo ID sản phẩm
        Model = (DefaultTableModel) tableResult.getModel();
        String ID_SP = Model.getValueAt(row, 0).toString();
        String query_xoa_SP = "delete from sanpham where id = ?";
        PreparedStatement pstm = null;
        
        try {
            pstm = conn.prepareStatement(query_xoa_SP);

            pstm.setString(1, ID_SP);

            int count_row = pstm.executeUpdate();

            if (count_row != 0) {
                JOptionPane.showMessageDialog(rootPane, "Xóa thành công");
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new JFrameSanPham().setVisible(true);
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSP(int row){
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        //Lấy dữ liệu của cột ID trong hàng cần xóa - Xóa SP dựa theo ID sản phẩm
        Model = (DefaultTableModel) tableResult.getModel();
        
        //Lấy dữ liệu từ ô nhập liệu
        String id = txtid.getText();
        String tenSP = txtten_SP.getText();
        String giaSP = txtgia_SP.getText();

        String query_update_SP = "update sanpham set TenSP = ?, GiaSP = ? where id = ? ";
        PreparedStatement pstm = null;
        
        try {
            pstm = conn.prepareStatement(query_update_SP);

            pstm.setString(1, tenSP);
            pstm.setString(2, giaSP);
            pstm.setString(3, id);

            int count_row = pstm.executeUpdate();

            if (count_row != 0) {
                JOptionPane.showMessageDialog(rootPane, "Cập nhật thành công !");
                
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new JFrameSanPham().setVisible(true);
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void showTable(){
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        String query_get_SP = "SELECT * from sanpham";
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(query_get_SP);
            ResultSet rs = pstm.executeQuery();
            
        
            while(rs.next()){
                String id = rs.getString("id");
                String ten_SP = rs.getString("TenSP");
                String gia_SP = rs.getString("GiaSP");
                
                //Định dạng phân tách phần thập phân trong số tiền.
                NumberFormat formatter = new DecimalFormat("#,###");
                String formattedNumber = formatter.format(Double.parseDouble(gia_SP));
                
                Model.addRow(new Object[] { id, ten_SP, formattedNumber});
                //Lấy ra tổng số sản phẩm
                txtsum_SP.setText(Integer.toString(rs.getRow()));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
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

        btnback = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableResult = new javax.swing.JTable();
        btnthemSP = new javax.swing.JButton();
        btnxoaSP = new javax.swing.JButton();
        txtsum_SP = new javax.swing.JTextField();
        txtten_SP = new javax.swing.JTextField();
        txtgia_SP = new javax.swing.JTextField();
        btnupdate = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtid = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnback.setText("Trở Về");
        getContentPane().add(btnback, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Tổng số sản phẩm:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 430, 130, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/14661311.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 0, 240, 540));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/Pizza-icon.png"))); // NOI18N
        jLabel2.setText("Quản Lý Sản Phẩm");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 0, 570, 120));

        tableResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên Sản Phẩm", "Giá Sản Phẩm (VNĐ)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableResultMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableResult);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 550, 290));

        btnthemSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnthemSP.setText("Thêm Sản Phẩm");
        getContentPane().add(btnthemSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 130, 40));

        btnxoaSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnxoaSP.setText("Xóa Sản Phẩm");
        getContentPane().add(btnxoaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 420, 130, 40));

        txtsum_SP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        getContentPane().add(txtsum_SP, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 420, 60, 40));
        getContentPane().add(txtten_SP, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 500, 150, 30));
        getContentPane().add(txtgia_SP, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 500, 130, 30));

        btnupdate.setText("Cập Nhật Sản Phẩm");
        getContentPane().add(btnupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 500, 150, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("ID Sản Phẩm");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 480, 100, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Tên Sản Phẩm");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 480, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Giá Sản Phẩm");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 480, -1, -1));

        txtid.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        txtid.setText("none");
        getContentPane().add(txtid, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 500, 70, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableResultMouseClicked
        //Thiết lập sự kiện mỗi khi nhấn vào 1 dòng sẽ hiển thị qua cho text field
        String tblID = Model.getValueAt(tableResult.getSelectedRow(),0).toString();
        String tblten = Model.getValueAt(tableResult.getSelectedRow(),1).toString();
        String tblgia = Model.getValueAt(tableResult.getSelectedRow(),2).toString();
        
        
        NumberFormat formatter = new DecimalFormat("###");
        String tblprice = formatter.format(Double.parseDouble(tblgia)*1000);
        
        //Hiển thị qua ô nhập
        txtid.setText(tblID);
        txtten_SP.setText(tblten);
        txtgia_SP.setText(tblprice);
    }//GEN-LAST:event_tableResultMouseClicked

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
            java.util.logging.Logger.getLogger(JFrameSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameSanPham().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnback;
    private javax.swing.JButton btnthemSP;
    private javax.swing.JButton btnupdate;
    private javax.swing.JButton btnxoaSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableResult;
    private javax.swing.JTextField txtgia_SP;
    private javax.swing.JLabel txtid;
    private javax.swing.JTextField txtsum_SP;
    private javax.swing.JTextField txtten_SP;
    // End of variables declaration//GEN-END:variables
}
