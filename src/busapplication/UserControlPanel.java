/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package busapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tushar
 */
public class UserControlPanel extends javax.swing.JFrame {
    String dbUsername="root";
    String dbrootPassword="tiger";
    /**
     * Creates new form UserControlPanel
     */
    String luser;

    public UserControlPanel() {
        initComponents();
        busSourceCBFillData();
        busDestinationCBFillData();
        busDetails();
        paymentTF.setEnabled(false);

    }

    public UserControlPanel(String loginuser) {
        initComponents();
        this.luser = loginuser;
        String getValue = jLabel1.getText();
        jLabel1.setText(getValue + " : " + loginuser);
    }

    public void infoMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void busDetails() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bus";
        String dbUser = dbUsername;
        String dbPassword = dbrootPassword;
        try {
            var con = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            System.out.println("hello");
            String sql = "SELECT * FROM bus_details";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            DefaultTableModel model = (DefaultTableModel) bus_details.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void showPrice() {
        String source = (String) sourceCB.getSelectedItem();
        String destination = (String) destinationCB.getSelectedItem();

        String jdbcUrl = "jdbc:mysql://localhost:3306/bus";
        String dbUser = dbUsername;
        String dbPassword = dbrootPassword;

        try {
            var con = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            System.out.println("hello");
            if (timeTF.getText().trim().equals("")) {
                infoMessage("Select Departure Time", "No Time");
                return;
            }

            String sql = "SELECT price FROM bus_details where bus_source='" + source + "' and bus_dest='" + destination + "' and depart_time='" + timeTF.getText() + "'";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            double totalCalPrice = 0.0; // Initialize the total price to zero

            while (rs.next()) {
                String price = rs.getString("price");
                if (price != null && isNumeric(price)) {
                    int passengerValue = Integer.parseInt(passengerNum.getText());
                    double calPrice = Double.parseDouble(price) * passengerValue;
                    totalCalPrice += calPrice;
                }
            }

            paymentTF.setText(String.valueOf(totalCalPrice));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void price() {
        String source = (String) sourceCB.getSelectedItem();
        String destination = (String) destinationCB.getSelectedItem();
        String firstname = (String) firstnameTF.getText();
        String lastname = (String) lastnameTF.getText();

        if (firstname.trim().equals("")) {
            infoMessage("Enter firstname and last name!", "No Name");
        }

        String jdbcUrl = "jdbc:mysql://localhost:3306/bus";
        String dbUser = dbUsername;
        String dbPassword = dbrootPassword;
        try {
            var con = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            System.out.println("hello");
            String sql = "SELECT price FROM bus_details where bus_source='" + source + "' and bus_dest='" + destination + "' and depart_time like'" + timeTF.getText() + "'";

            String sql2 = "SELECT bus_no FROM bus_details where bus_source='" + source + "' and bus_dest='" + destination + "' and depart_time='" + timeTF.getText() + "'";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            PreparedStatement st2 = con.prepareStatement(sql2);
            ResultSet rs2 = st2.executeQuery();

            while (rs.next()) {
                String price = rs.getString("price");
                if (price != null && isNumeric(price)) {
                    int passengerValue = Integer.parseInt(passengerNum.getText());
                    double calPrice = Double.parseDouble(price) * passengerValue;
                    paymentTF.setText(String.valueOf(calPrice));

                    String BusNo = "";
                    while (rs2.next()) {
                        BusNo = rs2.getString("bus_no");
                    }

                    String insertQuery = "INSERT INTO passenger (firstname, lastname, payment, source, dest, busnumber) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStat = con.prepareStatement(insertQuery)) {
                        insertStat.setString(1, firstname);
                        insertStat.setString(2, lastname);
                        insertStat.setString(3, String.valueOf(calPrice));
                        insertStat.setString(4, source);
                        insertStat.setString(5, destination);
                        insertStat.setString(6, BusNo);

                        int rowsAffected = insertStat.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Data inserted successfully.");
                            infoMessage("Successfully booked!!", "Success");

                        } else {
                            System.out.println("Failed to insert data.");
                            // Optionally, you can show an error message to the user.
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void busSourceCBFillData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String databaseURL = "jdbc:mysql://localhost:3306/bus";
            Connection con = DriverManager.getConnection(databaseURL, dbUsername, dbrootPassword);
            Statement stat = con.createStatement();
            String selectQuery = "select distinct(bus_source) as bus_source from bus_details";
            ResultSet rs = stat.executeQuery(selectQuery);
            while (rs.next()) {
                sourceCB.addItem(rs.getString("bus_source"));

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void busDestinationCBFillData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String databaseURL = "jdbc:mysql://localhost:3306/bus";
            Connection con = DriverManager.getConnection(databaseURL, dbUsername, dbrootPassword);
            Statement stat = con.createStatement();
            String selectQuery = "select distinct(bus_dest) as bus_dest  from bus_details";
            ResultSet rs = stat.executeQuery(selectQuery);
            while (rs.next()) {
                destinationCB.addItem(rs.getString("bus_dest"));

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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        firstnameTF = new javax.swing.JTextField();
        lastnameTF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        sourceCB = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        destinationCB = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        timeTF = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        paymentTF = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        bus_details = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        passengerNum = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Book Your Seat");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(358, 42, 230, -1));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Firstname");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 124, 140, -1));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Lastname");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 124, 110, -1));

        firstnameTF.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        firstnameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstnameTFActionPerformed(evt);
            }
        });
        getContentPane().add(firstnameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 121, 181, -1));

        lastnameTF.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        getContentPane().add(lastnameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 120, 197, -1));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Source");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 194, 90, -1));

        sourceCB.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        getContentPane().add(sourceCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 191, 194, -1));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Destination");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 249, 150, -1));

