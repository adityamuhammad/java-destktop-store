/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttrs_penjualan;

import mstr_customer.*;
import Class.DBO;
import Class.mstr_customer;
import Class.mstr_user;
import Class.mstr_product;
import Class.ttrs_penjualan;
import Class.ttrs_penjualan_detail;
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
    private DefaultTableModel _tempttrs_mstr_penjualan;
    Connection _Cnn;
    String _User;
    
    /**
     * Creates new form _List
     */
    public _List() {
        initComponents();
        DBO on = new DBO();
        on.makeConnect();
        String[] kolomtbl = {"FAKTUR","ANGGAL","CUSTOMER","TOTAL"};
        _tempttrs_mstr_penjualan = new DefaultTableModel(null, kolomtbl) {
            Class[] types = new Class[] {
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
                
            };
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
            
            public boolean isCellEditable(int row, int col) {
                int kol = _tempttrs_mstr_penjualan.getColumnCount();
                return (col < kol) ? false : true;
            }
        };
        tbl_mstr_ttrs_penjualan.setModel(_tempttrs_mstr_penjualan);
        loadData();
        
        tbl_mstr_ttrs_penjualan.getColumnModel().getColumn(0).setPreferredWidth(140);
        tbl_mstr_ttrs_penjualan.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbl_mstr_ttrs_penjualan.getColumnModel().getColumn(2).setPreferredWidth(210);
        tbl_mstr_ttrs_penjualan.getColumnModel().getColumn(3).setPreferredWidth(100);
        
    }
    
    public void hapusRowData(){
        try {
            int row = _tempttrs_mstr_penjualan.getColumnCount();
            for(int i = 0; i < row; i++);
            {
                _tempttrs_mstr_penjualan.setRowCount(0);
            }
        } catch (Exception e) {
            
        }
    }
    
    private void loadData() {
        try {
            String _sql ="";
            DBO vcon = new DBO();
            vcon.makeConnect();
            hapusRowData();
            _Cnn = vcon.vkoneksi;
            if(txtcari.getText().equals("")){
                _sql=""
                        + " SELECT pj.faktur, pj.tanggal, cust.nama, fk.bayar FROM ttrs_penjualan pj, mstr_customer cust,"
                        + "(SELECT SUM((prd.harga*pd.qty)-(prd.harga*pd.qty)*(pd.diskon/100))AS bayar, pd.faktur "
                        + " FROM ttrs_penjualan_detail pd, mstr_product prd WHERE pd.prd_id = prd.prd_id GROUP BY pd.faktur) fk"
                        + " WHERE pj.faktur=fk.faktur AND pj.cust_id=cust.cust_id";
                System.out.println(_sql);
                
            } else {
                _sql=""
                        + " SELECT pj.faktur, pj.tanggal, cust.nama, fk.bayar FROM ttrs_penjualan pj, mstr_customer cust, ttrs_penjualan_detail ppl,"
                        + "(SELECT SUM((prd.harga*pd.qty)-(prd.harga*pd.qty)*(pd.diskon/100)) AS bayar, pd.faktur "
                        + "FROM ttrs_penjualan_detail pd, mstr_product prd WHERE pd.prd_id = prd.prd_id GROUP BY pd.faktur) fk"
                        + " WHERE pj.faktur LIKE '%"+txtcari.getText()+"%' AND pj.cust_id=cust.cust_id AND ppl.faktur = fk.faktur LIKE '%"+txtcari.getText()+"%'";
            }
            Statement st = _Cnn.createStatement();
            ResultSet rst = st.executeQuery(_sql);
            while(rst.next()){
                String faktur = rst.getString(1);
                String tanggal = rst.getString(2);
                String nama = rst.getString(3);
                String bayar = rst.getString(4);
                Object[] data = {faktur,tanggal,nama,bayar};
                _tempttrs_mstr_penjualan.addRow(data);
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error"+e);
        }
    }
    
    ActionListener actionlist;
    private javax.swing.Timer timer = new javax.swing.Timer(10,actionlist);
    
    public void refresh() {
        hapusRowData();
        try {
            loadData();
            ActionListener acl = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    timer.stop();
                }
            };
            timer = new javax.swing.Timer(10,acl);
            timer.start();
        } catch (Exception e) {
            System.out.println("ttrs_penjualan._List.refresh ERROR" +e);
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
        tbl_mstr_ttrs_penjualan = new javax.swing.JTable();

        jXTitledPanel1.setTitle("_Entry Data Transaksi Penjualan");

        jLabel1.setText("Pencarian");

        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcariKeyPressed(evt);
            }
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

        tbl_mstr_ttrs_penjualan.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_mstr_ttrs_penjualan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_mstr_ttrs_penjualanKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_mstr_ttrs_penjualan);

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(46, 46, 46)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(bKeluar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bRef, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(bUbah, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bTambah, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
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
            _Entry f = new _Entry("U1","","Add");
            Dimension parentSize = this.getSize();
            f.setLocation((parentSize.width)/2,(parentSize.height)/2);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            f.b_keluar.addActionListener(new java.awt.event.ActionListener(){
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    loadData();
                }
            });
            f.b_simpan.addActionListener(new java.awt.event.ActionListener(){
                public void actionPerformed(java.awt.event.ActionEvent evt){
                    refresh();
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_bTambahActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        String kode = tbl_mstr_ttrs_penjualan.getValueAt(tbl_mstr_ttrs_penjualan.getSelectedRow(),0).toString();
        int pilih = JOptionPane.showConfirmDialog(this, "Hapus data user:"+kode,"KONFIRMASI",JOptionPane.YES_NO_OPTION);
        if (pilih == JOptionPane.YES_OPTION){
            ttrs_penjualan x = new ttrs_penjualan();
            x.hapusData(kode);
            refresh();
        }
    }//GEN-LAST:event_bHapusActionPerformed

    private void bUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbahActionPerformed
        try {
            String kode = tbl_mstr_ttrs_penjualan.getValueAt(tbl_mstr_ttrs_penjualan.getSelectedRow(), 0).toString();
            System.out.println(kode);
            _Entry f = new _Entry("",kode,"Edit");
            Dimension parentSize = this.getSize();
            f.setLocation((parentSize.width)/2,(parentSize.height)/2);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            f.b_keluar.addActionListener(new java.awt.event.ActionListener(){
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    loadData();
                }
            });
            f.b_simpan.addActionListener(new java.awt.event.ActionListener(){
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    refresh();
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR"+e);
        }
    }//GEN-LAST:event_bUbahActionPerformed

    private void bRefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefActionPerformed
        try {
            refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_bRefActionPerformed

    private void tbl_mstr_ttrs_penjualanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_mstr_ttrs_penjualanKeyPressed
        try {
            JOptionPane.showMessageDialog(this, "TES");
            bHapus.setText("Hapus ?"+tbl_mstr_ttrs_penjualan.getValueAt(tbl_mstr_ttrs_penjualan.getSelectedRow(),0).toString());
            
        } catch (Exception e) {
            
        }
    }//GEN-LAST:event_tbl_mstr_ttrs_penjualanKeyPressed

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        try {
            loadData();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e);
        }
    }//GEN-LAST:event_txtcariKeyTyped

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        try {
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e);
        }
    }//GEN-LAST:event_txtcariKeyReleased

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyPressed
        try {
            loadData();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e);
        }
    }//GEN-LAST:event_txtcariKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bKeluar;
    private javax.swing.JButton bRef;
    private javax.swing.JButton bTambah;
    private javax.swing.JButton bUbah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JTable tbl_mstr_ttrs_penjualan;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
