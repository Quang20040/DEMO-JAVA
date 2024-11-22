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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.sql.CallableStatement;

/**
 *
 * @author trant
 */
public class JFramePhanCongCaTruc extends javax.swing.JFrame {

    /**
     * Creates new form JFramePhanCongCaTruc
     */
    public JFramePhanCongCaTruc() {
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
                            new JFrameLichLamViec().setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(JFramePhanCongCaTruc.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        });

        //Đẩy dữ liệu nhân viên vào combo box DS nhân viên
        show_danhsachnhanvien();

        btn_xacnhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((!catruc_sang.isSelected()) && (!catruc_chieu.isSelected())) || (txt_date.getDate().toString().isEmpty())) {
                    JOptionPane.showMessageDialog(rootPane, "Vui lòng hoàn thành đầy đủ thông tin!");
                } else {
                    //Hàm lấy ra id ca trực tương ứng
                    String ten_catruc = "";
                    if (catruc_sang.isSelected()) {
                        ten_catruc = "Sáng";
                    }

                    if (catruc_chieu.isSelected()) {
                        ten_catruc = "Chiều";
                    }

                    //Lấy ra giá trị id_catruc
                    int id_ca_truc = find_id_catruc(ten_catruc);

                    //Lấy ra giá trị id_nhanvien khi chọn trong cb dropdown nhân viên
                    String tennv_from_cb_dsnhanvien = (String) cb_danhsachnhanvien.getSelectedItem();
                    int id_nv = find_id_nhanvien(tennv_from_cb_dsnhanvien);

                    //Lấy ra giá trị ngày làm việc từ JDate
                    SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");
                    String ngaylamviec = format_date.format(txt_date.getDate());

                    themvaoDB_lichlamviec(id_nv, id_ca_truc, ngaylamviec);
                }

            }
        });

        btn_find_day.addActionListener(new ActionListener() {
            private Object LocalDate;

            @Override
            public void actionPerformed(ActionEvent e) {
                //Lấy ra giá trị ngày làm việc từ JDate
                SimpleDateFormat format_date = new SimpleDateFormat("dd-MM-yyyy");
                String ngaylamviec = format_date.format(txt_date.getDate());

//              Dùng hàm split để tách chuỗi
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

                txtdayofweek.setText(dayofweek);

            }
        });

    }

    public void themvaoDB_lichlamviec(int id_nv, int id_ca_truc, String ngaylamviec) {
        //Thực hiện đưa dữ liệu vào DB
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();
        //Câu lệnh truy vấn đưa dữ liệu vào table lichlamviec
        String query_add_to_lichlamviec = "insert into lichlamviec(id_NhanVien, id_CaTruc, ngaylamviec) values(?, ?, ?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(query_add_to_lichlamviec);

            pstm.setInt(1, id_nv);
            pstm.setInt(2, id_ca_truc);
            pstm.setString(3, ngaylamviec);

            int row = pstm.executeUpdate();

            if (row != 0) {
                JOptionPane.showMessageDialog(rootPane, "Thêm lịch làm việc thành công!");
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new JFrameLichLamViec().setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(JFramePhanCongCaTruc.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPane, "Thêm lịch làm việc thất bại!");

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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

    public int find_id_nhanvien(String tennv_from_cb_dsnhanvien) {
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        String query_get_id_nhanvien = "select id_NV from nhanvien where HoTenNV = ?";

        PreparedStatement pstm = null;
        String id_nv = "";

        try {
            pstm = conn.prepareStatement(query_get_id_nhanvien);
            pstm.setString(1, tennv_from_cb_dsnhanvien);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                id_nv = rs.getString("id_NV");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Integer.parseInt(id_nv);
    }

    public int find_id_catruc(String ten_catruc) {
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        String query_get_id_catruc = "select id_CaTruc from catruc where ten_catruc = ?";

        PreparedStatement pstm = null;
        String id_ca_truc = "";

        try {
            pstm = conn.prepareStatement(query_get_id_catruc);
            pstm.setString(1, ten_catruc);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                id_ca_truc = rs.getString("id_CaTruc");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Integer.parseInt(id_ca_truc);
    }

    public void show_danhsachnhanvien() {
        //Lấy tên nhân viên từ DB hiển thị combo box nhân viên
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        String query_get_dsnhanvien = "SELECT HoTenNV from nhanvien where BiDuoiViec = 0";

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(query_get_dsnhanvien);
            ResultSet rs = pstm.executeQuery();

            cb_danhsachnhanvien.removeAllItems();

            while (rs.next()) {
                String ds_nhanvien = rs.getString("HoTenNV");

                cb_danhsachnhanvien.addItem(ds_nhanvien);
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

        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        btnback = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtdayofweek = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        catruc_chieu = new javax.swing.JRadioButton();
        catruc_sang = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        cb_danhsachnhanvien = new javax.swing.JComboBox<>();
        txt_date = new com.toedter.calendar.JDateChooser();
        btn_xacnhan = new javax.swing.JButton();
        btn_find_day = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnback.setText("Trở Về");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/calendar-icon_1.png"))); // NOI18N
        jLabel2.setText("Phân Công Ca Trực");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Ngày Làm Việc: ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Ngày Trong Tuần: ");

        txtdayofweek.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtdayofweek.setForeground(new java.awt.Color(255, 51, 51));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Ca Trực:");

        buttonGroup1.add(catruc_chieu);
        catruc_chieu.setText("Chiều");

        buttonGroup1.add(catruc_sang);
        catruc_sang.setText("Sáng");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Nhân Viên Phụ Trách Ca Làm:");

        txt_date.setDateFormatString("dd-MM-yyyy");

        btn_xacnhan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_xacnhan.setText("Xác Nhận");

        btn_find_day.setText("Tìm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnback)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(149, 149, 149)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtdayofweek, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(btn_find_day, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(txt_date, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(148, 148, 148)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(catruc_sang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(catruc_chieu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(149, 149, 149)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_xacnhan, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                                    .addComponent(cb_danhsachnhanvien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(btnback))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtdayofweek, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_find_day, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(catruc_chieu)
                    .addComponent(catruc_sang)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_danhsachnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(40, 40, 40)
                .addComponent(btn_xacnhan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151))
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
            java.util.logging.Logger.getLogger(JFramePhanCongCaTruc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFramePhanCongCaTruc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFramePhanCongCaTruc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFramePhanCongCaTruc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFramePhanCongCaTruc().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_find_day;
    private javax.swing.JButton btn_xacnhan;
    private javax.swing.JButton btnback;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton catruc_chieu;
    private javax.swing.JRadioButton catruc_sang;
    private javax.swing.JComboBox<String> cb_danhsachnhanvien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private com.toedter.calendar.JDateChooser txt_date;
    private javax.swing.JTextField txtdayofweek;
    // End of variables declaration//GEN-END:variables
}
