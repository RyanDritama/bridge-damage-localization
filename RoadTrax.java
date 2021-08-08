/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tugas_akhir.sink;

import com.mycompany.neuralnetwork.NeuralNetwork;
import com.tugas_akhir.sink.storage.DataJembatan;
import com.tugas_akhir.sink.storage.Storage;
import com.tugas_akhir.sink.wsn.Wsn;
import com.tugas_akhir.sink.wsn.StatusSensor;
import com.tugas_akhir.sink.wim.Wim;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.DecoderException;
import java.util.Scanner;



/**
 *
 *
 */
public class RoadTrax {

    /**
     * @param args the command line arguments
     */
    
    public RoadTrax(int threshold1, int threshold2) throws SQLException, ClassNotFoundException, DecoderException, FileNotFoundException{ 
        data = new DataJembatan();
        String URL = "https://tugasakhir.monitorjembatan.my.id";
        String localDatabase = "src/main/resources/storage//javaDatabaseTutorials.db";
        storage = new Storage(1,localDatabase, URL, "toni", "tugas_akhir");
        idealFreq = 20f;
        WIM = new Wim((byte)0,threshold1,threshold2);
        WSN = new Wsn(false);
    }
    
    public void start() throws IOException, SQLException, InterruptedException{
//        storage.startUploader();
        System.out.println("Database connected.");

        WIM.startSampling();
        System.out.println("Sampling started.");

        WSN.startAT();
        WSN.set("ATID","B");
        WSN.set("ATDL", "AF01");
        WSN.endAT();
        System.out.println("WSN configured.");

        while (true) {    
            if(WIM.isWakeUp()){
                System.out.println("WSN wake!.");
                WIM.sleep();

//                WSN.wakeUp();
//                idealFreq = storage.remote.getIdealFreq();
//                WSN.parse(data);
//                System.out.println("finished parsing.");
//                hitungKesehatan();
//                storage.local.write(data);
            }else{
                Thread.sleep(100);
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner (System.in);
        System.out.println("Masukan nilai threshold pertama : ");
        int a = scan.nextInt();
        System.out.println("Masukan nilai threshold kedua : ");
        int b = scan.nextInt();
        try {
           RoadTrax roadtrax = new RoadTrax(a, b);
           roadtrax.start();
        } catch (SQLException | IOException | InterruptedException | DecoderException | ClassNotFoundException ex) {
            Logger.getLogger(Sink.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void hitungKesehatan(){
        float FreqTerukur = data.meanFrequency; 
        if( FreqTerukur > idealFreq){
            data.indeksKesehatan = 9;
        }else{
            float deltaF = Math.abs((idealFreq-FreqTerukur)/idealFreq);
            float G = deltaF * 1000 / 123;
            data.indeksKesehatan = 9 > G ? (byte)Math.round(9-G) : 0;
        }
    }
    
    final DataJembatan data;
    final Storage storage;
    float idealFreq;
    final Wim WIM;
    final Wsn WSN;
}
