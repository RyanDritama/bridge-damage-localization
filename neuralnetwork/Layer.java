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
public class Layer {
    public Neuron[] neurons;
    public Layer(int inNeurons, int jumNeurons) {
        this.neurons = new Neuron[jumNeurons];
        for (int i =0; i<jumNeurons; i++){
            float[] weights = new float[inNeurons];
            for (int j =0; j<inNeurons; j++){
                weights[j]= randomWeight();
            }
            float num = (float) Math.random();
            neurons[i] = new Neuron(weights,num);
        }
    }
    
    public Layer(float input[]) {
		this.neurons = new Neuron[input.length];
		for(int i = 0; i < input.length; i++) {
			this.neurons[i] = new Neuron(input[i]);
		}
	}
    
    public static float randomWeight(){
        float num = (float) Math.random();
        float tanda = (float) Math.random();
        if (tanda<0.5)
            return num;    
        else 
            return -num;
    }
//    public void Neuron1 (float weights[], float bias){
//        this.weights = weights;
//        this.bias = bias;
//        this.cache_weights = this.weights;
//        this.gradient = 0;
//    }
    
}