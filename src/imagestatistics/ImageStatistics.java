/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package imagestatistics;

import java.awt.image.BufferedImage;
import java.lang.*;

/**
 *
 * @author jonat
 */
public class ImageStatistics {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         // 1- Read an image:
        BufferedImage inImage = ImageIo.readImage("utb.jpg");
        ImageIo.getBufferedImageType(inImage,"Main-Example-01: InImage");
        
        // 2- Convert to Gray (3-channels go to one)
        BufferedImage grayImage = ImageIo.toGray(inImage);
        
        //3 Extract 2-d byte arrays
        //Get 1 array
        byte[][] byteData = ImageIo.getGrayByteImageArray2DFromBufferedImage(grayImage);
        
        // get size of image for normalizing histogram
        int imageSize = byteData.length * byteData[0].length;

        // get the stats of the gray image
        Statistics mystats = new Statistics();
        float imageavg = mystats.getImageAverage(byteData);
        float imagevar = mystats.getImageVariance(byteData);
        double imagestd = Math.sqrt((double) imagevar);
        int minimum = mystats.getMinimumValue(byteData);
        int maximum = mystats.getMaximumValue(byteData);
        int range = maximum - minimum;
        
        // gets histogram and normalized histogram
        int[] histogram = mystats.getHistogram(byteData);
        double[] normHistogram = mystats.normalizeHistogram(histogram, imageSize);
        
        // prints the normalized histogram and checks if the values add up to 1
        double testifNorm = 0.0;
        for (int i = 0; i < normHistogram.length; i++) {
            System.out.println(i + "\t" + normHistogram[i]);
            testifNorm += normHistogram[i];
        }
        System.out.println("The sum of all normalized histogram values: " + testifNorm);
        // output: 0.9999999999999997
        // there is some data loss from the really big decimal numbers
        
        // gets the maximum count for the scaled histogram
        int maxCount = mystats.getMaximumCount(histogram);
        
        // gets scaled histogram and create histogram image
        int[] scaledHist = mystats.scaledImageHistogram(histogram, maxCount, maximum);
        byte[][] histogramImage = mystats.createHistoImage(scaledHist);

        
        System.out.println("the image average is: " + imageavg);
        System.out.println("the image variance is: " + imagevar);
        System.out.println("the image std is: " + imagestd);
        System.out.println("the image minimum gray level is: " + minimum);
        System.out.println("the image maximum gray level is: " + maximum);
        System.out.println("the image range of gray levels is: " + range + "(" + minimum + " - " + maximum + ")");

        
        BufferedImage outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(byteData);
        BufferedImage histoImage = ImageIo.setGrayByteImageArray2DToBufferedImage(histogramImage);
        
        
        //6- Write out as a file
        ImageIo.writeImage(outImage, "jpg", "Statistics.jpg");
        ImageIo.writeImage(histoImage, "jpg", "histogram.jpg");
    }
    
}
