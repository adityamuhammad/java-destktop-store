/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mstr_customer;

import Class.DBO;
import Class.mstr_customer;
import java.awt.Dimension;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Aditya
 */
public class _List extends javax.swing.JInternalFrame {
    
    private boolean append;
    private DefaultTableModel _tempttrs_mstr_customer;
    Connection _Cnn;
    String _User;
    /**
     * Creates new form _List
     */
    public _List() {
        initComponents();
        
        String[] kolomtbl = {"CUSTOMER ID","NAMA", "ALAMAT", "TELEPON"};
        _tempttrs_mstr_customer = new DefaultTableModel(null, kolomtbl) {
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
            };
            
            public Class getColumnClass(int columnIndex){
                return types[columnIndex];
            }
            
            public boolean isCellEditable(int row, int col){
                int kol = _tempttrs_mstr_customer.getColumnCount();
                return (col < kol) ? false : true;
            }
        };
        tbl_mstr_customer.setModel(_tempttrs_mstr_customer);
        loadData();
    }
    
    public void hapusRowData() {
        int row = _tempttrs_mstr_customer.getColumnCount();
        while (_tempttrs_mstr_customer.getRowCount() > 0) {
            _tempttrs_mstr_customer.setRowCount(0);
        }
    }
    
    private void loadData(){
        String _sql = "";
        DBO vcon = new DBO();
        vcon.makeConnect();
        hapusRowData();
        try {
            _Cnn = vcon.vkoneksi;
            if(txtcari.getText().equals("")){
                _sql ="SELECT cust_id, nama, alamat, telp FROM mstr_customer";
            } else {
                _sql = "SELECT * FROM mstr_customer WHERE"
                        + " cust_id LIKE '%"+txtcari.getText()+"%' OR"
                        + " nama LIKE '%"+txtcari.getText()+"%' OR"
                        + " alamat LIKE '%"+txtcari.getText()+"%' OR"
                        + " telp LIKE '%"+txtcari.getText()+"%'";
            }
            System.out.println(_sql);
            Statement st = _Cnn.createStatement();
            ResultSet rst = st.executeQuery(_sql);
            while (rst.next()){
                String cust_id = rst.getString(1);
                String nama = rst.getString(2);
                String alamat = rst.getString(3);
                String telp = rst.getString(4);
                Object[] data = {cust_id,nama,alamat,telp};
                _tempttrs_mstr_customer.addRow(data);
            }
            tbl_mstr_customer.getColumnModel().getColumn(0).setPreferredWidth(40);
            tbl_mstr_customer.getColumnModel().getColumn(1).setPreferredWidth(210);
            tbl_mstr_customer.getColumnModel().getColumn(3).setPreferredWidth(100);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error"+e);
        }
    }
    
    ActionListener actionlist;
    private javax.swing.Timer timer = new javax.swing.Timer(10, actionlist);
    
    public void refresh(){
        try {
            loadData();
            ActionListener acl = new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    timer.stop();
                }
            };
            timer = new javax.swing.Timer(10,acl);
            timer.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR"+e);
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

        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jLabel1 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        bUbah = new javax.swing.JButton();
        bTambah = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        bRef = new javax.swing.JButton();
        bKeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_mstr_customer = new javax.swing.JTable();

        jXTitledPanel1.setBackground(new java.awt.Color(0, 51, 102));
        jXTitledPanel1.setTitle("_List Data Customer");

        jLabel1.setText("Pencarian");

        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcariKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcariKeyTyped(evt);
            }
        });

        bUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/if_pencil-01_374624.png"))); // NOI18N
        bUbah.setText("Ubah");
        bUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbahActionPerformed(evt);
            }
        });

        bTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/if_list-add_118777.png"))); // NOI18N
        bTambah.setText("Tambah");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        bHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/if_basket_1814090.png"))); // NOI18N
        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        bRef.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/if_view-refresh_118801.png"))); // NOI18N
        bRef.setText("Refresh");
        bRef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefActionPerformed(evt);
            }
        });

        bKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/if_40_Close_Switch_Off_Power_Switcher_Button_2142692.png"))); // NOI18N
        bKeluar.setText("Keluar");
        bKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKeluarActionPerformed(evt);
            }
        });

        tbl_mstr_customer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbl_mstr_customer);

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(bTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bRef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bUbah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 345, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addComponent(bUbah)
                        .addGap(18, 18, 18)
                        .addComponent(bTambah)
                        .addGap(18, 18, 18)
                        .addComponent(bHapus)
                        .addGap(18, 18, 18)
                        .addComponent(bRef)
                        .addGap(18, 18, 18)
                        .addComponent(bKeluar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKeluarActionPerformed
        this.dispose();
    }//GEN-LAST:event_bKeluarActionPerformed

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
        try {
            _Entry f = new _Entry("","","Add");
            Dimension parentSize = this.getSize();
            f.setLocation((parentSize.width)/2, (parentSize.height)/2);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            f.b_keluar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    refresh();
                }
            });
            f.b_simpan.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    refresh();
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_bTambahActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        String kode = tbl_mstr_customer.getValueAt(tbl_mstr_customer.getSelectedRow(), 0).toString();
        int pilih = JOptionPane.showConfirmDialog(this, "Hapus data user :"+kode, "KONFIRMASI", JOptionPane.YES_NO_OPTION);
        if (pilih == JOptionPane.YES_OPTION) {
            mstr_customer x = new mstr_customer();
            x.hapusData(kode);
            refresh();
        }
    }//GEN-LAST:event_bHapusActionPerformed

    private void bUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbahActionPerformed
        try {
            String kode = tbl_mstr_customer.getValueAt(tbl_mstr_customer.getSelectedRow(),0).toString();
            _Entry f = new _Entry("",kode,"Edit");
            Dimension parentSize = this.getSize();
            f.setLocation((parentSize.width)/2, (parentSize.height)/2);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            f.b_keluar.addActionListener(new java.awt.event.ActionListener(){
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    refresh();
                }
            });
            f.b_simpan.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    refresh();
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" +e);
        }
    }//GEN-LAST:event_bUbahActionPerformed

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        try {
            refresh();
        } catch (Exception e){
            JOptionPane.showMessageDialog(this,e);
        }
    }//GEN-LAST:event_txtcariKeyTyped

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        try {
            refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_txtcariKeyReleased

    private void bRefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefActionPerformed
        try {
            refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_bRefActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bKeluar;
    private javax.swing.JButton bRef;
    private javax.swing.JButton bTambah;
    private javax.swing.JButton bUbah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JTable tbl_mstr_customer;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
