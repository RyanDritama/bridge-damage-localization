package com.tugas_akhir.sink.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class LocalStorage {    
    public LocalStorage(String url) throws SQLException  {
        maxPageCount = 5000000000L / 4096;//5GB/4096B
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sqlite = DriverManager.getConnection("jdbc:sqlite:"+url);
    }
    
    public  boolean write(DataJembatan data) throws SQLException  {
        String sql;
        long pageCount = getPageCount();
        if(pageCount == -1){
            return false;      
        }else if(pageCount >= maxPageCount){
            sql =   "UPDATE DataJembatan SET"+Storage.getModeShapeStr(true)+"time=?,"+ Storage.getLocation(true)+Storage.getValue(true)+Storage.getFrequency(true) 
                    + "mean_frequency=?, indeks_kesehatan=? \n" +
                    "WHERE time IN (SELECT time FROM DataJembatan ORDER BY time ASC LIMIT 1);";
        }else{
            sql =   "INSERT INTO DataJembatan("+Storage.getModeShapeStr(false)+"time,"+Storage.getLocation(false)+Storage.getValue(false)+Storage.getFrequency(false)
                    + "mean_frequency, indeks_kesehatan)"+
                    " VALUES("+Storage.getHolder(39)+")";
        }
             
        PreparedStatement st = sqlite.prepareStatement(sql);
        for (int i = 1; i <= 10; i++) {
            st.setFloat(i, data.modeShape[i-1]);
        }
        st.setLong(11, data.date.getTime());
        for (int i = 12; i <= 19; i++) {
            st.setString(i, data.lokasiKerusakan[i-12]);
        }
        for (int i = 20; i <= 27; i++) {
            st.setFloat(i, data.value[i-20]);
        }
        for (int i = 28; i <= 37; i++) {
            st.setFloat(i, data.frequency[i-28]);
        }
        st.setFloat(38, data.meanFrequency);
        st.setByte(39, data.indeksKesehatan);
        st.execute();
       
        return true;
    }
    
    public  boolean read(DataJembatan data) throws SQLException  {
        String sql =    "SELECT "+Storage.getModeShapeStr(false) + "time," + Storage.getLocation(false)+ Storage.getValue(false) + Storage.getFrequency(false)
                        + " mean_frequency,indeks_kesehatan" + 
                        " FROM DataJembatan ORDER BY time DESC LIMIT 1";
      
        PreparedStatement st = sqlite.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        
        if(rs.next()){
            for (int i = 1; i <= 10; i++) {
                data.modeShape[i-1] = rs.getFloat(i);
            }
            data.date.setTime(rs.getLong(11));
            for (int i = 12; i <= 19; i++) {
                data.lokasiKerusakan[i-12] = rs.getString(i);
            }
            for (int i = 20; i <= 27; i++) {
                data.value[i-20] = rs.getFloat(i);
            }
            for (int i = 28; i <= 37; i++) {
                data.frequency[i-28] = rs.getFloat(i)*19;
            }
            data.meanFrequency = rs.getFloat(38);
            data.indeksKesehatan = rs.getByte(39);

            //Delete entry after read
            sql =  "DELETE FROM DataJembatan WHERE time = ?";
            st = sqlite.prepareStatement(sql);
            st.setLong(1, rs.getLong(11));
            st.execute();
            return true;
        }else{
            return false;
        }     
    }
    
    private long getPageCount() throws SQLException{
        String sql = "pragma page_count";
        PreparedStatement st = sqlite.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        rs.next();       
        return rs.getLong(1);
    }
        
    Connection sqlite;
    final SimpleDateFormat sdf;
    final long maxPageCount;
}
