/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neuralnetwork;
import com.tugas_akhir.sink.storage.DataJembatan;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Ryan Dritama
 */
public class NeuralNetwork {
    
    static float[] test_Input1 = new float[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; 
    static float[] test_Input2 = new float[] {(float) 0.9, 1, 1, 1, 1, 1, 1, 1, 1, 1}; 
    static float[] test_Input3 = new float[] {1, (float) 0.1, 1, 1, 1, 1, (float) 0.9, 1, 1, 1}; 
    static float[] test_Input4 = new float[] {1, 0, 1, 1, 1, 1, 1, 1, 1, 1,}; 
        
    static float[] test_Output0 = new float[] {1, 0, 0, 0, 0, 0, 0, 0, 0};  
    static float[] test_Output1 = new float[] {0, 1, 0, 0, 0, 0, 0, 0, 0};
    static float[] test_Output2 = new float[] {0, 0, 1, 0, 0, 0, 0, 0, 0};
    static float[] test_Output3 = new float[] {0, 0, 0, 1, 0, 0, 0, 0, 0};
    static float[] test_Output4 = new float[] {0, 0, 0, 0, 1, 0, 0, 0, 0};
    static float[] test_Output5 = new float[] {1, 0, 0, 0, 0, 1, 0, 0, 0};  
    static float[] test_Output6 = new float[] {0, 1, 0, 0, 0, 0, 1, 0, 0};
    static float[] test_Output7 = new float[] {0, 0, 1, 0, 0, 0, 0, 1, 0};
    static float[] test_Output8 = new float[] {0, 0, 0, 1, 0, 0, 0, 0, 1};
    static float[] test = new float[12];
    public static Layer[] layers;
    static TDataSet[] trainData; 
    static DataJembatan data;
    public static void main(String[] args) throws FileNotFoundException {
        data = new DataJembatan();
        for (int i = 0; i<test_Input3.length; i++){
            data.frequency[i] = test_Input3[i];
        }
        
        for (int i = 0; i<data.frequency.length; i++){
            test[i] = data.frequency[i];
        }
        
        
        NeuralNetwork nn = new NeuralNetwork();
        NeuralNetwork.train(10000, 0.1f);
        NeuralNetwork.feedForward(test);
        for (Neuron neuron : nn.layers[3].neurons) {
            System.out.print(neuron.value);
            if (neuron.value > 0.5) {
                System.out.println (" Lokasi Rusak");
            } else {
                System.out.println (" Lokasi tidak Rusak");
            }
        }
        
    }
    
    public NeuralNetwork() throws FileNotFoundException{

        layers = new Layer [4];
        layers[0] = null;
        layers[1] = new Layer(10,25);
        layers[2] = new Layer(25,25);
        layers[3] = new Layer(25,9);
        float[][] input = new float[4][10];
        float[][] output = new float[4][8];
        input[1] = test_Input1;
        float[][] x = new float[180][10];
        float[][] y = new float[180][9];
        File text = new File("/home/up/Dataset.txt");
        //Creating Scanner instnace to read File in Java
        Scanner scnr = new Scanner(text);
        float[][] xtest = new float[180][10];


        while(scnr.hasNext()){
            for(int i=0; i<180; i++){
                for (int a=0; a<10; a++ ){
                    scnr.useDelimiter(Pattern.compile(","));
                    xtest[i][a] = Float.parseFloat(scnr.next());
                                            if (xtest[i][a] >= 18.0){
                            xtest[i][a] = 1;
                        }
                        else{
                            xtest[i][a] = xtest[i][a]/18;
                        }
                }
                scnr.next();
            
            }
    }
    }
    
    public static float sigmoid (float x){
        return (float) (1/(1+Math.pow(Math.E, -x)));
    }
    
    public static void feedForward (float[] x){
        layers [0] = new Layer(x);
        for (int i = 1; i<layers.length; i++){
            for (int j = 0; j < layers[i].neurons.length; j++){
                float sum = 0;
                for (int k=0; k<layers[i-1].neurons.length; k++){
                    sum += layers[i-1].neurons[k].value*layers[i].neurons[j].weights[k];//+layers[i].neurons[j].bias;
                }
                layers[i].neurons[j].value = sigmoid(sum);
                
            }
        }
//        for (int i=0; i<layers[3].neurons.length; i++){
//            System.out.println(layers[3].neurons[i].value);
//        }
    }
       
    public static void selfBackProp(float lr,TDataSet tData) {
    	
    	int number_layers = layers.length;
    	int out_index = number_layers-1;
    	
    	for(int i = 0; i < layers[out_index].neurons.length; i++) {
    		// and for each of their weights
    		float output = layers[out_index].neurons[i].value;
    		float target = tData.training_output[i];
    		float derivative = output-target;
    		float delta = derivative*(output*(1-output));
    		layers[out_index].neurons[i].gradient = delta;
    		for(int j = 0; j < layers[out_index].neurons[i].weights.length;j++) { 
    			float previous_output = layers[out_index-1].neurons[j].value;
    			float error = delta*previous_output;
    			layers[out_index].neurons[i].cache_weights[j] = layers[out_index].neurons[i].weights[j] - lr*error;
    		}
    	}
    	
    	for(int i = out_index-1; i > 0; i--) {
    		
    		for(int j = 0; j < layers[i].neurons.length; j++) {
    			float output = layers[i].neurons[j].value;
    			float gradient_sum = sumGradient(j,i+1);
    			float delta = (gradient_sum)*(output*(1-output));
    			layers[i].neurons[j].gradient = delta;
    			// And for all their weights
    			for(int k = 0; k < layers[i].neurons[j].weights.length; k++) {
    				float previous_output = layers[i-1].neurons[k].value;
    				float error = delta*previous_output;
    				layers[i].neurons[j].cache_weights[k] = layers[i].neurons[j].weights[k] - lr*error;
    			}
    		}
    	}
    	
        // Here we do another pass where we update all the weights
        for (Layer layer : layers) {
            for (Neuron neuron : layer.neurons) {
                neuron.update_weight();
            }
        }
    	
    }
    
    // This function sums up all the gradient connecting a given neuron in a given layer
    public static float sumGradient(int n_index,int l_index) {
    	float gradient_sum = 0;
    	Layer current_layer = layers[l_index];
        for (Neuron current_neuron : current_layer.neurons) {
            gradient_sum += current_neuron.weights[n_index]*current_neuron.gradient;
        }
    	return gradient_sum;
    }
    
    public static void train (int iterations, float lr){
        for (int i = 0; i< iterations; i++){
            for (TDataSet trainData1 : trainData) {
                feedForward(trainData1.training_input);
                selfBackProp(lr, trainData1);
            }
            
        }
    }
}
