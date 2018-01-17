/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttrs_penjualan;

import mstr_customer.*;
import Class.DBO;
import penjualan.frmMenuUtama;
import Class.mstr_customer;
import Class.ttrs_penjualan;
import Class.ttrs_penjualan_detail;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import report.FrmLaporan;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Aditya
 */
public class _Entry extends javax.swing.JFrame {
    Connection _Cnn;
    private boolean append;
    DBO k = new DBO();
    private Dimension dmn = Toolkit.getDefaultToolkit().getScreenSize();
    private DefaultTableModel temp_tbl_mstr_penjualan_detail;
    SimpleDateFormat t2 = new SimpleDateFormat("yyyy-MM-dd");
    public String _faktur ;
    String _PID;
    String _Status;
    String _User;
    /**
     * Creates new form _Entry
     */
    public _Entry(String user, String pid, String status) {
        initComponents();
        _PID = pid;
        if(status.equals("Edit")) {
            System.out.println("kode dipilih"+pid);
            txt_faktur.setText(_PID);
            b_simpan.setText("Update");
        }
        _Status = status;
        _User = user;
        listUser();
        listProduct();
        listCustomer();
        
        String[] kolomtbl = {"FAKTUR","PRODUK", "HARFA","QTY","DISKON", "SUBTOTAL"};
        temp_tbl_mstr_penjualan_detail = new DefaultTableModel(null, kolomtbl) {
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.Double.class,
                java.lang.Double.class,
                java.lang.Double.class,
                java.lang.Double.class
            };
            
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        
        tb_ttrs_mstr_penjualan_detail.setModel(temp_tbl_mstr_penjualan_detail);
        loadData();
        
        tb_ttrs_mstr_penjualan_detail.getColumnModel().getColumn(0).setPreferredWidth(90);
        tb_ttrs_mstr_penjualan_detail.getColumnModel().getColumn(1).setPreferredWidth(100);
        tb_ttrs_mstr_penjualan_detail.getColumnModel().getColumn(2).setPreferredWidth(100);
        tb_ttrs_mstr_penjualan_detail.getColumnModel().getColumn(3).setPreferredWidth(100);
        tb_ttrs_mstr_penjualan_detail.getColumnModel().getColumn(4).setPreferredWidth(110);
        tb_ttrs_mstr_penjualan_detail.getColumnModel().getColumn(5).setPreferredWidth(110);
        
    }
    
    public _Entry(){
        
    }
    
    ActionListener actionlist;
    private javax.swing.Timer timer = new javax.swing.Timer(10, actionlist);
    
    public void hapusRowData(){
        int row = temp_tbl_mstr_penjualan_detail.getColumnCount();
        while (temp_tbl_mstr_penjualan_detail.getRowCount()> 0) {
            temp_tbl_mstr_penjualan_detail.setRowCount(0);
        }
    }
    
