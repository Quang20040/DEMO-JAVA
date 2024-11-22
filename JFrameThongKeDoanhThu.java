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
/**
 *
 * @author trant
 */
public class JFrameThongKeDoanhThu extends javax.swing.JFrame {

    /**
     * Creates new form JFrameThongKeDoanhThu
     */
    private DefaultTableModel Model;
    public JFrameThongKeDoanhThu() throws ParseException {
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
        
        //Đổ dữ liệu từ DB vào combo box ngaylapdonhang
        cb_box_ngaylap();
      
        
        btnlietkedanhsach.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(cbbox_ngaylapdonhang.getItemCount() == 0){
                    JOptionPane.showMessageDialog(rootPane, "Danh sách rỗng");
                }else{
                    //Lấy giá trị khi chọn trong menu dropdown thống kê theo ngày
                    String selectedItem = (String)cbbox_ngaylapdonhang.getSelectedItem();
                    //Chuyển đổi String to Date
                    //Instantiating the SimpleDateFormat class
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    //Parsing the given String to Date object
                    Date date = null;
                    try {
                        date = formatter.parse(selectedItem);
                    } catch (ParseException ex) {
                        Logger.getLogger(JFrameThongKeDoanhThu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    String ngay_lap_don = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    
                    txtdoanhthutheongay.setText(selectedItem);
                    lietkedanhsachDH(ngay_lap_don);
                    tongdoanhthutheongay(ngay_lap_don);
                }
            }
        });
        
        
        btnTimDonHang.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(cbbox_danhsachdonhang.getItemCount() == 0){
                    JOptionPane.showMessageDialog(rootPane, "Danh sách rỗng");
                }else{
                    //Lấy giá trị khi chọn trong menu dropdown thống kê theo ngày
                    String selectedItem = (String)cbbox_danhsachdonhang.getSelectedItem();
                    Model.setRowCount(0);
                    timdonhang(selectedItem);
                }
            }
        });
    }
    
    
    public void cb_box_ngaylap() throws ParseException{
          //Lấy tên sản phẩm từ bảng sản phẩm hiển thị vào bảng hóa đơn
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        String query_get_ngaylapdonhang = "SELECT distinct NgayLapDonHang from donhang order by NgayLapDonHang";
      
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(query_get_ngaylapdonhang);
            ResultSet rs = pstm.executeQuery();
            
            cbbox_ngaylapdonhang.removeAllItems();

            while(rs.next()){
                String ngaylapdonhang = rs.getString("NgayLapDonHang");
                //Chuyển đổi String to Date
                //Instantiating the SimpleDateFormat class
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                //Parsing the given String to Date object
                Date date_ngaylap = formatter.parse(ngaylapdonhang);

                String ngaylapdonhang_cb = new SimpleDateFormat("dd-MM-yyyy").format(date_ngaylap);
                cbbox_ngaylapdonhang.addItem(ngaylapdonhang_cb);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void tongdoanhthutheongay(String selectedItem){
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();
        
        //Tách ra tháng trong date selected
        String[] splits = selectedItem.split("-");
        String get_month = splits[1];
        txtdoanhthutheothang.setText("Tháng "+ get_month);

        String query_get_tongdonhang = "SELECT * from donhang where NgayLapDonHang = ?";
        String query_get_tongdonhang_theo_thang = "SELECT * from donhang where month(NgayLapDonHang) = ?";
        
        PreparedStatement pstm_tongdonhang = null;
        PreparedStatement pstm_tongdonhang_theo_thang = null;
        
        int sum = 0;
        int sum_theo_thang = 0;
        
         try{
            pstm_tongdonhang = conn.prepareStatement(query_get_tongdonhang);
            pstm_tongdonhang_theo_thang = conn.prepareStatement(query_get_tongdonhang_theo_thang);
            
            pstm_tongdonhang.setString(1, selectedItem);
            pstm_tongdonhang_theo_thang.setString(1, get_month);
            
            ResultSet rsx = pstm_tongdonhang.executeQuery();
            ResultSet rsx_theo_thang = pstm_tongdonhang_theo_thang.executeQuery();
            
            while(rsx.next()){
                 //Lấy ra tổng doanh thu theo ngày
                 sum += Integer.parseInt(rsx.getString("TongDonHang"));
           }
            
            while(rsx_theo_thang.next()){
                 //Lấy ra tổng doanh thu theo tháng
                 sum_theo_thang += Integer.parseInt(rsx_theo_thang.getString("TongDonHang"));
            }
            
            String tongdoanhthu = Integer.toString(sum);
            String tongdoanhthu_theo_thang = Integer.toString(sum_theo_thang);
            
            NumberFormat formatter = new DecimalFormat("#,###");
            String formattedNumber = formatter.format(Double.parseDouble(tongdoanhthu));
            String formattedNumber_theo_thang = formatter.format(Double.parseDouble(tongdoanhthu_theo_thang));
          
            txttongdoanhthu.setText(formattedNumber);
            txttongdoanhthutheothang.setText(formattedNumber_theo_thang);
         }catch(Exception e){
             e.printStackTrace();
         }

    }
    
    public void timdonhang(String selectedItem){
        JDBC_Connect connection = new JDBC_Connect();
        Connection conn = connection.connect();

        NumberFormat formatter = new DecimalFormat("#,###");
        String query_get_chitietdonhang = "SELECT * from chitietdonhang where id_DonHang = ?";
        
        
        
        PreparedStatement pstm = null;
       
        
        try {
            pstm = conn.prepareStatement(query_get_chitietdonhang);
           
            
            pstm.setString(1, selectedItem);
           
            
            ResultSet rs = pstm.executeQuery();
           
            int tong_HD = 0;
        
            while(rs.next()){
                String id = rs.getString("id");
                String soluong = rs.getString("SoLuong");
                String tenSP = rs.getString("TenSP");
                String gia = rs.getString("gia");
                String tongtien = rs.getString("TongTien");
                
                //Định dạng phân tách phần thập phân trong số tiền.
//                NumberFormat formatter = new DecimalFormat("#,###");
//                String formattedNumber = formatter.format(Double.parseDouble(gia_SP));
                String format_gia = formatter.format(Double.parseDouble(gia));
                String format_tongtien = formatter.format(Double.parseDouble(tongtien));
                Model.addRow(new Object[] { id, tenSP, soluong, format_gia, format_tongtien});
//                //Lấy ra tổng số sản phẩm
//                txtsum_SP.setText(Integer.toString(rs.getRow()));

                tong_HD += Integer.parseInt(tongtien);
            }
            
          
            String format_tonghoadon = formatter.format(Double.parseDouble(Integer.toString(tong_HD)));
            Model.addRow(new Object[] {"","","","Tổng Hóa Đơn",  format_tonghoadon});
         
            
          
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void lietkedanhsachDH(String selectedItem){
          JDBC_Connect connection = new JDBC_Connect();
          Connection conn = connection.connect();
          String query_get_danhsachdonhang = "SELECT id_DonHang from donhang where NgayLapDonHang = ?";
          PreparedStatement pstm = null;
          if(!selectedItem.equals("")){
                try {
                    pstm = conn.prepareStatement(query_get_danhsachdonhang);
                  
                    pstm.setString(1,selectedItem);

                    ResultSet rsx = pstm.executeQuery();
                    cbbox_danhsachdonhang.removeAllItems();
                    while(rsx.next()){
                        cbbox_danhsachdonhang.addItem(rsx.getString("id_DonHang"));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
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

        jSlider1 = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        btnback = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbbox_ngaylapdonhang = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableResult = new javax.swing.JTable();
        btnlietkedanhsach = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txttongdoanhthu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbbox_danhsachdonhang = new javax.swing.JComboBox<>();
        btnTimDonHang = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtdoanhthutheongay = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 50), new java.awt.Dimension(0, 50), new java.awt.Dimension(32767, 50));
        jLabel6 = new javax.swing.JLabel();
        txtdoanhthutheothang = new javax.swing.JLabel();
        txttongdoanhthutheothang = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/Chart-icon.png"))); // NOI18N
        jLabel2.setText("Thống Kê Doanh Thu");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 30, 643, -1));

        btnback.setText("Trở Về");
        getContentPane().add(btnback, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 14, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Thống kê theo ngày (dd--mm--yyyy): ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 166, 274, -1));

        cbbox_ngaylapdonhang.setToolTipText("");
        getContentPane().add(cbbox_ngaylapdonhang, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 160, 208, 35));

        tableResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID_SP", "Tên SP", "Số Lượng", "Giá", "Tổng Tiền"
            }
        ));
        jScrollPane1.setViewportView(tableResult);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 319, 771, 314));

        btnlietkedanhsach.setText("Liệt Kê Danh Sách");
        getContentPane().add(btnlietkedanhsach, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 207, 180, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Tổng Doanh Thu Theo Ngày:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 640, 200, 30));
        getContentPane().add(txttongdoanhthu, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 670, 153, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Danh Sách Đơn Hàng(ID): ");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 272, 274, -1));

        cbbox_danhsachdonhang.setToolTipText("");
        getContentPane().add(cbbox_danhsachdonhang, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 266, 131, 35));

        btnTimDonHang.setText("Tìm");
        getContentPane().add(btnTimDonHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(543, 266, -1, 35));

        jLabel5.setText("VNĐ");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 670, -1, 30));

        txtdoanhthutheongay.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        txtdoanhthutheongay.setForeground(new java.awt.Color(255, 51, 51));
        getContentPane().add(txtdoanhthutheongay, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 670, 130, 20));
        getContentPane().add(filler1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 640, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Tổng Doanh Thu Theo Tháng:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 640, 200, 30));

        txtdoanhthutheothang.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        txtdoanhthutheothang.setForeground(new java.awt.Color(255, 51, 51));
        getContentPane().add(txtdoanhthutheothang, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 670, 130, 20));
        getContentPane().add(txttongdoanhthutheothang, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 670, 153, -1));

        jLabel7.setText("VNĐ");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 670, -1, 30));

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
            java.util.logging.Logger.getLogger(JFrameThongKeDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameThongKeDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameThongKeDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameThongKeDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JFrameThongKeDoanhThu().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(JFrameThongKeDoanhThu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimDonHang;
    private javax.swing.JButton btnback;
    private javax.swing.JButton btnlietkedanhsach;
    private javax.swing.JComboBox<String> cbbox_danhsachdonhang;
    private javax.swing.JComboBox<String> cbbox_ngaylapdonhang;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTable tableResult;
    private javax.swing.JLabel txtdoanhthutheongay;
    private javax.swing.JLabel txtdoanhthutheothang;
    private javax.swing.JTextField txttongdoanhthu;
    private javax.swing.JTextField txttongdoanhthutheothang;
    // End of variables declaration//GEN-END:variables
}
