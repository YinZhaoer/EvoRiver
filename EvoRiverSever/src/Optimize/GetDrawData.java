package Optimize;

import java.util.ArrayList;

/**
 * Created by ZY on 2017/5/21.
 */
public class GetDrawData {
    public GetDrawData(){};
    public double[][] getDrawCoordinates(ArrayList timestamps,double[]xx){
        Time_Stamp timestamp= (Time_Stamp) timestamps.get(0);
        int n_times=timestamps.size();
        int n_topics=timestamp.getOrdedtopic().size();
        double[][] Coordinates=new double[n_topics][n_times];
        for(int i=0;i<n_times;i++){
            Time_Stamp t1= (Time_Stamp) timestamps.get(i);
            ArrayList topics=t1.getOrdedtopic();
            for(int j=0;j<n_topics;j++){
                Topic t2= (Topic) topics.get(j);
                int order=t2.getId();
                Coordinates[order][i]=xx[i*n_topics+j];
            }
        }
        return Coordinates;
    }
    public double[][] getDrawWidth(ArrayList timestamps,double[][] data,double F){
        Time_Stamp timestamp= (Time_Stamp) timestamps.get(0);
        int n_times=timestamps.size();
        int n_topics=timestamp.getOrdedtopic().size();
        double[][] Width=new double[n_topics][n_times];
        for(int i=0;i<n_times;i++){
            Time_Stamp t1= (Time_Stamp) timestamps.get(i);
            ArrayList topics=t1.getOrdedtopic();
            for(int j=0;j<n_topics;j++){
                Topic t2= (Topic) topics.get(j);
                int order=t2.getId();
                Width[order][i]=Math.abs(data[i][order]*F);
            }
        }
        return Width;

    }
}
