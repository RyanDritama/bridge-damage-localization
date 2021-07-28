package com.tugas_akhir.sink.wsn;

import com.mycompany.neuralnetwork.NeuralNetwork;
import com.tugas_akhir.sink.storage.DataJembatan;
import com.tugas_akhir.sink.storage.RemoteStorage;
import com.tugas_akhir.sink.storage.Storage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.DecoderException;


public class TestXBee {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            File text = new File("/home/up/neuro=25,layer tetap 2, f = 19, 62 28.txt");
        //Creating Scanner instnace to read File in Java
            Scanner scnr = new Scanner(text);
            NeuralNetwork nn = new NeuralNetwork();
            String URL = "http://103.157.97.25:8000";
            String localDatabase = "src/main/resources/storage//DataJembatan.db";
            RemoteStorage remote = new RemoteStorage(1, URL, "toni", "tugas_akhir");

            System.out.println("Test New xbee");
            Wsn WSN = new Wsn(true);
            
            while(scnr.hasNext()){
                for(int i = 1; i< nn.layers.length;i++) {
                    for(int j = 0; j < nn.layers[i].neurons.length;j++) {
                        for (int k = 0; k < nn.layers[i].neurons[j].weights.length;k++) {
                            nn.layers[i].neurons[j].weights[k] = Float.parseFloat(scnr.next());
                        }
                    }
                }
            }
            
            System.out.println("Test machine learnig");
            System.out.println("Set PAN");
            
            WSN.startAT();
            WSN.set("ATID","B");
            WSN.set("ATDL", "AF01");
            WSN.endAT();
            
            System.out.println("Wake up");
            WSN.wakeUp();
            
            System.out.println("parsing...");
            DataJembatan data = new DataJembatan();
            WSN.parse(data);
            System.out.printf("mean_frequency:%.3f\n", data.meanFrequency);
            data.frequency[6] = data.frequency[4];
            data.frequency[4] = 30f;
            data.frequency[9] = 30f;
            System.out.printf("frequency: ");
            for(float f: data.frequency){
                System.out.println(String.format("%6.3e, ",f));
            } 




            for (int i = 0; i<data.frequency.length; i++){
                data.frequency[i] = data.frequency[i] /19f;
            }
//            System.out.printf("amplitude: ");
//             for(float f: data.amplitude){
//                System.out.println(String.format("%6.3e, ",f));
//            }  
//            System.out.printf("frequency: ");
//            for(float f: data.frequency){
//                System.out.println(String.format("%6.3e, ",f));
//            } 
//            System.out.printf("mode_shape: ");
//
//            for(float f: data.modeShape){
//                System.out.println(String.format("%6.3e, ",f));
//            }
            nn.feedForward(data.frequency);
            for (int i =0;i<nn.layers[3].neurons.length; i++){
                System.out.println(nn.layers[3].neurons[i].value);
            }
//        for (int i = 0; i< data.frequency.length; i++){
//            Float temp = (Float) data.frequency[i];            
//            if (temp.isNaN()){
//                data.frequency[i] = data.meanFrequency;            
//            }
//        }
//        
//        for (int i = 0; i< data.frequency.length; i++){
//            if(data.frequency[i]>27.5){
//                data.frequency[i]=1;
//            }
//            else{
//                data.frequency[i]=(float) (data.frequency[i]/27.5);
//            }
//        }
//        remote.write(data);

//        nn.feedForward(data.frequency);
//        for (int i=1; i<nn.layers[3].neurons.length; i++){
//            System.out.print(nn.layers[3].neurons[i].value);
//            if (nn.layers[3].neurons[i].value> 0.5)
//            {
//               System.out.println (" Lokasi Rusak");
//            }
//            else
//            {
//               System.out.println (" Lokasi tidak Rusak");
//
//            }
//        }
            
        }catch (DecoderException | IOException | InterruptedException  ex) {
            Logger.getLogger(TestXBee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