        destinationCB.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        destinationCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destinationCBActionPerformed(evt);
            }
        });
        getContentPane().add(destinationCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 246, 194, -1));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Departure Time");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(531, 194, 200, -1));

        timeTF.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        timeTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeTFActionPerformed(evt);
            }
        });
        getContentPane().add(timeTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 190, 202, -1));

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Pay amount");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 303, 140, -1));

        paymentTF.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        paymentTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentTFActionPerformed(evt);
            }
        });
        getContentPane().add(paymentTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(263, 299, 193, -1));

        bus_details.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Bus No", "Source", "Destination", "Departure Time", "Price", "Seat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(bus_details);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 348, 794, 364));

        jButton1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jButton1.setText("Confirm Booking");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 300, -1, -1));

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Number of passenger");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(478, 249, 260, -1));

        passengerNum.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        getContentPane().add(passengerNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 250, 202, -1));

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("Show Amount");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(478, 303, -1, -1));

        jButton3.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jButton3.setText("Login Page");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 45, -1, -1));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon("C:\\Users\\Tushar\\Downloads\\payment.jpg")); // NOI18N
        jLabel9.setText("jLabel9");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void destinationCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destinationCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_destinationCBActionPerformed

    private void timeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timeTFActionPerformed

    private void paymentTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paymentTFActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        String firstname = firstnameTF.getText();
        String numPessenger = passengerNum.getText();
        String source = (String) sourceCB.getSelectedItem();
        String destination = (String) destinationCB.getSelectedItem();
        String seat = "";

        if (numPessenger.trim().equals("")) {
            infoMessage("Enter num of passengers", "No Passenger");
            return;
        }

        if (firstname.trim().equals("")) {
            infoMessage("Enter firstname and last name!", "No Name");
            return;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String databaseURL = "jdbc:mysql://localhost:3306/bus";
            Connection con = DriverManager.getConnection(databaseURL, dbUsername, dbrootPassword);
            Statement stat = con.createStatement();

            // Select the current seat count
            String selectSql = "SELECT seat FROM bus_details WHERE bus_source='" + source + "' AND bus_dest='" + destination + "' AND depart_time='" + timeTF.getText() + "'";
            ResultSet rs = stat.executeQuery(selectSql);

            while (rs.next()) {
                seat = rs.getString("seat");
            }

            int passengerValue = Integer.parseInt(numPessenger);
            int seatValue = Integer.parseInt(seat);

            if (passengerValue > seatValue) {
                infoMessage("Not Enough Seats Available", "Over Passenger");
                return;
            }

            // Deduct the passengers and update the seats
            int updatedSeatValue = seatValue - passengerValue;
            String updateSql = "UPDATE bus_details SET seat=" + updatedSeatValue + " WHERE bus_source='" + source + "' AND bus_dest='" + destination + "' AND depart_time='" + timeTF.getText() + "'";
            int rowsAffected = stat.executeUpdate(updateSql);

            if (rowsAffected > 0) {
                infoMessage("Seats updated successfully", "Success");
            } else {
                infoMessage("Failed to update seats", "Error");
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
            infoMessage("Something went wrong, try again later", "Error");
        }

        price();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void firstnameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstnameTFActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String numPessenger = passengerNum.getText();
        if (numPessenger.trim().equals("")) {
            infoMessage("Enter num of passenger", "No Pessenger");
            return;
        }
        showPrice();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        dispose();
        UserLogin u1 = new UserLogin();
        u1.setLocationRelativeTo(null);
        u1.setVisible(true);

    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(UserControlPanel.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserControlPanel.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserControlPanel.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserControlPanel.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserControlPanel().setVisible(true);
            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bus_details;
    private javax.swing.JComboBox<String> destinationCB;
    private javax.swing.JTextField firstnameTF;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lastnameTF;
    private javax.swing.JTextField passengerNum;
    private javax.swing.JTextField paymentTF;
    private javax.swing.JComboBox<String> sourceCB;
    private javax.swing.JTextField timeTF;
    // End of variables declaration//GEN-END:variables
}
