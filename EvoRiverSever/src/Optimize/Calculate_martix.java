package Optimize;

import java.util.*;

/**
 * Created by Administrator on 2017/1/19 0019.计算Q以及相关求解所需的参数
 */
public class Calculate_martix {
    //flag用于判断平方项是否置4
    private int flag=-1;
    //flagQ用于最后的Q矩阵生成
    private int flagQ=0;
    //左边部分系数
    private double a=1;
    //右边部分系数
    private double b=20;
    private int numcoordinates;
    private Subject s=new Subject();
    private int[] qsubi;
    private int[] qsubj;
    private double[] qval;
    //   private Map<Subject,Double> Q=new HashMap<Subject,Double>();
    Subject_comparator subject_comparator= new Subject_comparator();
    private Map<Subject,Double> QL=new TreeMap<Subject,Double>(subject_comparator);
    private Map<Subject,Double> QR=new TreeMap<Subject,Double>(subject_comparator);
    private Map<Subject, Double> Q = new TreeMap<Subject,Double>(subject_comparator);
    public int[] getQsubi() {
        return qsubi;
    }

    public int[] getQsubj() {
        return qsubj;
    }

    public double[] getQval() {
        return qval;
    }

    public Map<Subject, Double> getQ() {
        return Q;
    }

    public Map<Subject, Double> getQL() {
        return QL;
    }

    public Map<Subject, Double> getQR() {
        return QR;
    }

    public int getNumcoordinates(){return numcoordinates;}

    //左边部分矩阵QL
    public void get_martixQL(ArrayList timestamps){
        Subject s=new Subject(-1,-2);
        Time_Stamp t= (Time_Stamp) timestamps.get(0);
        ArrayList Cal_length=t.getOrdedtopic();
        int n_stamps=timestamps.size();
        int n_topics=Cal_length.size();
        for(int i=0;i<n_topics;i++){
            for(int j=0;j<n_stamps-1;j++){
                Time_Stamp temp1=(Time_Stamp) timestamps.get(j);
                ArrayList a1=temp1.getOrdedtopic();
                Topic t1= (Topic) a1.get(i);
                double n1=t1.getVaule();
                Time_Stamp temp2=(Time_Stamp) timestamps.get(j+1);
                ArrayList a2=temp2.getOrdedtopic();
                Topic t2= (Topic) a2.get(i);
                double n2=t2.getVaule();
                if((n1*n2>0)&&(t1.getId()==t2.getId())){
                    Subject s1=new Subject();
                    Subject s2=new Subject();
                    Subject s4=new Subject();
                    if(j*n_topics+i==flag){s1.setSubi(flag);s1.setSubj(flag);QL.remove(s1);QL.put(s1,4*a);}
                    else{s1.setSubi(j*n_topics+i); s1.setSubj(j*n_topics+i);QL.put(s1,2*a);}
                    s2.setSubi((j+1)*n_topics+i); s2.setSubj(j*n_topics+i);QL.put(s2,-2*a);
                    s4.setSubi((j+1)*n_topics+i); s4.setSubj((j+1)*n_topics+i);QL.put(s4,2*a);
                    flag=(j+1)*n_topics+i;
                }


            }
        }

    }
    //右边部分矩阵QR
    public void get_martixQR(ArrayList timestamps){
        Time_Stamp t= (Time_Stamp) timestamps.get(0);
        ArrayList Cal_length=t.getOrdedtopic();
        int n_stamps=timestamps.size();
        int n_topics=Cal_length.size();
        for(int i=0;i<n_stamps-1;i++){
            Time_Stamp t1= (Time_Stamp) timestamps.get(i);
            Time_Stamp t2= (Time_Stamp) timestamps.get(i+1);
            ArrayList top1=t1.getTop_list();
            int n_top1=top1.size();
            ArrayList top2=t2.getTop_list();
            int n_top2=top2.size();
            ArrayList bottom1=t1.getBottom_list();
            int n_bottom1=bottom1.size();
            ArrayList bottom2=t2.getBottom_list();
            int n_bottom2=bottom2.size();
            //top1自乘
            for(int m1=0;m1<n_top1;m1++){
                for(int m2=0;m2<n_top1;m2++){
                    double varrm;
                    Subject srm;
                    if(i*n_topics+m1>i*n_topics+m2){ srm=new Subject(i*n_topics+m1,i*n_topics+m2);}//保证subi>subj,使得矩阵为下三角
                    else{  srm=new Subject(i*n_topics+m2,i*n_topics+m1);}
                    double d=(double)1/n_top1;
                    if(i==0){ varrm=d*d*2*b;}
                    else { varrm=4*d*d*b;}
                    QR.put(srm,varrm);

                }

            }
            //top1与top2互乘
            for(int m1=0;m1<n_top1;m1++){
                for(int m2=0;m2<n_top2;m2++){
                    double varrm;
                    double d1=(double)1/n_top1;
                    double d2=(double)1/n_top2;
                    Subject srm;
                    if((i+1)*n_topics+m2>i*n_topics+m1){ srm=new Subject((i+1)*n_topics+m2,i*n_topics+m1);}
                    else { srm=new Subject(i*n_topics+m1,(i+1)*n_topics+m2);}
                    varrm=-2*d1*d2*b;
                    QR.put(srm,varrm);
                }

            }
            //top2自乘
            for(int m1=0;m1<n_top2;m1++){
                for(int m2=0;m2<n_top2;m2++){
                    double varrm;
                    double d=(double)1/n_top2;
                    Subject srm;
                    if((i+1)*n_topics+m1>(i+1)*n_topics+m2){ srm=new Subject((i+1)*n_topics+m1,(i+1)*n_topics+m2);}//保证subi>subj,使得矩阵为下三角
                    else{  srm=new Subject((i+1)*n_topics+m2,(i+1)*n_topics+m1);}
                    if(i==n_stamps-2){ varrm=d*d*2*b;}
                    else { varrm=4*d*d*b;}
                    QR.put(srm,varrm);
                }

            }

            //bottom1自乘
            for(int m1=n_top1;m1<n_topics;m1++){
                for(int m2=n_top1;m2<n_topics;m2++){
                    double varrm;
                    double d=(double)1/n_bottom1;
                    Subject srm=new Subject();
                    if(i*n_topics+m1>i*n_topics+m2){ srm=new Subject(i*n_topics+m1,i*n_topics+m2);}//保证subi>subj,使得矩阵为下三角
                    else{  srm=new Subject(i*n_topics+m2,i*n_topics+m1);}
                    if(i==0){ varrm=d*d*2*b;}
                    else { varrm=4*d*d*b;}
                    QR.put(srm,varrm);
                }

            }
            //bottom1与bottom2互乘
            for(int m1=n_top1;m1<n_topics;m1++){
                for(int m2=n_top2;m2<n_topics;m2++){
                    double varrm;
                    double d1=(double)1/n_bottom1;
                    double d2=(double)1/n_bottom2;
                    Subject srm;
                    if((i+1)*n_topics+m2>i*n_topics+m1){ srm=new Subject((i+1)*n_topics+m2,i*n_topics+m1);}
                    else { srm=new Subject(i*n_topics+m1,(i+1)*n_topics+m2);}
                    varrm=-2*d1*d2*b;
                    QR.put(srm,varrm);
                }

            }
            //bottom2自乘
            for(int m1=n_top2;m1<n_topics;m1++){
                for(int m2=n_top2;m2<n_topics;m2++){
                    double varrm;
                    double d=(double)1/n_bottom2;
                    Subject srm;
                    if((i+1)*n_topics+m1>(i+1)*n_topics+m2){ srm=new Subject((i+1)*n_topics+m1,(i+1)*n_topics+m2);}//保证subi>subj,使得矩阵为下三角
                    else{  srm=new Subject((i+1)*n_topics+m2,(i+1)*n_topics+m1);}
                    if(i==n_stamps-2){ varrm=d*d*2*b;}
                    else { varrm=4*d*d*b;}
                    QR.put(srm,varrm);
                }

            }

        }

    }





