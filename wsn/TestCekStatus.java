/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tugas_akhir.sink.wsn;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.DecoderException;


public class TestCekStatus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Wsn WSN = new Wsn(true);
            System.out.println("Set PAN");
            
            WSN.startAT();
            WSN.set("ATID","B");
            WSN.set("ATDL", "AF01");
            WSN.endAT();
            StatusSensor test = new StatusSensor();
            WSN.status();
            WSN.cekStatus(test);
            System.out.println(Arrays.toString(test.statusSensor));
            for (int i = 0; i<test.statusSensor.length; i++){
                if (test.statusSensor[i] == 1.0){
                    System.out.println("Sensor aktif");
                    
                }
                else{
                    System.out.println("sensor tidak aktif");
                }
            }
            
            
            // TODO code application logic here
        } catch (DecoderException | IOException | InterruptedException ex) {
            Logger.getLogger(TestCekStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
