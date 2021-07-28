/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neuralnetwork;

/**
 *
 *
 */
public class TDataSet {
    
    float [] training_input;
    float [] training_output;
    
    public TDataSet(float[] x, float [] y){
        this.training_input = x;
        this.training_output = y;
    } 
}
