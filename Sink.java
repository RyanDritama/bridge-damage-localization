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


public class Sink {
    public static void main(String[] args) {
        try {
           Sink sink = new Sink();
           sink.start();
        } catch (SQLException | IOException | InterruptedException | DecoderException | ClassNotFoundException ex) {
            Logger.getLogger(Sink.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Sink() throws SQLException, ClassNotFoundException, DecoderException, FileNotFoundException{ 
        data = new DataJembatan();
        String URL = "https://monitorjembatan.my.id";
        String localDatabase = "src/main/resources/storage//javaDatabaseTutorials.db";
        storage = new Storage(1,localDatabase, URL, "toni", "tugas_akhir");
        idealFreq = storage.remote.getIdealFreq();
        WIM = new Wim((byte)0);
        WSN = new Wsn(false);
        nn = new NeuralNetwork();
    }
    
    public void start() throws IOException, SQLException, InterruptedException{
        storage.startUploader();
        System.out.println("Test Program Sink");
        File text = new File("/home/up/data.txt");
        Scanner scnr = new Scanner(text);
        while(scnr.hasNext()){
            for(int i = 1; i< nn.layers.length;i++) {
                for(int j = 0; j < nn.layers[i].neurons.length;j++) {
                    for (int k = 0; k < nn.layers[i].neurons[j].weights.length;k++) {
                        nn.layers[i].neurons[j].weights[k] = Float.parseFloat(scnr.next());
                    }
                }
            }
        }
        WIM.startSampling();
        System.out.println("Sampling started.");

        WSN.startAT();
        WSN.set("ATID","B");
        WSN.set("ATDL", "AF01");
        WSN.endAT();
        System.out.println("WSN configured.");
        Float x;
        long statusTime = System.currentTimeMillis(); 
        while (true) {    
            if(WIM.isWakeUp()){                  
                System.out.println("WSN wake!.");
                WIM.sleep();
                WSN.wakeUp();
                idealFreq = storage.remote.getIdealFreq();
                WSN.parse(data);
                System.out.println("finished parsing.");
                System.out.println("Processing Data.");
                aktivasi();
                hitungKesehatan();
                nn.feedForward(data.frequency);
                for (int i =1; i<nn.layers[3].neurons.length; i++){
                    System.out.println(nn.layers[3].neurons[i].value);
                }
                pickNeurons();
                for (int i =0; i<8; i++){
                    if(i == 2){
                        System.out.println("Lokasi Rusak");
                    }
                    else{
                        System.out.println("Lokasi tidak Rusak");
                    }
                    
                }
                System.out.println ("Saving data");
                System.out.print ("indeksKesehatan = ");
        
                System.out.print ("Prediksi Kerusakan = ");
                System.out.println(Arrays.toString(data.lokasiKerusakan));
                System.out.println(Arrays.toString(data.value));
                System.out.println(Arrays.toString(data.frequency));
                storage.local.write(data);
            }
            if(System.currentTimeMillis()-statusTime >= 86400000){
                System.out.println("cek status");
                sendStatus();
                statusTime = System.currentTimeMillis();
            }
            else{
                Thread.sleep(100);
            }
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
    
    private void sendStatus() throws IOException{
        StatusSensor sensor = new StatusSensor();
        WSN.status();
        WSN.cekStatus(sensor);
        System.out.println(Arrays.toString(sensor.statusSensor));
        for (int i=0; i<sensor.statusSensor.length; i++) {
            if (sensor.statusSensor[i] == 1.0) {
                System.out.println("Sensor aktif");
            } else {
                System.out.println("sensor tidak aktif");
            }
        }
        storage.remote.sendStatus(sensor);
    }
    
        private void aktivasi (){
            for (int i = 0; i< data.frequency.length; i++){
                Float temp = (Float) data.frequency[i];            
                if (temp.isNaN()){
                    data.frequency[i] = 1;            
                }
            }
        
            for (int i = 0; i< data.frequency.length; i++){
                if(data.frequency[i]>19){
                    data.frequency[i]=1;
                }
                else    {
                    data.frequency[i]=(float) (data.frequency[i]/19);
                }
            }
    }
        private void pickNeurons(){
            float max = (float) 0.0;
            int index = 0;                
            for (int j=0; j<nn.layers[3].neurons.length; j++){
                if (nn.layers[3].neurons[j].value>=max){
                    max = nn.layers[3].neurons[j].value;
                    index = j;
                }
            }
            for (int i=0; i<data.lokasiKerusakan.length; i++){
                data.value[i] = (Float) nn.layers[3].neurons[i+1].value;
                if (i == (index-1)){
                    if (i != 0){
                        data.lokasiKerusakan[i] = "1";
                    }
                }
                else {
                    data.lokasiKerusakan[i] = "0";
                }
            }
        }
    
    final DataJembatan data;
    final Storage storage;
    float idealFreq;
    final Wim WIM;
    final Wsn WSN;
    final NeuralNetwork nn;
//                                                                                                                                                                                                  final Wsn WSN;
}
