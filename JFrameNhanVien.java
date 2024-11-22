/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pizzastorect467;

/**
 *
 * @author trant
 */


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
import java.sql.CallableStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;



public class JFrameNhanVien extends javax.swing.JFrame {
    private DefaultTableModel Model;
    /**
     * Creates new form JFrameNhanVien
     */
    public JFrameNhanVien() throws ParseException {
        initComponents();
        setTitle("Quản lý của hàng bánh Pizza");
        setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.ORANGE); //Set màu cho background giao diện
        
        Model = (DefaultTableModel) tableResult.getModel();
        
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
        
        
        
        
        btnthemNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                         new JFrameThemNV().setVisible(true);
                    }
                });
            }
        });
         
        //Hiển thị dữ liệu ra bảng
        show_data_NV();
        
         
        btnshow_nv_sathai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.setRowCount(0);
                try {
                    show_nv_bisathai();
                } catch (ParseException ex) {
                    Logger.getLogger(JFrameNhanVien.class.getName()).log(Level.SEVERE, null, ex);
                }
            }}
        );
        
        
        btnsathaiNV.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            //Xóa hàng
            if(tableResult.getSelectedRowCount() == 1){
                //Xác định index hàng NV cần sa thải
                int row = tableResult.getSelectedRow();
                sathaiNV(row);
            }else if(tableResult.getSelectedRowCount() == 0){
                JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn dữ liệu trước khi thực hiện!");
            }                
            else{
                if(tableResult.getRowCount() == 0){
                    //Nếu không tồn tại hàng nào trong bảng
                    JOptionPane.showMessageDialog(rootPane, "Bảng không có dữ liệu !");
                }else {
                    //Nếu bảng có dữ liệu và hàng không được chọn hoặc có quá nhiều hàng chọn cùng lúc
                    JOptionPane.showMessageDialog(rootPane, "Chỉ có thể thực hiện từng hàng dữ liệu !");
                }
            }
        }
        });
      
    }
    
      public void show_nv_bisathai() throws ParseException {
        List<NhanVien> list_nhanvien = new ArrayList<>();
        try {
            //Sử dụng thủ tục
            JDBC_Connect connection = new JDBC_Connect();
            Connection conn = (Connection) connection.connect();

            CallableStatement cStmt = null;   
            ResultSet rs;
 

            cStmt = (CallableStatement) conn.prepareCall("{call get_db_nhanvienbisaithai(?)}");
            cStmt.setInt(1, 1);
            rs = cStmt.executeQuery();
            
               while(rs.next()){
                NhanVien nv = new NhanVien( rs.getInt("ID_NV"),
                                            rs.getString("HoTenNV"),
                                            rs.getString("SDT"),
                                            rs.getString("NgayTaoHoSo"),
                                            rs.getInt("GioiTinh"),
                                            rs.getInt("BiDuoiViec"));
                list_nhanvien.add(nv);
            }
            
            //Set trạng thái giới tính cho nhân viên
            String gioitinh_NV;
            
            for(NhanVien nv_items: list_nhanvien){
                if(nv_items.getGioiTinh() == 1){
                    gioitinh_NV = "Nam";
                }else{
                    gioitinh_NV = "Nữ";
                }
                
                
                 //Định dạng ngày
                String ngaytaohoso = nv_items.getNgayTaoHoSo();
                //Chuyển đổi String to Date
                 //Instantiating the SimpleDateFormat class
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                //Parsing the given String to Date object
                Date date = formatter.parse(ngaytaohoso);

                String ngaytaohoso_format = new SimpleDateFormat("dd-MM-yyyy").format(date);
                
                Model.addRow(new Object[]{
                    nv_items.getID_NV(), nv_items.getHoTen(),
                    nv_items.getSoDienThoai(), ngaytaohoso_format,
                    gioitinh_NV
                });
    
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(JFramePhanCongCaTruc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void show_data_NV() throws ParseException{
         List<NhanVien> list_nhanvien = new ArrayList<>();
      
          
        //Lấy dữ liệu nhân viên từ DB show ra bảng
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        String query_get_DB_nhanvien = "SELECT * from nhanvien where BiDuoiViec = 0";
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(query_get_DB_nhanvien);
            ResultSet rs = pstm.executeQuery();
             
                   
            while(rs.next()){
                NhanVien nv = new NhanVien( rs.getInt("ID_NV"),
                                            rs.getString("HoTenNV"),
                                            rs.getString("SDT"),
                                            rs.getString("NgayTaoHoSo"),
                                            rs.getInt("GioiTinh"),
                                            rs.getInt("BiDuoiViec"));
                list_nhanvien.add(nv);
            }
            
            //Set trạng thái giới tính cho nhân viên
            String gioitinh_NV;
            
            for(NhanVien nv_items: list_nhanvien){
                if(nv_items.getGioiTinh() == 1){
                    gioitinh_NV = "Nam";
                }else{
                    gioitinh_NV = "Nữ";
                }
                
                
                 //Định dạng ngày
                String ngaytaohoso = nv_items.getNgayTaoHoSo();
                //Chuyển đổi String to Date
                 //Instantiating the SimpleDateFormat class
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                //Parsing the given String to Date object
                Date date = formatter.parse(ngaytaohoso);

                String ngaytaohoso_format = new SimpleDateFormat("dd-MM-yyyy").format(date);
                
                Model.addRow(new Object[]{
                    nv_items.getID_NV(), nv_items.getHoTen(),
                    nv_items.getSoDienThoai(), ngaytaohoso_format,
                    gioitinh_NV
                });
    
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public void sathaiNV(int row){
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        //Lấy dữ liệu của cột ID trong hàng cần xóa - Xóa SP dựa theo ID sản phẩm
        Model = (DefaultTableModel) tableResult.getModel();
        String ID_NV = Model.getValueAt(row, 0).toString();
        String query_xoa_NV = "update nhanvien set BiDuoiViec = 1 where id_NV = ?";
        PreparedStatement pstm = null;
        
        try {
            pstm = conn.prepareStatement(query_xoa_NV);

            pstm.setString(1, ID_NV);

            int count_row = pstm.executeUpdate();

            if (count_row != 0) {
                JOptionPane.showMessageDialog(rootPane, "Sa thải nhân viên thành công !");
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new JFrameNhanVien().setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(JFrameNhanVien.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableResult = new javax.swing.JTable();
        btnthemNV = new javax.swing.JButton();
        btnsathaiNV = new javax.swing.JButton();
        btnshow_nv_sathai = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        btnback.setText("Trở Về");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/nhanvienpizza.png"))); // NOI18N
        jLabel2.setText("Quản Lý Nhân Viên");

        tableResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID_NV", "Họ Tên NV", "SĐT", "Ngày Tạo Hồ Sơ", "Giới Tính"
            }
        ));
        jScrollPane1.setViewportView(tableResult);

        btnthemNV.setText("Thêm Nhân Viên");
        btnthemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemNVActionPerformed(evt);
            }
        });

        btnsathaiNV.setText("Sa Thải Nhân Viên");

        btnshow_nv_sathai.setText("Nhân viên bị sa thải");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Lọc danh sách:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnback)
                        .addGap(53, 53, 53)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnthemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnsathaiNV, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnshow_nv_sathai, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(btnback))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnshow_nv_sathai, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnsathaiNV, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnthemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentHidden

    private void btnthemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnthemNVActionPerformed

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
            java.util.logging.Logger.getLogger(JFrameNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JFrameNhanVien().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(JFrameNhanVien.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnback;
    private javax.swing.JButton btnsathaiNV;
    private javax.swing.JButton btnshow_nv_sathai;
    private javax.swing.JButton btnthemNV;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableResult;
    // End of variables declaration//GEN-END:variables
}
