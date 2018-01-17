/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

/**
 *
 * @author Aditya
 */

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ttrs_penjualan {
    Connection _Cnn;
    
    public String _faktur, _tanggal, _cust_id, _user_id;
    
    public String _Akses = "";
    
    public void hapusData(String kode) {
        System.out.println("pesan kode" +kode);
        this._faktur = kode;
        try {
            _Cnn = DBO.getConnection();
            String _sql = "DELETE FROM store_akhtar.ttrs_penjualan WHERE ttrs_penjualan.faktur = '" + _faktur + "'";
            System.out.println(_sql);
            PreparedStatement prs = _Cnn.prepareStatement(_sql);
            prs.executeUpdate();
            System.out.println(_sql);
            prs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void simpanData(){
        DBO v = new DBO();
        try {
            String _sql = "";
            _Cnn = v.getConnection();
            _sql = "INSERT INTO ttrs_penjualan(faktur, tanggal, cust_id, user_id)"
                    + " VALUES "
                    + "('" + String.valueOf(_faktur) + "',"
                    + "'" + String.valueOf(_tanggal) + "',"
                    + "'" + String.valueOf(_cust_id) + "',"
                    + "'" + String.valueOf(_user_id) + "')";
            System.out.println(_sql);
            int status = v.executestatement(_sql);
            if (status == 1) {
                System.out.println(_sql);
            }
        } catch (Exception err) {
            System.out.println("_simpan data error _" +err);
        }
    }
    
    public void Cari(String faktur) {
        String pilih_faktur = faktur;
        try {
            _Akses = "";
            _Cnn = DBO.getConnection();
            String SQL = "SELECT * FROM ttrs_penjualan WHERE faktur = '" + pilih_faktur + "'";
            Statement st= _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                _Akses = "-";
                this._faktur = rs.getString(1);
                this._tanggal = rs.getString(2);
                this._cust_id = rs.getString(3);
                this._user_id = rs.getString(4);
                System.out.println(SQL);
                _Cnn = DBO.getConnection();
            }
            st.close();
            System.out.println("_" +st.toString());
        } catch (Exception err) {
            System.out.println("_ERROR" + err.toString());
        }
    }
    
    public void updateData() {
        boolean result = false;
        String _sql = "";
        try {
            _Cnn = DBO.getConnection();
            Statement st = _Cnn.createStatement();
            _sql = "UPDATE store_akhtar.ttrs_penjualan SET"
                    + " tanggal = '" +String.valueOf(this._tanggal)+ "',"
                    + " cust_id = '" +String.valueOf(this._cust_id)+ "',"
                    + " user_id = '" +String.valueOf(this._user_id)+ "'"
                    + " WHERE ttrs_penjualan.faktur = '" +String.valueOf(this._faktur)+ "'";
            int status = DBO.executestatement(_sql);
            PreparedStatement prs = _Cnn.prepareStatement(_sql);
            if (status == 1) {
                prs.setString(1, this._faktur);
                prs.setString(2, this._tanggal);
                prs.setString(3, this._cust_id);
                prs.setString(4, this._user_id);
                prs.executeUpdate();
                System.out.println(_sql);
                _Cnn = DBO.getConnection();
                prs.close();
            }
        } catch (Exception err) {
            System.out.println("ERROR" + err + " " +_Cnn + " " + _sql);
        }
    }
    
    public void simpanOrUpdate(){
        try {
            String _sql;
            _Cnn = DBO.getConnection();
            if (_Akses.equals("")) {
                _sql = "INSERT INTO ttrs_penjualan(faktur, tanggal, cust_id, user_id)"
                        + " VALUES"
                        + "('" + String.valueOf(_faktur) + "',"
                        + "'" + String.valueOf(_tanggal) + "',"
                        + "'" + String.valueOf(_cust_id) + "',"
                        + "'" + String.valueOf(_user_id) + "')";
                System.out.println(_sql);
            } else {
                _sql = "UPDATE store_akhtar.ttrs_penjualan SET "
                        + "tanggal = '" +String.valueOf(this._tanggal) + "',"
                        + "cust_id = '" +String.valueOf(this._cust_id) + "',"
                        + "user_id = '" +String.valueOf(this._user_id) + "'"
                        + " WHERE ttrs_penjualan.faktur ='" +String.valueOf(this._faktur) + "'";
                System.out.println(_sql);
            }
            int status = DBO.executestatement(_sql);
            if (status == 1) {
                PreparedStatement prs = _Cnn.prepareStatement(_sql);
                prs.setString(1, this._faktur);
                prs.setString(2, this._tanggal);
                prs.setString(3, this._cust_id);
                prs.setString(4, this._user_id);
                prs.executeUpdate();
                System.out.println(_sql);
                prs.close();
            }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "simpan or update ERROR : " +err);
        }
    }
}
