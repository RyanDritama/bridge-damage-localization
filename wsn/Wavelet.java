package com.tugas_akhir.sink.wsn;

public class Wavelet {
    public  double[] getLowPass(int wavelet_code, int wavelet_order) {
		if (wavelet_code == 0) { // haar
			double[] f = { .707106781186547, .707106781186547 };
			return f;
		}
		else if (wavelet_code == 1) { // daubechies
			switch (wavelet_order) {
			case 4: { // (Wickerhauser)
				double[] f = { .482962913144534160, .836516303737807940,
						.224143868042013390, -.129409522551260370 };
				return f;

			}
			case 6: {// (Wickerhauser)
				double[] f = { 0.332670552950082630, 0.806891509311092550,
						0.459877502118491540, -0.135011020010254580,
						-0.0854412738820266580, 0.0352262918857095330 };
						
				return f;

			}
			case 8: {// (Wickerhauser but order reversed)
				double[] f = { 0.2303778133090, 0.7148465705530,
						0.6308807679300, -0.02798376941700, -0.1870348117190,
						0.03084138183700, 0.032883011667, -0.01059740178500 };

				return f;

			}
			case 10: {// (Wickerhauser but order reversed)
				double[] f = { 0.160102397974, 0.603829269797, 0.724308528438,
						0.138428145901, -0.242294887066, -0.032244869585,
						0.07757149384, -0.006241490213, -0.012580751999,
						0.003335725285 };
				return f;

			}
			case 12: {// (Wickerhauser but order reversed)
				double[] f = { 0.11154074335, 0.494623890398, 0.751133908021,
						0.315250351709, -0.226264693965, -0.129766867567,
						0.097501605587, 0.02752286553, -0.031582039318,
						0.000553842201, 0.004777257511, -0.001077301085 };
				return f;

			}
			case 14: {// (Wickerhauser but order reversed)
				double[] f = { 0.077852054085, 0.396539319482, 0.729132090846,
						0.469782287405, -0.143906003929, -0.224036184994,
						0.071309219267, 0.080612609151, -0.038029936935,
						-0.016574541631, 0.012550998556, 0.000429577973,
						-0.001801640704, 0.0003537138 };
				return f;

			}
			case 16: {// (Wickerhauser but order reversed)
				double[] f = { 0.054415842243, 0.312871590914, 0.675630736297,
						0.585354683654, -0.015829105256, -0.284015542962,
						0.000472484574, 0.12874742662, -0.017369301002,
						-0.044088253931, 0.013981027917, 0.008746094047,
						-0.004870352993, -0.000391740373, 0.000675449406,
						-0.000117476784 };
				return f;

			}
			case 18: {// (Wickerhauser but order reversed)
				double[] f = { 0.038077947364, 0.243834674613, 0.60482312369,
						0.657288078051, 0.133197385825, -0.293273783279,
						-0.096840783223, 0.148540749338, 0.030725681479,
						-0.067632829061, 0.000250947115, 0.022361662124,
						-0.004723204758, -0.004281503682, 0.001847646883,
						0.000230385764, -0.000251963189, 0.00003934732 };
				return f;

			}
			case 20: {// Wavelab src
				double[] f = { 0.026670057901, 0.188176800078, 0.527201188932,
						0.688459039454, 0.281172343661, -0.249846424327,
						-0.195946274377, 0.127369340336, 0.093057364604,
						-0.071394147166, -0.029457536822, 0.033212674059,
						0.003606553567, -0.010733175483, 0.001395351747,
						0.001992405295, -0.000685856695, -0.000116466855,
						0.00009358867, -0.000013264203 };
				return f;

			}
        }}
		else if (wavelet_code == 2) {// Wavelab src coiflet
			switch (wavelet_order) {
			case 6: {
				double[] f = { .038580777748, -.126969125396, -.077161555496,
						.607491641386, .745687558934, .226584265197 };
				return f;
			}
			case 12: {
				double[] f = { .016387336463, -.041464936782, -.067372554722,
						.386110066823, .812723635450, .417005184424,
						-.076488599078, -.059434418646, .023680171947,
						.005611434819, -.001823208871, -.000720549445 };
				;
				return f;
			}
			case 18: {
				double[] f = { -.003793512864, .007782596426, .023452696142,
						-.065771911281, -.061123390003, .405176902410,
						.793777222626, .428483476378, -.071799821619,
						-.082301927106, .034555027573, .015880544864,
						-.009007976137, -.002574517688, .001117518771,
						.000466216960, -.000070983303, -.000034599773 };

				return f;

			}
			case 24: {
				double[] f = { .000892313668, -.001629492013, -.007346166328,
						.016068943964, .026682300156, -.081266699680,
						-.056077313316, .415308407030, .782238930920,
						.434386056491, -.066627474263, -.096220442034,
						.039334427123, .025082261845, -.015211731527,
						-.005658286686, .003751436157, .001266561929,
						-.000589020757, -.000259974552, .000062339034,
						.000031229876, -.000003259680, -.000001784985 };
				return f;

			}
			case 30: {
				double[] f = { -.000212080863, .000358589677, .002178236305,
						-.004159358782, -.010131117538, .023408156762,
						.028168029062, -.091920010549, -.052043163216,
						.421566206729, .774289603740, .437991626228,
						-.062035963906, -.105574208706, .041289208741,
						.032683574283, -.019761779012, -.009164231153,
						.006764185419, .002433373209, -.001662863769,
						-.000638131296, .000302259520, .000140541149,
						-.000041340484, -.000021315014, .000003734597,
						.000002063806, -.000000167408, -.000000095158 };
				return f;

			}
        }} 
        else {
            double[] f = {0};
            return f;
        }    
        double[] f = {0};
        return f; 
    }

