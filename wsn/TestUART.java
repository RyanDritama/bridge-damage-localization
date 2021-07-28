package com.tugas_akhir.sink.wsn;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TestUART {
    public static void main(String[] args) throws IOException {
        SerialPort port = SerialPort.getCommPort("/dev/ttyS4");
        port.setComPortParameters(
            115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY
        );
        port.openPort();
        //Disable read timeout
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
        
        OutputStream output = port.getOutputStream();
        InputStream input = port.getInputStream();
        output.write("+++".getBytes());
        System.out.println(new String(input.readNBytes(3)));
        output.write("ATCN\r".getBytes());
        System.out.println(new String(input.readNBytes(3)));
    }
}
