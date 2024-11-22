/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pizzastorect467;



import com.mysql.cj.xdevapi.Statement;
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
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.CallableStatement;
/**
 *
 * @author trant
 */
public class JFrameLichLamViec extends javax.swing.JFrame {

    /**
     * Creates new form JFrameLichLamViec
     */
    private DefaultTableModel Model;
    public JFrameLichLamViec() throws ParseException {
        initComponents();
        setTitle("Quản lý của hàng bánh Pizza");
        setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.ORANGE); //Set màu cho background giao diện
        
        show_lich_lam_viec();
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
        
         btnphancongcatruc.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               setVisible(false);
               java.awt.EventQueue.invokeLater(new Runnable() {
                   public void run() {
                       new JFramePhanCongCaTruc().setVisible(true);
                   }
               });
           }
       });
         
         btn_search_lichlamviec.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               hienthi_ngaytrongtuan();
               //Thêm dữ liệu vào TableResult
               Model.setRowCount(0);
               try {
                   them_db_vao_tableResult((String)cb_lichlamviec.getSelectedItem());
               } catch (ParseException ex) {
                   Logger.getLogger(JFrameLichLamViec.class.getName()).log(Level.SEVERE, null, ex);
               } catch (SQLException ex) {
                   Logger.getLogger(JFrameLichLamViec.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       });
         
         
        btn_xoalichlamviec.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    xoalichlamviec((String)cb_lichlamviec.getSelectedItem());
                } catch (ParseException ex) {
                    Logger.getLogger(JFrameLichLamViec.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    
    public void xoalichlamviec(String lichlamviec) throws ParseException{
         SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //Parsing the given String to Date object
        Date ds_lichlamviec = formatter.parse(lichlamviec);

        String ds_lichlamviec_format = new SimpleDateFormat("yyyy-MM-dd").format(ds_lichlamviec);
       
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        String sql_ex = "delete from lichlamviec where ngaylamviec = ?";
        PreparedStatement pstm = null;
       
        try {
            pstm = conn.prepareStatement(sql_ex);
            pstm.setString(1, ds_lichlamviec_format);
            int count_row = pstm.executeUpdate();
            
            
            if(count_row != 0){
                JOptionPane.showMessageDialog(rootPane, "Xóa lịch làm việc thành công!");
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {           
                        try {
                            new JFrameLichLamViec().setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(JFrameLichLamViec.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }else{
                 JOptionPane.showMessageDialog(rootPane, "Xóa lịch làm việc thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public void them_db_vao_tableResult(String choose_calendar) throws ParseException, SQLException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //Parsing the given String to Date object
        Date ds_lichlamviec = formatter.parse(choose_calendar);

        String ds_lichlamviec_format = new SimpleDateFormat("yyyy-MM-dd").format(ds_lichlamviec);
       
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        String query_get_value = "SELECT * from lichlamviec llv inner join nhanvien nv on nv.id_NV = llv.id_NhanVien inner join catruc ct on ct.id_CaTruc = llv.id_CaTruc where ngaylamviec = ? and nv.BiDuoiViec = 0 group by nv.id_NV";
        PreparedStatement pstm = null;
       
        try {
            pstm = conn.prepareStatement(query_get_value);
            pstm.setString(1, ds_lichlamviec_format);
            ResultSet rs = pstm.executeQuery();
            
            
            int stt = 1;
            while(rs.next()){
                String id_NhanVien = rs.getString("id_NhanVien");
                String ten_NhanVien = rs.getString("HoTenNV");
                String gioitinh = sd_store_function_getgioitinhNV(Integer.parseInt(id_NhanVien)); //Sử dụng Function
                String ngaylamviec = rs.getString("ngaylamviec");
                String ten_catruc = rs.getString("ten_catruc");
                
                
                Model.addRow(new Object[] { stt, id_NhanVien, ten_NhanVien, gioitinh ,choose_calendar, ten_catruc});
                stt++;
            }           
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     
    public String sd_store_function_getgioitinhNV(int id_nv) throws SQLException{
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = (Connection) connection.connect();

        CallableStatement cStmt = null;
        
        cStmt =  (CallableStatement)conn.prepareCall("{? = call get_gioitinhNV(?)}");
        cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
        cStmt.setInt(2, id_nv);
        
        
        cStmt.execute();
        
        int gioitinh_NV = cStmt.getInt(1);
        
        String gioitinh = "";
        if(gioitinh_NV == 1){
            gioitinh = "Nam";
        }else if(gioitinh_NV == 0){
            gioitinh = "Nữ";
        }
        
        return gioitinh;
                
    }
    
    public void hienthi_ngaytrongtuan() {
        String ngaylamviec = (String)cb_lichlamviec.getSelectedItem();

//      Dùng hàm split để tách chuỗi
        String[] splits = ngaylamviec.split("-");
        String get_day = splits[0];
        String get_month = splits[1];
        String get_year = splits[2];

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, Integer.parseInt(get_day));
        calendar.set(Calendar.MONTH, Integer.parseInt(get_month));
        calendar.set(Calendar.YEAR, Integer.parseInt(get_year));

        int ngay_cua_tuan = calendar.get(Calendar.DAY_OF_WEEK);

        String dayofweek = sd_procedure_getdayofweek(ngay_cua_tuan);

        txt_ngaytrongtuan.setText(dayofweek);
    }

    
     public String sd_procedure_getdayofweek(int ngaycuatuan) {
        String ketqua = "";
        try {
            //Sử dụng thủ tục
            JDBC_Connect connection = new JDBC_Connect();
            Connection conn = (Connection) connection.connect();

            CallableStatement cStmt = null;

            cStmt = (CallableStatement) conn.prepareCall("{call get_day_of_week(?,?)}");
            cStmt.setInt(1, ngaycuatuan);
            cStmt.registerOutParameter(2, java.sql.Types.CHAR);
            cStmt.executeQuery(); //gọi thủ tục
            ketqua = cStmt.getString(2); //Lấy giá trị tham số OUT balance
        } catch (SQLException ex) {
            Logger.getLogger(JFramePhanCongCaTruc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketqua;
    }

    
    public void show_lich_lam_viec() throws ParseException{
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        String query_get_ds_lichlamviec = "select distinct ngaylamviec from lichlamviec llv join nhanvien nv on nv.id_NV = llv.id_NhanVien where nv.BiDuoiViec = 0 order by ngaylamviec";

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(query_get_ds_lichlamviec);
            ResultSet rs = pstm.executeQuery();
            
            //Xóa lịch làm việc cũ
            cb_lichlamviec.removeAllItems();
            while (rs.next()) {
                String ds_lichlamviec = rs.getString("ngaylamviec");
                
                //Chuyển đổi String to Date
                 //Instantiating the SimpleDateFormat class
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                //Parsing the given String to Date object
                Date date = formatter.parse(ds_lichlamviec);

                String ds_lichlamviec_format = new SimpleDateFormat("dd-MM-yyyy").format(date);
                
                cb_lichlamviec.addItem(ds_lichlamviec_format);
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
        jLabel1 = new javax.swing.JLabel();
        cb_lichlamviec = new javax.swing.JComboBox<>();
        btn_search_lichlamviec = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableResult = new javax.swing.JTable();
        btnphancongcatruc = new javax.swing.JButton();
        txt_ngaytrongtuan = new javax.swing.JTextField();
        btn_xoalichlamviec = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnback.setText("Trở Về");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/calendar-icon_1.png"))); // NOI18N
        jLabel2.setText("Thông Tin Lịch Làm Việc");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Danh sách lịch làm việc: ");

        btn_search_lichlamviec.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_search_lichlamviec.setText("Tìm Kiếm");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Ngày trong tuần:");

        tableResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "ID_NV", "Tên Nhân Viên", "Giới tính", "Ngày Làm Việc", "Ca Trực"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableResult);

        btnphancongcatruc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnphancongcatruc.setText("Phân Công Ca Trực");

        txt_ngaytrongtuan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_ngaytrongtuan.setForeground(new java.awt.Color(255, 51, 51));

        btn_xoalichlamviec.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_xoalichlamviec.setText("Xóa Lịch");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_ngaytrongtuan, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cb_lichlamviec, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(btn_search_lichlamviec)
                                .addGap(27, 27, 27)
                                .addComponent(btn_xoalichlamviec)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnback)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnphancongcatruc)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 857, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 10, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnback))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cb_lichlamviec, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_search_lichlamviec)
                    .addComponent(btn_xoalichlamviec))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_ngaytrongtuan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnphancongcatruc, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addContainerGap())
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
            java.util.logging.Logger.getLogger(JFrameLichLamViec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameLichLamViec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameLichLamViec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameLichLamViec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JFrameLichLamViec().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(JFrameLichLamViec.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_search_lichlamviec;
    private javax.swing.JButton btn_xoalichlamviec;
    private javax.swing.JButton btnback;
    private javax.swing.JButton btnphancongcatruc;
    private javax.swing.JComboBox<String> cb_lichlamviec;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableResult;
    private javax.swing.JTextField txt_ngaytrongtuan;
    // End of variables declaration//GEN-END:variables
}