    public  float[] getHighPass(float[] lowpass){
        int sign = 1;
        int kernel_len = lowpass.length;
        float[] G = new float[kernel_len];

        for (int i = 0; i < kernel_len; i++)
        {
            G[i] = lowpass[kernel_len - i -1] * sign;
			sign = sign * -1;
        }

        return G;
	}

	public  float roundNearestDecimal(double data, int decimal_point){
		return (float)(Math.round(data*Math.pow(10,decimal_point))/Math.pow(10,decimal_point)); 
	}

	public  float[] roundArray2Float(double[] data, int decimal_point){
		float[] result = new float[data.length];
		for (int i = 0; i < data.length; i++)
		{
			result[i] = roundNearestDecimal(data[i], decimal_point);
		}

		return result;
	}
	
	public  float mean(float[] array){
		float sum = 0;

		for (int i = 0; i< array.length; i++)
		{
			sum += array[i];
		}

		float mean = sum/array.length;
		return mean;
	}

	public  float std(float[] array, float mean){
		float sum = 0;

		for (int i = 0; i< array.length; i++)
		{
			sum += (array[i] - mean)*(array[i] - mean);
		}

		float std = (float)Math.sqrt(sum/array.length);
		return std;
	}

	public  void removeMean(float[] array){
		float mean = mean(array);

		for (int i = 0; i < array.length; i++)
		{
			array[i] -= mean;
		}

	}

    public  float[] circConvDsmp2(float[] signal, float[] kernel, int signal_len){
        float[] result = new float[signal_len/2];
        int kernel_len = kernel.length;
        int index = 0;

        for(int n = 1 ; n < signal_len; n+=2)
        {
            for (int k =  0;  k < kernel_len; k++){
                
                if (n - k < 0){
                    index = signal_len + n - k;
                }                  
                else{ 
                    index = n - k;
                }
                
                result[n/2] = result[n/2] + signal[index]*kernel[k];
            }
        }
        
        return(result);
	}
	
	public  float[] circConvDsmp2(float[] signal, float[] kernel, int signal_len, int start_index){
        float[] result = new float[signal_len/2];
        int kernel_len = kernel.length;
        int index = 0;

        for(int n = 1 ; n < signal_len; n+=2)
        {
            for (int k =  0;  k < kernel_len; k++){
                
                if (n - k < 0){
                    index = start_index + signal_len + n - k;
                }                  
                else{ 
                    index = start_index + n - k;
                }
                
                result[n/2] = result[n/2] + signal[index]*kernel[k];
            }
        }
        
        return(result);
    }

