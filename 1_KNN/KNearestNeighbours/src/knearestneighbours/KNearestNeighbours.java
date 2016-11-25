package knearestneighbours;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.Arrays;

public class KNearestNeighbours {

    public static void main(String[] args) 
    {
        int k = 2;
        int cor = 0, wrong = 0;
        int noClasses;
        
        double[][] rawData; 
        double[][] testRawData;
        
        //rawData = getRawData("train_heart.txt",224, 14);  noClasses = 2;
        //rawData = getRawData("train_heart-1.txt",103, 14);  noClasses = 5;
        //rawData = getRawData("train_hillValley.txt",606, 101);  noClasses = 2;
        rawData = getRawData("train_wine.txt",146, 14); noClasses = 3;
        
        //testRawData = getRawData("test_heart.txt", 46, 14);
        //testRawData = getRawData("test_heart-1.txt", 100, 14);
        //testRawData = getRawData("test_hillValley.txt", 606, 101);
        testRawData = getRawData("test_wine.txt", 32, 14);
        
        
        double[][] data = getData(rawData);
        int[] cls = getClass(rawData);
        
        Mahalanobis m = new Mahalanobis();
        
        
        
        double[][] testData = getData(testRawData);
        int[] testClass = getClass(testRawData);
        int[] testClassNew = new int[testClass.length];
        
        double[] temp = new double[testData.length];
        int t, cnt0, cnt1;
        int cnt[] = new int[noClasses];
        for(int i = 0 ; i < noClasses ; i++)
            cnt[i] = 0;
        
        for(int i = 0 ; i < testData[0].length ; i++)
        {
            for(int j = 0 ; j < testData.length ; j++)
                temp[j] = testData[j][i];
            double distances[] = m.findMahalanobis(temp, data);
            double tmp[] = new double[distances.length];
            System.arraycopy( distances, 0, tmp, 0, distances.length );
            Arrays.sort(distances);
            cnt0 = cnt1 = 0;
            for(int j = 0 ; j < k ; j++)
            {
                int index = -1;
                for(int p = 0; (p < distances.length) && (index == -1); p++) 
                {
                    if (tmp[p] == distances[j]) {
                        index = p;
                    }
                }
                /*if(cls[index] == 0)
                    cnt0++;
                else if(cls[index] == 1)
                    cnt1++;*/
                cnt[cls[index]]++;
            }
            /*if(cnt0 > cnt1)
            {
                testClassNew[i] = 0;
            }
            else
            {
                testClassNew[i] = 1;
            }*/
            int max = -1, index = -1;
            for(int p = 0 ; p < noClasses ; p++)
            {
                if(max < cnt[p])
                {
                    max = cnt[p];
                    index = p;
                }
            }
            testClassNew[i] = index;
            if(testClassNew[i] == testClass[i])
            {
                //System.out.println("Correct");
                cor++;
            } 
            else{
                //System.out.println("Wrong"); 
                wrong++;
            }
                
            
        }
        System.out.println("Correct: " + cor);
        System.out.println("Wrong: " + wrong);
    }
    
    public static double[][] getData(double[][] data)
    {
        double [][]out = new double[data.length-1][data[0].length];
        
        for(int i=0; i<out.length; i++)
            for(int j=0; j<out[0].length; j++)
                out[i][j]=data[i][j];
        
        return out;
    }
    
    public static int[] getClass(double[][] data)
    {
        int out[] = new int[data[0].length];
        for(int i = 0 ; i < out.length ; i++)
        {
            out[i] = (int)data[data.length-1][i];
        }
        
        return out;
    }
    
    
    public static double[][] getRawData(String file, int col, int row)
    {
        BufferedReader buffer = null;
        double[][] out = new double[row][col];
        String s = "";
        try {
            buffer = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KNearestNeighbours.class.getName()).log(Level.SEVERE, null, ex);
        }
        int c = 0;
        
        try 
        {
            while((c = buffer.read()) != -1) 
            {
                char character = (char) c;
                if(character == ',')
                    character = ' ';
                if(character != '\r' && character != '\n')
                    s += character;
                if(character == '\n')
                    s += " ";
            }
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
        Scanner input = new Scanner(s);
        for(int i = 0 ; i < out[0].length ; i++)
        {
            for(int j = 0 ; j < out.length ; j++)
            {
                double temp = input.nextDouble();
                out[j][i] = temp;
            }
        }
        return out;
    }
    
}
