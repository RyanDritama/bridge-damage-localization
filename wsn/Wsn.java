/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tugas_akhir.sink.wsn;

import com.mycompany.neuralnetwork.NeuralNetwork;
import com.tugas_akhir.sink.storage.DataJembatan;
import com.fazecast.jSerialComm.SerialPort;
import com.tugas_akhir.sink.storage.DataJembatan;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Achmad Fathoni 13215061
 */
public class Wsn {    
    public Wsn(boolean debug) throws DecoderException{
        port = SerialPort.getCommPort("/dev/ttyS4");
        port.setComPortParameters(
            115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY
        );
        port.openPort();
        //Disable read timeout
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
        
        output = port.getOutputStream();
        input = port.getInputStream();
        meanFrequency = new float[2];

        raw = new byte[4];
        panId = new byte[2];
          
        dataFlag = Hex.decodeHex("7fc00000");
        wakeFlag = Hex.decodeHex("ba17");
        statusFlag = Hex.decodeHex("bb28");
//        wavelet = new Wavelet();
        this.debug = debug;
    }
      
   public void parse(DataJembatan data) throws IOException{
        boolean cmplt1 = false, cmplt2 = false;
        while(!(cmplt1 && cmplt2)){
            //Wait for 1 or two consecutive flag
            input.readNBytes(raw, 0, 4);
            byte state = 0;
            while(state != 2){
                switch(state){
                    case 0:
                        if(Arrays.equals(dataFlag, raw)){
                            state = 1;
                        }else{
                            System.arraycopy(raw, 1, raw, 0, 3);
                            raw[3] = (byte)input.read();
                        }
                        break;
                    case 1:
                        //Parse sender address
                        panId = input.readNBytes(2);
                        headId = input.readNBytes(2);
                        //Parse data
                        byte offset = 0;
                        switch(headId[1]){
                            case (byte)0x01:
                                cmplt1 = true;
                            break;
                            case (byte)0x02:
                                cmplt2 = true;
                                offset = 1;
                            break;
                        }
                        for (int i = offset*15; i < (offset + 1)*15; i++) {
                            input.readNBytes(raw, 0, 4);
                            if (i >= offset*15+5 && i < offset*15+10){
                                data.frequency[i-(10*(offset)+5)] = ByteBuffer.wrap(raw).getFloat();
                            }
                            else if (i >= offset*15+10) {
                                 data.modeShape[i-(10*(offset)+10)] = ByteBuffer.wrap(raw).getFloat();
                            }
                            else {
                                 data.amplitude[i-10*(offset)] = ByteBuffer.wrap(raw).getFloat();
                            }
                        }
                        input.readNBytes(raw, 0, 4);
                        meanFrequency[offset] = ByteBuffer.wrap(raw).getFloat();
                        state = 2;
                        break;
                }
            }
        }
        data.meanFrequency = (meanFrequency[0] + meanFrequency[1])/2;
        data.date.setTime(System.currentTimeMillis());
    }
        

    
    public void parse(float[] output, float[] dominant) throws IOException{
        input.readNBytes(raw, 0, 4);
        byte state = 0;
        while(state != 2){
            switch(state){
                case 0:
                    if(Arrays.equals(dataFlag, raw)){
                        state = 1;
                    }else{
                        System.arraycopy(raw, 1, raw, 0, 3);
                        raw[3] = (byte)input.read();
                    }
                    break;
                case 1:
                    //Parse sender address
                    panId = input.readNBytes(2);
                    input.readNBytes(2);//headId
                    //Parse data
                    for(int i =0;i<output.length;++i){
                        input.readNBytes(raw, 0, 4);
                        output[i] = ByteBuffer.wrap(raw).getFloat();
                    }
                    for(int i =0;i<dominant.length;++i){
                        input.readNBytes(raw, 0, 4);
                        dominant[i] = ByteBuffer.wrap(raw).getFloat();
                    }
                    //Read end flag
                    input.readNBytes(raw, 0, 4);
//                        wavelet.inverseDWT(output, 8, 1, 4);
                    state = 2;
                    break;
            }
        }

    }
    
    
    public void wakeUp() throws IOException{
        output.write(wakeFlag);
    }
    
    public void wakeUpFlag(String flag) throws IOException, DecoderException{
        output.write(Hex.decodeHex(flag));
    }
    
    public void status() throws IOException{
        output.write(statusFlag);
    }

    //https://cdn.sparkfun.com/learn/materials/29/22AT%20Commands.pdf
    public void set(String at_command, String value){
        try {
            String command = at_command+value+'\r';
            output.write(command.getBytes());
            byte[] response = input.readNBytes(3);
            if(debug) System.out.println(at_command+" "+value+" "+new String(response));
        } catch (IOException ex) {
            Logger.getLogger(Wsn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void startAT() throws IOException, InterruptedException{
        Thread.sleep(1100);
        output.write("+++".getBytes());      
        input.readNBytes(3);
        Thread.sleep(1100);
    }
    
    public void endAT() throws IOException{
        output.write("ATCN\r".getBytes());
        input.readNBytes(3);
    }
    
      public void cekStatus(StatusSensor sensor) throws IOException{
        boolean cmplt1 = false, cmplt2 = false;
        while(!(cmplt1 && cmplt2)){
            //Wait for 1 or two consecutive flag
            input.readNBytes(raw, 0, 4);
            byte state = 0;
            while(state != 2){
                switch(state){
                    case 0:
                        if(Arrays.equals(dataFlag, raw)){
                            state = 1;
                        }else{
                            System.arraycopy(raw, 1, raw, 0, 3);
                            raw[3] = (byte)input.read();
                        }
                        break;
                    case 1:
                        //Parse sender address
                        panId = input.readNBytes(2);
                        headId = input.readNBytes(2);
                        //Parse data
                        byte offset = 0;
                        switch(headId[1]){
                            case (byte)0x01:
                                cmplt1 = true;
                            break;
                            case (byte)0x02:
                                cmplt2 = true;
                                offset = 1;
                            break;
                        }
                        for (int i = offset*5; i < (offset*5)+5; i++) {
                            input.readNBytes(raw, 0, 4);
                            sensor.statusSensor[i] = ByteBuffer.wrap(raw).getFloat();
                        }
                        state = 2;
                        break;
                }
            }
        }
        sensor.date.setTime(System.currentTimeMillis());
    }
    
    private final SerialPort port;
    private final OutputStream output;
    private final InputStream input;
    private final byte[] dataFlag, wakeFlag, raw, statusFlag;
    private byte[] panId, headId;
    float[] meanFrequency;
//    final Wavelet wavelet;
    final private boolean debug;
}