	public  float[] circCorrUpsmp2(float[] signal, float[] kernel, int signal_len, int start_index){
		float[] result = new float[signal_len*2];
		float[] signal_upsmp2 = new float[signal_len*2];
        int kernel_len = kernel.length;
		int index = 0;
		int result_index = 0;
		
		for(int i = 0; i < signal_len*2; i++)
		{
			if (i % 2 == 0)
			{
				signal_upsmp2[i] = 0;
			}
			else 
			{
				signal_upsmp2[i] = signal[start_index + (int)Math.floor(i/2)];
			}
		}

        for(int n = 0 ; n < signal_len*2; n++)
        {
            for (int k =  0;  k < kernel_len; k++){
                
                if (n - k < 0){
                    index = signal_len*2 + n - k;
                }                  
                else{ 
                    index = n - k;
				}
				
				if (n < kernel_len - 1){
					result_index = signal_len*2 + n - kernel_len + 1;
				}
				else{
					result_index = n - kernel_len + 1;
				}
                
				result[result_index] = result[result_index] + signal_upsmp2[index]*kernel[kernel_len - k - 1];
				// System.out.println(n);
            }
        }
        
        return(result);
	}
		
    public  float[] forwardDWT(float[] signal, int level, int wavelet_code, int wavelet_order){
        int n = signal.length;
        float[] H = roundArray2Float(getLowPass(wavelet_code, wavelet_order),6);
		float[] G = getHighPass(H);
        for (int i = 0; i < level; i++) 
        {
			int sub_length = n / (int) (Math.pow(2, i));
			float[] sub_result_H = circConvDsmp2(signal, H, sub_length, 0);
			float[] sub_result_G = circConvDsmp2(signal, G, sub_length, 0);
            for (int j = 0; j < sub_length/2; j++)
            {
                signal[j] = sub_result_H[j];
            }
            for (int j = 0; j < sub_length/2; j++)
            {
				signal[j + sub_length/2] = sub_result_G[j];
            }

		}
		return signal;        
	}

	public float[] inverseDWT(float[] signal, int level, int wavelet_code, int wavelet_order){
        int n = signal.length;
        float[] H = roundArray2Float(getLowPass(wavelet_code, wavelet_order),6);
		float[] G = getHighPass(H);
		for (int i = level; i > 0; i--) 
        {
			int sub_length = n / (int) (Math.pow(2, i));
			float[] sub_result_H = circCorrUpsmp2(signal, H, sub_length, 0);
			float[] sub_result_G = circCorrUpsmp2(signal, G, sub_length, sub_length);
            for (int j = 0; j < sub_length*2; j++)
            {
                signal[j] = sub_result_H[j] + sub_result_G[j];
            }
		}
		return signal;        
	}
	
	public  float[] forwardWPT(float[] signal, int level, int wavelet_code, int wavelet_order){
        int n = signal.length;
        float[] H = roundArray2Float(getLowPass(wavelet_code, wavelet_order),6);
		float[] G = getHighPass(H);
        for (int i = 0; i < level; i++) 
        {
			int sub_length = n / (int) (Math.pow(2, i));
			for (int j = 0; j < Math.pow(2,i); j++)
			{
				float[] sub_result_H = circConvDsmp2(signal, H, sub_length, j*sub_length);
				float[] sub_result_G = circConvDsmp2(signal, G, sub_length, j*sub_length);
				for (int k = 0; k < sub_length/2; k++)
				{
					signal[k + j*sub_length] = sub_result_H[k];
				}
				for (int k = 0; k < sub_length/2; k++)
				{
					signal[k + sub_length/2 + j*sub_length] = sub_result_G[k];
				}
			}
		}
		return signal;        
    }

	public  float[] inverseWPT(float[] signal, int level, int wavelet_code, int wavelet_order){
        int n = signal.length;
		float[] H = roundArray2Float(getLowPass(wavelet_code, wavelet_order),6);
		float[] G = getHighPass(H);
        for (int i = level; i > 0; i--) 
        {
			int sub_length = n / (int) (Math.pow(2, i));
			for (int j = 0; j < Math.pow(2,i); j+=2)
			{
				float[] sub_result_H = circCorrUpsmp2(signal, H, sub_length, j*sub_length);
				float[] sub_result_G = circCorrUpsmp2(signal, G, sub_length, (j+1)*sub_length);
				for (int k = 0; k < sub_length*2; k++)
				{
					signal[k + j*sub_length] = sub_result_H[k] + sub_result_G[k];
				}
			}
		}
		return signal;        
	}
	
