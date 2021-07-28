package com.tugas_akhir.sink.storage;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Achmad Fathoni 13215061
 */
public class Storage {
    public Storage(int id, String localURL, String remoteURL, String user, String password) throws SQLException, ClassNotFoundException{
        local = new LocalStorage(localURL);
        remote = new RemoteStorage(id, remoteURL, user, password);
        uploadThread = new Thread(){
            @Override
            public void run(){
                while(true){
                    try {
                        if(local.read(data)){
                            System.out.println("Sending data...");
                            data.bridgeSensor[0] =1;
                            data.bridgeSensor[1] =1;
                            data.bridgeSensor[2] =1;
                            data.bridgeSensor[3] =1;
                            data.bridgeSensor[4] =1;
                            data.bridgeSensor[5] =1;
                            data.bridgeSensor[6] =1;
                            data.bridgeSensor[7] =1;
                            data.bridgeSensor[8] =1;
                            data.bridgeSensor[9] =1;               
                            remote.write(data);
                            System.out.println("Data is sent");
                        }else{
                            //Database is empty wait 1s
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException |SQLException ex) {
                        Logger.getLogger(LocalStorage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        data = new DataJembatan();
    }
    
    public void startUploader(){
        uploadThread.start();
    }
    
    public static String getModeShapeStr(boolean isHolded){
        String str = "";
        String pad = isHolded? "=?," : ",";
        for (int i = 1; i <= 10; i++) {
            str += "mode_shape" + Integer.toString(i) + pad;
        }
        return str;
    }
    
    public static String getHolder(int n){
        String holder="";
        for (int i = 0; i < n-1; i++) holder += "?,";
        holder += '?';
        return holder;
    }
    
        public static String getLocation(boolean isHolded){
        String str = "";
        String pad = isHolded? "=?," : ",";
        for (int i = 1; i <= 8; i++) {
            str += "lokasi" + Integer.toString(i) + pad;
        }
        return str;
    }
    
    public static String getValue(boolean isHolded){
        String str = "";
        String pad = isHolded? "=?," : ",";
        for (int i = 1; i <= 8; i++) {
            str += "value" + Integer.toString(i) + pad;
        }
        return str;
    }
    
    public static String getFrequency(boolean isHolded){
        String str = "";
        String pad = isHolded? "=?," : ",";
        for (int i = 1; i <= 10; i++) {
            str += "frequency" + Integer.toString(i) + pad;
        }
        return str;
    }
    
    final public LocalStorage local;
    final public RemoteStorage remote;
    final Thread uploadThread;
    final DataJembatan data;
}
