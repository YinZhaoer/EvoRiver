package Optimize;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class Topic implements Cloneable {
    private int former_order;
    private int order;
    private double vaule;
    private int id;
    public Topic(){}
    public Topic(int former_order, int order, double vaule, int id){
        this.former_order=former_order;
        this.order=order;
        this.vaule=vaule;
        this.id=id;
    }
    public void setFormer_order(int former_order){this.former_order=former_order;}
    public int getFormer_order(){return former_order;}
    public void setOrder(int order){this.order=order;}
    public int getOrder(){return  order;}
    public void setVaule(double vaule){this.vaule=vaule;}
    public double getVaule(){return vaule;}
    public void setId(int id){this.id=id;}
    public int getId(){return id;}
    //实现clone方法
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

}


