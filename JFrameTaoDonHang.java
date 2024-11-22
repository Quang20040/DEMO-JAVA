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
import java.util.Locale;
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author trant
 */
public class JFrameTaoDonHang extends javax.swing.JFrame {

    private DefaultTableModel Model;

    /**
     * Creates new form JFrameTaoDonHang
     */
    public JFrameTaoDonHang() {
        initComponents();
        setTitle("Quản lý của hàng bánh Pizza");
        setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.ORANGE); //Set màu cho background giao diện
        txttongdonhang_normal.setVisible(false);
        
        //Định dạng ngày
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        
        txtngaytaodon.setText(date);
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
        
        btnthemvaoHD.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                 //Lấy giá trị khi chọn trong menu dropdown
                String selectedItem = (String)menudropdown.getSelectedItem();
                themvaoHD(selectedItem);
            }
        });

        
        btntaodonhang.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //Tạo đơn hàng
                int row_count = 0;
                try {
                    row_count = taodonhang();
                } catch (ParseException ex) {
                    Logger.getLogger(JFrameTaoDonHang.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Tạo chi tiết đơn hàng
                if(row_count > 0){
                    taochitietdonhang(row_count);
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Không thể tạo đơn hàng !");
                }
         
            }
        });
        
        //Lấy tên sản phẩm từ bảng sản phẩm hiển thị vào bảng hóa đơn
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        String query_get_ten_SP = "SELECT TenSP from sanpham";
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(query_get_ten_SP);
            ResultSet rs = pstm.executeQuery();
            
        
            while(rs.next()){
                String tenSP = rs.getString("TenSP");
                menudropdown.addItem(tenSP);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void themvaoHD(String selectedItem) {
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        String soluong_input = txtsoluong.getText();
        int soluong_SP = Integer.parseInt(soluong_input);
        
        if (soluong_SP < 1) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập với số lượng >= 1");
        }else if(soluong_SP > 25){
            JOptionPane.showMessageDialog(rootPane, "Mỗi sản phẩm chị được mua số lượng tối đa là 25");
        }else {
            String query_get_SP = "SELECT * from sanpham where tenSP = ?";
            PreparedStatement pstm = null;
            
            //Định dạng số tiền không có dấu ngăn cách
//            NumberFormat formatter1 = new DecimalFormat("###");
            //Định dạng số tiền có dấu ngăn cách
//            NumberFormat formatter2 = new DecimalFormat("#,###");
            try {
                pstm = conn.prepareStatement(query_get_SP);

                pstm.setString(1, selectedItem);

                ResultSet rs = pstm.executeQuery();

               
                while (rs.next()) {
                    String id = rs.getString("id");
                    String ten_SP = rs.getString("TenSP");
                    String gia_SP = rs.getString("GiaSP");

                    int check_not_exist = 0;
                    
                    
                    //Sử dụng vòng lặp để tìm sản phẩm đã thêm trước đó sau đó cập nhật số lượng lên
                    if (tableResult.getRowCount() > 0) {
                        for (int i = 0; i < tableResult.getRowCount(); i++) {
                            String id_from_table = tableResult.getValueAt(i, 0).toString();
                            String soluong_from_table = tableResult.getValueAt(i, 3).toString();
                            String tongtien_from_table = tableResult.getValueAt(i, 4).toString();
//                            
                            if (id.equals(id_from_table)) {
                                if((Integer.parseInt(soluong_from_table) + soluong_SP) > 25){
                                    JOptionPane.showMessageDialog(rootPane, "Mỗi sản phẩm chị được mua số lượng tối đa là 25");
                                }else{
                                    Model.setValueAt(Integer.parseInt(soluong_from_table) + soluong_SP, i, 3);
                                    int sotien = (Integer.parseInt(soluong_from_table) + soluong_SP) * Integer.parseInt(gia_SP);


//                                    String formattedNumber = formatter2.format(Double.parseDouble(Double.toString(sotien)));
//                                    System.out.println(formattedNumber);
//                                    String tien_new = formatter2.format(sotien);
                                    Model.setValueAt(sotien, i, 4);
                                    break;
                                }
                            } else {
                                check_not_exist++;
                            }
                            
                        }

                        if (check_not_exist == tableResult.getRowCount()) {

                            int tong_tien = Integer.parseInt(gia_SP) * soluong_SP;
//                            String formattedNumber_2 = formatter2.format(Double.parseDouble(Double.toString(tong_tien)));

                            Model.addRow(new Object[]{id, ten_SP, gia_SP, soluong_SP, tong_tien});
                        }

                    } else {
                        int tong_tien = Integer.parseInt(gia_SP) * soluong_SP;
//                        String formattedNumber_2 = formatter2.format(Double.parseDouble(Integer.toString(tong_tien)));


                        Model.addRow(new Object[]{id, ten_SP, gia_SP, soluong_SP, tong_tien});

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            Double sum = 0.0;
            int sum1=0;
            for (int i = 0; i < tableResult.getRowCount(); i++) {
                  String price_from_table = tableResult.getValueAt(i, 4).toString();
//                  String formattedNumber_3 = formatter1.format(Double.parseDouble(price_from_table));
//                  sum += Long.parseLong(formattedNumber_3) * 1000;  
                    sum+=Double.parseDouble(price_from_table);
                    sum1+=Integer.parseInt(price_from_table);
            }
            
//            NumberFormat formatter = new DecimalFormat("#,###");
//            String formattedNumber = formatter.format(Double.parseDouble(Double.toString(sum)));
            
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            String str1 = currencyVN.format(sum);
           
//          String formattedNumber_tonghoadon = formatter2.format(Double.parseDouble(Integer.toString(sum)));
            txtTongDonHang.setText(str1);
            txttongdonhang_normal.setText(Integer.toString(sum1));
        }
    }
    
    public int check_exist_id_SP(String id){
        for(int i=0 ;i < tableResult.getRowCount();i++){
            String id_from_table = tableResult.getValueAt(i, 0).toString();
            if(id_from_table == id){
                return 1;
            }
        }
        return 0;
    }
    
    public void taochitietdonhang(int row_count) {
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();
        String query_add_to_hoadon = "insert into chitietdonhang(id_DonHang, SoLuong, TenSP, gia, TongTien) values(?, ?,?,?,?)";

        String query_get_id_DonHang = "SELECT id_DonHang from donhang where id_DonHang = ?";
 
       PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(query_get_id_DonHang);
            pstm.setString(1, Integer.toString(row_count));
            
            ResultSet rs = pstm.executeQuery();
            String last_id_donhang = new String();

            int check = 0;
            //Lấy dòng id cuối đơn hàng
            while (rs.next()) {
                check = 1;
                last_id_donhang = rs.getString("id_DonHang");
            }

            int row = 0;
            if (check == 1) {
                for (int i = 0; i < tableResult.getRowCount(); i++) {
                    String soluong_from_table = tableResult.getValueAt(i, 3).toString();
                    String tensp_from_table = tableResult.getValueAt(i, 1).toString();
                    String giasp_from_table = tableResult.getValueAt(i, 2).toString();
                    String tongtien_from_table = tableResult.getValueAt(i, 4).toString();

                    pstm = conn.prepareStatement(query_add_to_hoadon);

                    pstm.setString(1, last_id_donhang);
                    pstm.setString(2, soluong_from_table);
                    pstm.setString(3, tensp_from_table);
                    pstm.setString(4, giasp_from_table);
                    pstm.setString(5, tongtien_from_table);

                    row = pstm.executeUpdate();
                }

                if (row != 0) {
                    JOptionPane.showMessageDialog(rootPane, "Tạo đơn hàng thành công ! \nVào thống kê doanh thu để xem lại chi tiết các đơn hàng");
                    setVisible(false);
                    java.awt.EventQueue.invokeLater(new Runnable() {
                       public void run() {
                            new JFrameTaoDonHang().setVisible(true);
                       }
                   });
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Tạo đơn hàng thất bại !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public int taodonhang() throws ParseException{
        //Phần đơn hàng
        String tongdonhang = txttongdonhang_normal.getText();
       
        //Định dạng ngày
        String ngaylapdon = txtngaytaodon.getText();
        //Chuyển đổi String to Date
         //Instantiating the SimpleDateFormat class
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //Parsing the given String to Date object
        Date date = formatter.parse(ngaylapdon);
        
        String ngaytaodonhang = new SimpleDateFormat("yyyy-MM-dd").format(date);
     
        DecimalFormat x = new DecimalFormat("###");
        int row_count = 0;
        
        
        Double tongdonhang_number = Double.valueOf(tongdonhang);

        if(tongdonhang_number == 0.0){
            JOptionPane.showMessageDialog(rootPane, "Vui lòng thêm sản phẩm trước khi tạo đơn hàng !");
        }else{
            JDBC_Connect connection = new JDBC_Connect();
            Connection conn = connection.connect();
            //Câu lệnh truy vấn tạo đơn hàng
            String query_add_to_donhang = "insert into donhang(NgayLapDonHang, TongDonHang) values(?, ?)";
            
            //Câu lệnh truy vấn lấy số hàng trong bảng dữ liệu đơn hàng
            String query_get_row_count_DH = "SELECT count(*) from donhang";
            
            PreparedStatement pstm = null;
            try {
                //Tạo đơn hàng
                tongdonhang = x.format(tongdonhang_number);
                int TongDonHang = (Integer.parseInt(tongdonhang));
                
                pstm = conn.prepareStatement(query_add_to_donhang);
                
                pstm.setString(1, ngaytaodonhang);
                pstm.setString(2, Integer.toString(TongDonHang));
                
                
                int row = pstm.executeUpdate();
//               
                if(row != 0){
                    ResultSet rs = pstm.executeQuery(query_get_row_count_DH);
                    rs.next();
                    row_count = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return row_count;
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
        menudropdown = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtsoluong = new javax.swing.JTextField();
        btnthemvaoHD = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableResult = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtTongDonHang = new javax.swing.JLabel();
        btntaodonhang = new javax.swing.JButton();
        txtngaytaodon = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txttongdonhang_normal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/Order-history-icon.png"))); // NOI18N
        jLabel2.setText("Tạo Đơn Hàng");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 6, 468, -1));

        btnback.setText("Trở Về");
        getContentPane().add(btnback, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 15, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Sản Phẩm:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 154, -1, -1));

        menudropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menudropdownActionPerformed(evt);
            }
        });
        getContentPane().add(menudropdown, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 180, 35));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Số lượng:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 201, 70, -1));

        txtsoluong.setText("1");
        getContentPane().add(txtsoluong, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 180, 30));

        btnthemvaoHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnthemvaoHD.setText("Thêm vào Hóa Đơn");
        getContentPane().add(btnthemvaoHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 260, -1, 35));

        tableResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên Sản Phẩm", "Giá", "Số Lượng", "Tổng tiền"
            }
        ));
        jScrollPane1.setViewportView(tableResult);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(308, 145, 520, 287));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Tổng đơn hàng:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(308, 460, 128, -1));

        txtTongDonHang.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtTongDonHang.setForeground(new java.awt.Color(51, 51, 255));
        txtTongDonHang.setText("0 ");
        getContentPane().add(txtTongDonHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 450, 150, -1));

        btntaodonhang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btntaodonhang.setText("Xác Nhận Tạo Đơn Hàng");
        getContentPane().add(btntaodonhang, new org.netbeans.lib.awtextra.AbsoluteConstraints(308, 510, 196, 45));
        getContentPane().add(txtngaytaodon, new org.netbeans.lib.awtextra.AbsoluteConstraints(684, 521, 138, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Ngày tạo đơn:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(594, 524, 84, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/360_F_361148878_hsDMN17NgJqM8KxRfd0ONsx6kbslPEYx.jpg"))); // NOI18N
        jLabel7.setText("jLabel7");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 310, 300, -1));
        getContentPane().add(txttongdonhang_normal, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 460, 140, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menudropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menudropdownActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menudropdownActionPerformed

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
            java.util.logging.Logger.getLogger(JFrameTaoDonHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameTaoDonHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameTaoDonHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameTaoDonHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameTaoDonHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnback;
    private javax.swing.JButton btntaodonhang;
    private javax.swing.JButton btnthemvaoHD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> menudropdown;
    private javax.swing.JTable tableResult;
    private javax.swing.JLabel txtTongDonHang;
    private javax.swing.JTextField txtngaytaodon;
    private javax.swing.JTextField txtsoluong;
    private javax.swing.JLabel txttongdonhang_normal;
    // End of variables declaration//GEN-END:variables

//    private static class FormattingDate {
//
//        public FormattingDate() {
//        }
//    }
}