    //计算最终的系数矩阵Q，并按下标进行排序
    public void get_martixQ(Map<Subject,Double>QL,Map<Subject,Double>QR){
        Set QL_key=QL.keySet();
        Set QR_key=QR.keySet();
        Iterator<Subject> Lit = QL_key.iterator();
        Iterator<Subject> Rit = QR_key.iterator();
        //初步生成矩阵Q
        while((Lit.hasNext())&&(flagQ==0)){
            Subject sl=Lit.next();
            double varl=QL.get(sl);
            Q.put(sl,varl);
            while(Rit.hasNext()){
                Subject sr=Rit.next();
                double varr=QR.get(sr);
                Q.put(sr,varr);
            }
            flagQ=1;
            Lit = QL_key.iterator();
            Rit = QR_key.iterator();

        }
        //合并QL和QR
        while(Lit.hasNext()){
            Subject sl=Lit.next();
            double varl=QL.get(sl);
            Q.put(sl,varl);
            while(Rit.hasNext()){
                Subject sr=Rit.next();
                double varr=QR.get(sr);
                if(sl.equals(sr)){Q.remove(sl);Q.put(sl,varl+varr);}
            }
            Rit = QR_key.iterator();

        }







    }
    //得到Q元素坐标和值
    public void cal_sub_and_var(Map<Subject,Double> Q){
        ArrayList Qsubi=new ArrayList();
        ArrayList Qsubj=new ArrayList();
        ArrayList Qval=new ArrayList();
        Set Q_key=Q.keySet();
        Iterator<Subject> Qit = Q_key.iterator();
        while (Qit.hasNext()){
            Subject sq=Qit.next();
            Qsubi.add((sq.getSubi()));
            Qsubj.add(sq.getSubj());
            Qval.add(Q.get(sq));
        }
        numcoordinates=Qsubi.size();
        qsubi=new int[numcoordinates];
        qsubj=new int[numcoordinates];
        qval=new double[numcoordinates];
        for(int i=0;i<numcoordinates;i++){
            qsubi[i]=(int)Qsubi.get(i);
            qsubj[i]=(int)Qsubj.get(i);
            qval[i]=(double)Qval.get(i);
        }

    }




}