    public void refresh() {
        hapusRowData();
        try {
            ActionListener acl = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    timer.stop();
                }
            };
            timer = new javax.swing.Timer(10,acl);
            timer.start();
        } catch (Exception e) {
            System.out.println("ttrs_penjualan._Entry.Refresh ERRROR" +e);
        }
    }
    
    public void loadData() {
        refresh();
        try {
            _Cnn = DBO.getConnection();
            hapusRowData();
            String _sql = "";
            _sql = "SELECT pd.faktur, prd.prd_id,prd.harga, pd.qty,pd.diskon,"
                    + "((prd.harga*pd.qty)-((prd.harga*pd.qty)*(pd.diskon/100))) AS bayar "
                    + "FROM ttrs_penjualan_detail pd, mstr_product prd "
                    + "WHERE pd.prd_id = prd.prd_id AND pd.faktur ='" +txt_faktur.getText()+"'";
            System.out.println(_sql);
            Statement st = _Cnn.createStatement();
            ResultSet rst = st.executeQuery(_sql);
            while (rst.next()) {
                String faktur = rst.getString(1);
                String prd_id = rst.getString(2);
                Double harga = rst.getDouble(3);
                Double qty = rst.getDouble(4);
                Double diskon = rst.getDouble(5);
                Double subtotal = rst.getDouble(6);
                Object[] data = {faktur,prd_id,harga,qty,diskon,subtotal};
                temp_tbl_mstr_penjualan_detail.addRow(data);
                
                
                
            }
            _Cnn = null;
            _Cnn = DBO.getConnection();
            _sql = "SELECT SUM((prd.harga*pd.qty)-((prd.harga*pd.qty)*(pd.diskon/100)))"
                    + "AS bayar FROM ttrs_penjualan_detail pd, mstr_product prd "
                    + "WHERE pd.prd_id = prd.prd_id AND pd.faktur = '"+txt_faktur.getText()+"'";
            System.out.println(_sql);
            st = _Cnn.createStatement();
            rst = st.executeQuery(_sql);
            while (rst.next()){
                txt_total.setText(Double.toString(rst.getDouble(1)));
                System.out.println("Bayar ="+rst.getDouble(1));
            }
            String SQL = "SELECT nama FROM mstr_user WHERE user_id = '" +KeyUser[txt_user.getSelectedIndex()]+ "'";
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()){
                L_SetUser.setText(rs.getString(1).toString());
            }

            
        } catch (Exception e) {
            System.out.println("Error" +e);
        }
    }
    
    String[] KeyProduct;
    
    private void listProduct(){
        try {
            _Cnn = DBO.getConnection();
            String SQL = "SELECT prd_id, nama, harga FROM mstr_product";
            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            txt_prd.removeAllItems();
            int i = 0;
            while (rs.next()) {
                txt_prd.addItem(rs.getString(2));
                i++;
            }
            rs.first();
            KeyProduct = new String[i + 1];
            for (Integer x = 0; x < i; x++){
                KeyProduct[x] = rs.getString(1);
                rs.next();
            }
            rs.first();
            txt_harga.setText(Double.toString(rs.getDouble(3)));
        } catch (Exception e) {
            
        }
        
    }
    
    String[] KeyCustomer;
    
    private void listCustomer(){
        try {
            _Cnn = DBO.getConnection();
            String SQL = "SELECT cust_id,nama FROM mstr_customer";
            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            txt_cust.removeAllItems();
            int i = 0;
            while(rs.next()){
                txt_cust.addItem(rs.getString(2));
                i++;
            }
            rs.first();
            KeyCustomer = new String[i+1];
            for (Integer x =0; x< i; x++){
                KeyCustomer[x] = rs.getString(1);
                rs.next();
            }
        } catch (Exception e) {
            
        }
    }
    
    String[] KeyUser;
    
    private void listUser(){
        try {
            _Cnn = DBO.getConnection();
            String SQL = "SELECT user_id, nama FROM mstr_user";
            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            txt_user.removeAllItems();
            int i = 0;
            while (rs.next()){
                txt_user.addItem(rs.getString("user_id"));
                i++;
            }
            
            rs.first();
            KeyUser = new String[i+1];
            for(Integer x = 0; x< i; x++){
                KeyUser[x] = rs.getString(1);
                rs.next();
            }
        } catch (Exception e) {
            
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_faktur = new javax.swing.JTextField();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        L_SetUser = new javax.swing.JLabel();
        txt_user = new javax.swing.JComboBox<>();
        txt_cust = new javax.swing.JComboBox<>();
        txt_prd = new javax.swing.JComboBox<>();
        txt_harga = new javax.swing.JTextField();
        txt_qty = new javax.swing.JTextField();
        txt_diskon = new javax.swing.JTextField();
        b_simpan = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        bCetak = new javax.swing.JButton();
        b_keluar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_ttrs_mstr_penjualan_detail = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("FAKTUR");

        jLabel2.setText("Customer");

        jLabel3.setText("Product");

        jLabel4.setText("Harga");

        jLabel5.setText("QTy");

        jLabel6.setText("Diskon");

        L_SetUser.setText("USER : XXXXXXX");

        txt_user.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txt_user.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txt_userItemStateChanged(evt);
            }
        });

        txt_cust.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txt_prd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txt_prd.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txt_prdItemStateChanged(evt);
            }
        });

        txt_qty.setText("0.0");

        txt_diskon.setText("0.0");

        b_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/if_floppy_285657.png"))); // NOI18N
        b_simpan.setText("Simpan");
        b_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_simpanActionPerformed(evt);
            }
        });

        bHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/if_basket_1814090.png"))); // NOI18N
        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        bCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/if_document-print_38984.png"))); // NOI18N
        bCetak.setText("Cetak");
        bCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetakActionPerformed(evt);
            }
        });

        b_keluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/if_40_Close_Switch_Off_Power_Switcher_Button_2142692.png"))); // NOI18N
        b_keluar.setText("Keluar");
        b_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_keluarActionPerformed(evt);
            }
        });

        jLabel7.setText("Total");

        tb_ttrs_mstr_penjualan_detail.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tb_ttrs_mstr_penjualan_detail);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(b_simpan)
                                .addGap(18, 18, 18)
                                .addComponent(bHapus)
                                .addGap(18, 18, 18)
                                .addComponent(bCetak)
                                .addGap(18, 18, 18)
                                .addComponent(b_keluar))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txt_faktur, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(76, 76, 76)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(L_SetUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txt_user, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(txt_cust, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_prd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(79, 79, 79))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_faktur, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(L_SetUser))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_cust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_prd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_simpan)
                    .addComponent(bHapus)
                    .addComponent(bCetak)
                    .addComponent(b_keluar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_prdItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txt_prdItemStateChanged
        try {
            _Cnn = null;
            _Cnn = DBO.getConnection();
            String SQL = "SELECT harga FROM mstr_product WHERE prd_id = '" +KeyProduct[txt_prd.getSelectedIndex()]+ "'";
            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()){
                txt_harga.setText(rs.getString(1).toString());
            }
        } catch (Exception e) {
            
        }
    }//GEN-LAST:event_txt_prdItemStateChanged

    private void b_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_keluarActionPerformed
        append = false;
        this.setVisible(append);
    }//GEN-LAST:event_b_keluarActionPerformed

    private void b_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_simpanActionPerformed
        try {
            ttrs_penjualan tp = new ttrs_penjualan();
            tp.Cari(txt_faktur.getText());
            tp._faktur = txt_faktur.getText();
            tp._tanggal = t2.format(jXDatePicker1.getDate());
            tp._cust_id = KeyCustomer[txt_cust.getSelectedIndex()];
            tp._user_id = _User;
            if (tp._Akses.equals("-")){
                tp.updateData();
                txt_faktur.setEditable(false);
            } else {
                tp.simpanData();
                txt_faktur.setEditable(false);
            }
            ttrs_penjualan_detail tpd = new ttrs_penjualan_detail();
            String produk = KeyProduct[txt_prd.getSelectedIndex()];
            tpd.Cari(txt_faktur.getText(), produk);
            tpd.faktur = txt_faktur.getText();
            tpd.prd_id = produk;
            tpd.qty = Double.parseDouble((txt_qty.getText().toString()));
            tpd.diskon = Double.parseDouble(txt_diskon.getText().toString());
            tpd.simpanOrUpdate();
            loadData();
        } catch (Exception e) {
            System.err.println("ERROR simpan di tpd");
        }
    }//GEN-LAST:event_b_simpanActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        try {
            String kode = tb_ttrs_mstr_penjualan_detail.getValueAt(tb_ttrs_mstr_penjualan_detail.getSelectedRow(), 1).toString();
            int pilih = JOptionPane.showConfirmDialog(this,"Hapus data user : "+kode, "KONFIRMASI",JOptionPane.YES_NO_OPTION);
            if (pilih == JOptionPane.YES_OPTION) {
                ttrs_penjualan_detail x = new ttrs_penjualan_detail();
                x.hapusData(_PID, kode);
                JOptionPane.showMessageDialog(this,"Data dengan produk ID : "+kode+ " sudah berhasil dihapus");
                refresh();
                loadData();
            }
        } catch (Exception e) {
            
        }
    }//GEN-LAST:event_bHapusActionPerformed

    private void txt_userItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txt_userItemStateChanged
        try {
            _Cnn = null;
            _Cnn = DBO.getConnection();
            String SQL = "SELECT nama FROM mstr_user WHERE user_id = '" +KeyUser[txt_user.getSelectedIndex()]+ "'";
            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()){
                L_SetUser.setText(rs.getString(1).toString());
            }
        } catch (Exception e) {
            
        }
    }//GEN-LAST:event_txt_userItemStateChanged

    private void bCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetakActionPerformed
        _faktur = txt_faktur.getText();
        FrmLaporan l = new FrmLaporan();
        frmMenuUtama utama = new frmMenuUtama();
        l.FrmLaporan("new_laporan_detail_groupby_faktur.jasper",_faktur);
  
        utama.jDesktopPane1.add(l);
        Dimension parentSize = utama.getSize();
        Dimension childSize = l.getSize();
        l.setLocation((parentSize.width - childSize.width) /2 ,(parentSize.height - childSize.height) /2);
        l.setVisible(true);
        utama.setVisible(true);
        l.bTampil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                l.bTampil.setText("Cetak Struk");
            }
        });

