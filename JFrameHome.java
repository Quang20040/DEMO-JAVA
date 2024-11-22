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
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author trant
 */
public class JFrameHome extends javax.swing.JFrame {

    /**
     * Creates new form JFrameHome
     */
    public JFrameHome() {
        initComponents();
        setTitle("Quản lý của hàng bánh Pizza");
        setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.ORANGE); //Set màu cho background giao diện
        
 
        
        
          btnQLSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                         new JFrameSanPham().setVisible(true);
                    }
                });
            }
        });
        
         btnchangepass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                         new JFrameDoiMatKhau().setVisible(true);
                    }
                });
            }
        });
         
         
        btn_dangxuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check_log_out = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn đăng xuất không?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (check_log_out == JOptionPane.YES_OPTION) {
                    setVisible(false);
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            new JFrameDangNhap().setVisible(true);
                        }
                    });
                } else if (check_log_out == JOptionPane.NO_OPTION) {
                    setVisible(false);
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            new JFrameHome().setVisible(true);
                        }
                    });
                }
            }
        });
        
        btnquanlylichlamviec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new JFrameLichLamViec().setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(JFrameHome.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        });
        
       
          btnquanlyNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new JFrameNhanVien().setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(JFrameHome.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        });
          
           btnthongkedoanhthu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new JFrameThongKeDoanhThu().setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(JFrameHome.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        });
           
          
            
            
        btntaodon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                         new JFrameTaoDonHang().setVisible(true);
                    }
                });
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnchangepass = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btntaodon = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnthongkedoanhthu = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btn_dangxuat = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnquanlylichlamviec = new javax.swing.JButton();
        hinhanhpizza = new javax.swing.JLabel();
        btnQLSP = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnquanlyNV = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(846, 564));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/pass-icon.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 100, 100));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/PIZZA_BANNER.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 846, 215));

        btnchangepass.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnchangepass.setText("Đổi mật khẩu");
        btnchangepass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnchangepassActionPerformed(evt);
            }
        });
        getContentPane().add(btnchangepass, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 150, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/Order-history-icon.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 100, 110));

        btntaodon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btntaodon.setText("Tạo đơn hàng");
        btntaodon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntaodonActionPerformed(evt);
            }
        });
        getContentPane().add(btntaodon, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 330, 130, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/Chart-icon.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 410, 100, 100));

        btnthongkedoanhthu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnthongkedoanhthu.setText("Thống kê doanh thu");
        btnthongkedoanhthu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthongkedoanhthuActionPerformed(evt);
            }
        });
        getContentPane().add(btnthongkedoanhthu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/nhanvienpizza.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 400, 100, 130));

        btn_dangxuat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_dangxuat.setText("Đăng Xuất");
        getContentPane().add(btn_dangxuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 400, 140, 40));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/calendar-icon_1.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 400, 130, 130));

        btnquanlylichlamviec.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnquanlylichlamviec.setText("Quản lý Lịch làm việc");
        getContentPane().add(btnquanlylichlamviec, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 520, -1, 30));

        hinhanhpizza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/Pizza-icon.png"))); // NOI18N
        getContentPane().add(hinhanhpizza, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 200, 140, 140));

        btnQLSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQLSP.setText("Quản lý Sản Phẩm");
        btnQLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSPActionPerformed(evt);
            }
        });
        getContentPane().add(btnQLSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 330, 170, 30));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset_img/icons8-log-out-64.png"))); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 300, 100, 110));

        btnquanlyNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnquanlyNV.setText("Quản lý Nhân Viên");
        getContentPane().add(btnquanlyNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 520, 180, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnchangepassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnchangepassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnchangepassActionPerformed

    private void btntaodonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntaodonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btntaodonActionPerformed

    private void btnthongkedoanhthuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthongkedoanhthuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnthongkedoanhthuActionPerformed

    private void btnQLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnQLSPActionPerformed

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
            java.util.logging.Logger.getLogger(JFrameHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnQLSP;
    private javax.swing.JButton btn_dangxuat;
    private javax.swing.JButton btnchangepass;
    private javax.swing.JButton btnquanlyNV;
    private javax.swing.JButton btnquanlylichlamviec;
    private javax.swing.JButton btntaodon;
    private javax.swing.JButton btnthongkedoanhthu;
    private javax.swing.JLabel hinhanhpizza;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables
}
