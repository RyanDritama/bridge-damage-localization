package com.tugas_akhir.sink.storage;

import java.sql.Timestamp;

public class DataJembatan {
    public DataJembatan(){
        modeShape = new Float[10];
        date = new Timestamp(0);
        frequency = new float[10];
        amplitude = new Float[10];
        lokasiKerusakan = new String[8];
        bridgeSensor = new Integer[10];
        value = new Float[8];
    }
    

    public Timestamp date;
    public byte indeksKesehatan;
    public String[] lokasiKerusakan;
    public float meanFrequency;
    public Float[] modeShape;
    public Float[] amplitude;
    public float[] frequency;
    public Float[] value;
    public Integer[] bridgeSensor;
}
