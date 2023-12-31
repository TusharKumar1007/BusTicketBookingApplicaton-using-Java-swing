/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package busapplication;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tushar
 */
public class AdminDelete extends javax.swing.JInternalFrame {
    String dbUsername="root";
    String dbrootPassword="";
    /**
     * Creates new form AdminDelete
     */
    public AdminDelete() {
        initComponents();
        adminDetails();
    }

    public void adminDetails() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bus";
        String dbUser = dbUsername;
        String dbPassword = dbrootPassword;
        try {
            var con = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            System.out.println("hello");
            String sql = "SELECT * FROM admin_details";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            DefaultTableModel model = (DefaultTableModel) admin_details.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
            }

        } catch (Exception e) {
            System.out.println(e);
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        admin_details = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        adminid = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Delete Admin Control");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(427, 30, -1, -1));

        admin_details.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Name", "User Name", "Password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(admin_details);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 235, 916, 343));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Admin Id");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 119, -1, -1));

        adminid.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        adminid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminidActionPerformed(evt);
            }
        });
        getContentPane().add(adminid, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 116, 260, -1));

        jButton1.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(612, 116, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\Tushar\\Downloads\\AdminDelete.jpg")); // NOI18N
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void adminidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminidActionPerformed
public void infoMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int id = Integer.parseInt(adminid.getText());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String databaseURL = "jdbc:mysql://localhost:3306/bus";
            Connection con = DriverManager.getConnection(databaseURL, dbUsername, dbrootPassword);
            Statement stat = con.createStatement();

            // Replace with the actual admin ID you want to delete
            String deleteSql = "DELETE FROM admin_details WHERE id = " + id;
            int rowsAffected = stat.executeUpdate(deleteSql);

            if (rowsAffected > 0) {
                System.out.println("Admin deleted successfully.");
                infoMessage("Admin Data Deleted successfully!!", title);
            } else {
                System.out.println("Admin with ID " + id + " not found.");
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error deleting admin.");
        }

    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable admin_details;
    private javax.swing.JTextField adminid;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