//        try {
//
//            String path="src/report/new_laporan_detail_groupby_faktur.jasper";
//
//            HashMap parameter = new HashMap();
//
//            parameter.put("faktur",txt_faktur.getText());
//
//            JasperPrint print = JasperFillManager.fillReport(path,parameter,DBO.getConnection());
//
//            JasperViewer.viewReport(print, false);
//
//        } catch (Exception ex) {
//
//            JOptionPane.showMessageDialog(rootPane,"Dokumen Tidak Ada "+ex);
//
//        }
    }//GEN-LAST:event_bCetakActionPerformed

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
            java.util.logging.Logger.getLogger(_Entry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(_Entry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(_Entry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(_Entry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new _Entry().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel L_SetUser;
    private javax.swing.JButton bCetak;
    private javax.swing.JButton bHapus;
    public javax.swing.JButton b_keluar;
    public javax.swing.JButton b_simpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    public javax.swing.JTable tb_ttrs_mstr_penjualan_detail;
    private javax.swing.JComboBox<String> txt_cust;
    private javax.swing.JTextField txt_diskon;
    public javax.swing.JTextField txt_faktur;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JComboBox<String> txt_prd;
    private javax.swing.JTextField txt_qty;
    private javax.swing.JTextField txt_total;
    private javax.swing.JComboBox<String> txt_user;
    // End of variables declaration//GEN-END:variables
}
