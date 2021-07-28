/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neuralnetwork;

/**
 *
 * @author Asus
 */
public class Neuron {
    
    public float[] weights;
    float[] cache_weights;
    float gradient;
    float bias;
    public float value = 0;
    
    
   // Constructor for the hidden / output neurons
    public Neuron(float[] weights, float bias){
        this.weights = weights;
        this.bias = bias;
        this.cache_weights = this.weights;
        this.gradient = 0;
    }
    
    // Constructor for the input neurons
    public Neuron(float value){
        this.weights = null;
        this.bias = -1;
        this.cache_weights = this.weights;
        this.gradient = -1;
        this.value = value;
    }
    
    
    // Function used at the end of the backprop to switch the calculated value in the
    // cache weight in the weights
    public void update_weight() {
    	this.weights = this.cache_weights;
    }
    
    
}
