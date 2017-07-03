package Optimize;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class Discrete_optimation {
    public void topic_order(ArrayList Ordered_topic){
        Topic_comparator comparator=new Topic_comparator();
        Collections.sort(Ordered_topic,comparator);
        //排序，排完顺序后变化order和formerorder
        for(int i=0;i<Ordered_topic.size();i++){
            Topic t_temp= (Topic) Ordered_topic.get(i);
            int  n_temp=t_temp.getOrder();
            t_temp.setOrder(i);
            t_temp.setFormer_order(n_temp);
        }
    }
    public void R_timestamp_order(ArrayList time_stamps){
        R_Time_stamp_comparator comparator=new R_Time_stamp_comparator();
        Collections.sort(time_stamps,comparator);
        }

    public void D_timestamp_order(ArrayList times_stamps){
        D_Time_stamp_comparator comparator=new D_Time_stamp_comparator();
        Collections.sort(times_stamps,comparator);
         }
    //多次扫描排序
    public void time_stamp_order(ArrayList time_stamps,ArrayList topics,double[][]data,int times){
        for(int t=0;t<times;t++){
            //逆序
            for(int i=time_stamps.size()-1;i>-1;i--){
                for(int j=0;j<topics.size();j++){
                    Topic order_temp=(Topic) topics.get(j);
                    order_temp.setVaule(data[i][j]);
                }
                Time_Stamp change=new Time_Stamp(topics,i+"",i);
                time_stamps.set(i,change);
            }
            //正序
            for(int i=0;i<time_stamps.size();i++){
                for(int j=0;j<topics.size();j++) {
                    Topic temp= (Topic) topics.get(j);
                    temp.setVaule(data[i][j]);
                }
                Time_Stamp change=new Time_Stamp(topics,i+"",i);
                time_stamps.set(i,change);
            }


        }
    }




    }

