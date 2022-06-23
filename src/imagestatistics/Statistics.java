/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package imagestatistics;

/**
 *
 * @author jonat
 */
public class Statistics {
    
    // gets the minimum gray level in the image
    public int getMinimumValue(byte[][] inArray) {
        // initialize the first number in the 2-D array as the minimum
        int minimum = inArray[0][0] & 0xFF;
        
        int h = inArray.length;
        int w = inArray[0].length;
        
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                // compare all values to the minimum
                if ((inArray[i][j] & 0xFF) <= minimum)
                    minimum = inArray[i][j] & 0xFF;
                
            }
        }
        
        return minimum;
    }
    
    // gets the maximum gray level in the image
    public int getMaximumValue(byte[][] inArray) {
        // initialize the first number in the 2-D array as the maximum
        int maximum = inArray[0][0] & 0xFF;
        
        int h = inArray.length;
        int w = inArray[0].length;
        
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                // compare all values to the maximum
                if ((inArray[i][j] & 0xFF) >= maximum)
                    maximum = inArray[i][j] & 0xFF;
                
            }
        }
        
        return maximum;
    }
    
    // get the maximum count from histogram
    public int getMaximumCount(int[] histogram) {
        // initialize the first number in the array as the maximum
        int maxCount = histogram[0];
        
        for(int i = 0; i < histogram.length; i++) {
            // compare all values to the maxCount
            if (histogram[i] >= maxCount) {
                maxCount = histogram[i];
            }
        }
        return maxCount;
    }
    
    // creates scaled histogram given the original histogram and maxcount
    public int[] scaledImageHistogram(int[] originalhist, int maxcount, int maxGrayLevel) {
        int[] scaledHistogram = new int[256];
        float alpha = (float) maxGrayLevel / maxcount;
        
        for (int i = 0; i < scaledHistogram.length; i++) {
            scaledHistogram[i] = 0;
        }
        
        for (int i = 0; i < scaledHistogram.length; i++){
            scaledHistogram[i] = (int) (originalhist[i] * alpha);
            if (scaledHistogram[i] > maxGrayLevel){
                scaledHistogram[i] = maxGrayLevel;
            }
            if (scaledHistogram[i] < 0){
                scaledHistogram[i] = 0;
            }
        }
        return scaledHistogram;
    }
    
    // creates histogram image given the scaled histogram
    public byte[][] createHistoImage(int[] scaledHistogram) {
        byte [][] histogramImage = new byte[256][256];
        for (int j = 0; j < scaledHistogram.length; j++) {
            for (int i = 255 - scaledHistogram[j]; i < histogramImage.length; i++) {
                histogramImage[i][j] = (byte) 255;
            }
        }
        return histogramImage;
    }
    
    // creates histogram from gray image
    public int[] getHistogram(byte[][] inArray) {
        int h = inArray.length;
        int w = inArray[0].length;
        int[] histogram = new int[256];
        
        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++) {
                histogram[(inArray[i][j] & 0xFF)]++;
            }
        }
        
        return histogram;
    }
    
    // Normalizes histogram
    public double[] normalizeHistogram(int[] histogram, int imageSize) {
        int h = histogram.length;
        double[] normhist = new double[256];
        
        for (int i = 0; i < h; i++){
            normhist[i] = (double) histogram[i] / imageSize;
        }
        
        return normhist;
    }
    
    // calculates the mean value of the whole image
    public float getImageAverage(byte[][] inArray) {
        float average = 0.0f;
        int h = inArray.length;
        int w = inArray[0].length;
        
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                
                average += (inArray[i][j] & 0xFF);
            }
        }

        average = average/(h*w);
        return average;
    }
    
    // calculates the varience value of the whole image
    public float getImageVariance(byte[][] inArray) {
        float average;
        float variance = 0.0f;
        int h = inArray.length;
        int w = inArray[0].length;
        
        average = getImageAverage(inArray);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                
                variance += ((inArray[i][j] & 0xFF) - average) * ((inArray[i][j] & 0xFF) - average);
            }
        }

        variance = variance/(h*w);
        return variance;
    }
}
