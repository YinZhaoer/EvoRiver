package Optimize;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
//用克隆解决共用内存的问题，使得计算和储存分开，不过这样会需要额外的一份储存空间
public class Time_Stamp implements Cloneable {
    private String id;
    private int order;
    private int Top_number;
    private int Bottom_number;
    private ArrayList Ordered_topic;
    private ArrayList Top_list;
    private ArrayList Bottom_list;
    public Time_Stamp(){}//构造函数是没有类型的
    //注意这里传递的是引用，共用内存
    public Time_Stamp(ArrayList topic,String id,int order){
        this.id=id;
        this.order=order;
        int top_number=0;
        int bottom_number=0;
        this.Ordered_topic=new ArrayList();
        for(int i=0;i<topic.size();i++){
            Topic t_temp= (Topic) topic.get(i);
            double compare=t_temp.getVaule();
            if(compare<0){bottom_number++;}
            else if(compare>0){top_number++;}
            Ordered_topic.add((Topic) topic.get(i));
        }
        Discrete_optimation a=new Discrete_optimation();
        a.topic_order(Ordered_topic);
        for(int i=0;i<Ordered_topic.size();i++){
            Topic ttemp=(Topic) Ordered_topic.get(i);
            try {
                Topic tttemp= (Topic) ttemp.clone();//这里排完序在复制，保证传入的topic也排过序
                Ordered_topic.set(i,tttemp);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

        }
        this.Top_number=top_number;
        this.Bottom_number=bottom_number;
        this.Top_list=new ArrayList(top_number);
        //先排完序赋给才能这样顺序赋值
        for(int i=0;i<top_number;i++){this.Top_list.add(Ordered_topic.get(i));}
        this.Bottom_list=new ArrayList(bottom_number);
        for(int i=top_number;i< Ordered_topic.size();i++){this.Bottom_list.add(Ordered_topic.get(i));}

    }
    public int getorder(){return  order;}
    public ArrayList getOrdedtopic(){return  Ordered_topic;}

    public ArrayList getTop_list() {
        return Top_list;
    }

    public ArrayList getBottom_list() {
        return Bottom_list;
    }
}
