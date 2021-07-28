/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tugas_akhir.sink.wsn;
import java.sql.Timestamp;
/**
 *
 *
 */
public class StatusSensor {    
    public StatusSensor() {
        statusSensor = new Float[10];
        date = new Timestamp(0);
}
    public Float[] statusSensor;
    public Timestamp date;
}

