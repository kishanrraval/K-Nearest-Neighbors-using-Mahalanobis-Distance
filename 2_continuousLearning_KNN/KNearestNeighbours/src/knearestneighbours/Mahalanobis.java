package knearestneighbours;
import Jama.Matrix;
import java.lang.Object;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.stat.correlation.Covariance;
public class Mahalanobis 
{
    public double findMahalanobis(double[] data1, double[] data2, RealMatrix coVariance)
    {
        double data[][] = new double[data1.length][2];
        double sub[][] = new double[data1.length][1];
        for(int i = 0 ; i < data1.length ; i++)
        {
            data[i][0] = data1[i];
            data[i][1] = data2[i];
            
            sub[i][0] = data1[i] - data2[i];
        }
        
        RealMatrix realSub = MatrixUtils.createRealMatrix(sub);
        RealMatrix realSubT = realSub.transpose();
        
        
        
        RealMatrix out = realSubT.multiply(coVariance).multiply(realSub);
        
        return Math.sqrt(out.getEntry(0, 0));
    }
    
    public double[] findMahalanobis(double[] newPoint, double [][] data)
    {
        RealMatrix realData = MatrixUtils.createRealMatrix(data);
        double[] out = new double[data[0].length];
        double[] temp = new double[data.length];
        RealMatrix coVariance = new Covariance(realData.transpose()).getCovarianceMatrix();

        for(int i = 0 ; i < out.length ; i++)
        {
            for(int j = 0 ; j < temp.length ; j++)
            {
                temp[j] = data[j][i];
            }
            out[i] = findMahalanobis(temp, newPoint, coVariance);
        }
        return out;
    }
    
}