	public  float[] calcEnergyDWT(float[] dwt, int level){
		int n = dwt.length;
		float[] energy_cmpnt = new float[level+1];
		int start_index = 0;
		int sub_length = n / (int)(Math.pow(2,level));

		for (int j = start_index; j < start_index + sub_length; j++)
		{
			energy_cmpnt[0] += dwt[j]*dwt[j];
		}
		start_index += sub_length;

		for(int i = level; i > 0; i--)
		{
			sub_length = n / (int)(Math.pow(2,i));
			for (int j = start_index; j < start_index + sub_length; j++)
			{
				energy_cmpnt[level - i + 1] += dwt[j]*dwt[j];
			}
			start_index += sub_length;
		}

		return(energy_cmpnt);
	}

	public  float[] calcEnergyWPT(float[] wpt, int level){
		int n =wpt.length;
		float[] energy_cmpnt = new float[(int)Math.pow(2,level)];
		int start_index = 0;
		int sub_length = (int)(n/(Math.pow(2,level)));

		for(int i = 0; i < Math.pow(2,level); i++)
		{
			for (int j = start_index; j < start_index + sub_length; j++)
			{
				energy_cmpnt[i] += wpt[j]*wpt[j];
			}
			start_index += sub_length;
		}

		return(energy_cmpnt);
	}


	public  void bubbleSort(float[] mag, float[] freq){
	    float ctemp_mag;
	    float ctemp_freq;
	    boolean swapped = true;
	    while ( swapped ) {
	        swapped = false;
	        for ( int i=0;i<(mag.length-1)/2;i++ ) {
	        if (mag[i] < mag[i+1]) {
	                ctemp_mag = mag[i];
	                ctemp_freq = freq[i];
	                mag[i] = mag[i+1];
	                freq[i] = freq[i+1];
	                mag[i+1] = ctemp_mag;
	                freq[i+1] = ctemp_freq;
	                swapped = true;
	            }
	        }
	    }
	}

	public  void retainedEnergyComponent(float[] wt_energy, float[] retained, float[] retained_index){
		float[] temp_index = new float[wt_energy.length];	
		for(int i = 0; i < temp_index.length; i++){
			temp_index[i] = i;
		}	

		bubbleSort(wt_energy, temp_index);
		for(int i = 0; i < retained.length; i++){
			retained[i] = wt_energy[i];
			retained_index[i] = temp_index[i];
		}	
	}

	public  float calcSSD(float[] wpt_energy_base, float[] wpt_energy_measured, int dominant_num){
		float SSD = 0;

		for(int i = 0; i < dominant_num; i++)
		{
			SSD += (wpt_energy_measured[i] - wpt_energy_base[i])*(wpt_energy_measured[i] - wpt_energy_base[i]);
		}

		return SSD;
	}

	public  float calcSAD(float[] wpt_energy_base, float[] wpt_energy_measured, int dominant_num){
		float SAD = 0;

		for(int i = 0; i < dominant_num; i++)
		{
			SAD += Math.abs((wpt_energy_measured[i] - wpt_energy_base[i]));
		}
		return SAD;
	}

	public  float calcUCL(float[] indicator_array){
		float mean = mean(indicator_array);
		float std = std(indicator_array, mean);

		return ((float)(mean + 1.65*std/Math.sqrt(indicator_array.length)));
	}

	public  void dominantPeak(float[] array, int window_length, int peak_num, float[] peak, int[] index){
		float mean_arr = mean(array);
		float std_arr = std(array, mean_arr);
		float thresh = mean_arr + 1*std_arr;

		int n = 1;
		for (int i = window_length; i < array.length - window_length; i++){
			int j = 1;
			int larger = 1;
			if (array[i] > thresh){
				while (larger == 1 && j <= window_length){
					if (array[i] < array[i-j]){
						larger = 0;
					}
					j++;
				}	
				if (larger == 1){
					j = 1;
					while (larger == 1 && j <= window_length){
						if (array[i] < array[i+j]){
							larger = 0;
						}
						j++;
					}
					if (larger == 1){
						float swap_p = array[i];
						int swap_i = i;
						for (n = 0; n < peak_num; n++){
							if (swap_p > peak[n]){
								float temp_p = peak[n];
								peak[n] = swap_p;
								swap_p = temp_p;
								
								int temp_i = index[n];
								index[n] = swap_i;
								swap_i = temp_i;
							}
						}
					}
				}
			}
		}
	}
}