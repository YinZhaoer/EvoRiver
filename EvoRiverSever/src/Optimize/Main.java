package Optimize;

import java.util.ArrayList;
import java.util.Map;


public class Main {
    //坐标变动范围
    private static double B = 1500;
    //top和bottom的最小坐标差
    private static double H = 300;
    //对power的放大倍数
    private static double F=20000.0;

    public static void main(String[] args) {
        Data d = new Data();
        GetDrawData getdrawdata=new GetDrawData();
        //不同的gaetdata换不同的数据
        double[][] data = d.get_NewData();
        int n_topics = data[0].length;
        int n_times = data.length;
        Discrete_optimation discrete_optimation = new Discrete_optimation();
        ArrayList topics = new ArrayList();
        Calculate_martix calq = new Calculate_martix();
        for (int i = 0; i < data[0].length; i++) {
            Topic t = new Topic(i, i, i, i);
            topics.add(t);

        }

        ArrayList time_stamps = new ArrayList();
        //完成赋值和排序(timestamp类能进行自动排序)，得到所有stamp
        for (int i = 0; i < n_times; i++) {
            for (int j = 0; j < n_topics; j++) {
                Topic temp = (Topic) topics.get(j);
                temp.setVaule(data[i][j]);
            }
            Time_Stamp temp = new Time_Stamp(topics,i+"", i);
            time_stamps.add(temp);
        }

        discrete_optimation.time_stamp_order(time_stamps, topics,data, 500);
        //将排序完成的time_stamps赋给Data
        d.set_Timestamps(time_stamps);
        //得到系数矩阵Q
        calq.get_martixQL(time_stamps);
        calq.get_martixQR(time_stamps);
        calq.get_martixQ(calq.getQL(), calq.getQR());
        calq.cal_sub_and_var(calq.getQ());



        //mosek求解程序
        int numvar = n_times * n_topics;
        int numcon= n_times * (n_topics - 1);
        double[] xx=new double[numvar];//输出结果
        double infinity = 0;
        int[][] asub = new int[numvar][];
        double[][] aval = new double[numvar][];
        mosek.Env.boundkey[] bkc=new mosek.Env.boundkey[numcon];
        double[]blc= new double[numcon];
        double[]buc=new double[numcon];

        //每个x的约束
        mosek.Env.boundkey[] bkx = new mosek.Env.boundkey[numvar];
        for (int i = 0; i <numvar; i++) {
            bkx[i] = mosek.Env.boundkey.ra;
        }
        double[] blx = new double[numvar];
        double[] bux = new double[numvar];
        for (int i = 0; i < numvar; i++) {
            blx[i] = 0.0;
            bux[i] =B;
        }
        //对A以及约束进行赋值,注意A的下标和值都是二维数组
        for(int i=0;i<n_times;i++){
            Time_Stamp time_stamp= (Time_Stamp) time_stamps.get(i);
            ArrayList topic=time_stamp.getOrdedtopic();
            int lt=time_stamp.getTop_list().size()-1;
            int fb=time_stamp.getTop_list().size();
            for(int j=0;j<n_topics;j++){
                if(j==lt||j==fb){
                    if(j==0||j==n_topics-1){
                        int[]sub=new int[1];
                        double[] val=new double[1];
                        if(j==0){
                            sub[0]=i*(n_topics-1);
                            val[0]=1.0;
                            bkc[sub[0]]=mosek.Env.boundkey.lo;
                            blc[sub[0]]=H;
                            buc[sub[0]]=+infinity;

                        }
                        if(j==n_topics-1){
                            sub[0]=(i+1)*(n_topics-1)-1;
                            val[0]=-1.0;
                            bkc[sub[0]]=mosek.Env.boundkey.lo;
                            blc[sub[0]]=H;
                            buc[sub[0]]=+infinity;
                        }
                        asub[i*n_topics+j]=sub;
                        aval[i*n_topics+j]=val;

                    }
                    else{
                        Topic t1 = (Topic) topic.get(j);
                        Topic t2 = (Topic) topic.get(j -1);
                        double b = Math.abs(t1.getVaule()) + Math.abs(t2.getVaule());
                        int[]sub=new int[2];
                        double[] val=new double[2];
                        if(j==lt){
                            sub[0]=i*(n_topics-1)+j-1;val[0]=-1.0;
                            sub[1]=i*(n_topics-1)+j;val[1]=1.0;
                            bkc[sub[1]]=mosek.Env.boundkey.lo;
                            blc[sub[1]]=H;
                            buc[sub[1]]=+infinity;

                        }
                        if(j==fb){
                            t2 = (Topic) topic.get(j+1);
                            b = Math.abs(t1.getVaule()) + Math.abs(t2.getVaule());
                            sub[0]=i*(n_topics-1)+j-1;val[0]=-1.0;
                            sub[1]=i*(n_topics-1)+j;val[1]=1.0;
                            bkc[sub[1]]=mosek.Env.boundkey.fx;
                            blc[sub[1]]=b*F;
                            buc[sub[1]]=b*F;

                        }
                        asub[i*n_topics+j]=sub;
                        aval[i*n_topics+j]=val;
                    }
                }
                else{
                    if(j==0||j==n_topics-1){
                        int[]sub=new int[1];
                        double[]val=new double[1];
                        if(j==0){
                            Topic t1 = (Topic) topic.get(j);
                            Topic t2 = (Topic) topic.get(j +1);
                            double b = Math.abs(t1.getVaule()) + Math.abs(t2.getVaule());
                            sub[0]=i*(n_topics-1);
                            val[0]=1.0;
                            bkc[sub[0]]=mosek.Env.boundkey.fx;
                            blc[sub[0]]=b*F;
                            buc[sub[0]]=b*F;
                        }
                        if(j==n_topics-1){
                            Topic t1 = (Topic) topic.get(j);
                            Topic t2 = (Topic) topic.get(j-1);
                            double b = Math.abs(t1.getVaule()) + Math.abs(t2.getVaule());
                            sub[0]=(i+1)*(n_topics-1)-1;
                            val[0]=-1.0;
                            bkc[sub[0]]=mosek.Env.boundkey.fx;
                            blc[sub[0]]=b*F;
                            buc[sub[0]]=b*F;
                        }
                        asub[i*n_topics+j]=sub;
                        aval[i*n_topics+j]=val;
                    }
                    else{
                        Topic t1 = (Topic) topic.get(j);
                        Topic t2 = (Topic) topic.get(j-1);
                        Topic t3 = (Topic) topic.get(j+1);
//                        double b1 = Math.abs(t1.getVaule()) + Math.abs(t2.getVaule());
                        double b2 = Math.abs(t1.getVaule()) + Math.abs(t3.getVaule());
                        int[]sub=new int[2];
                        double[]val=new double[2];
                        sub[0]=i*(n_topics-1)+j-1;val[0]=-1.0;
                        sub[1]=i*(n_topics-1)+j;val[1]=1.0;
                        bkc[sub[1]]=mosek.Env.boundkey.fx;
                        blc[sub[1]]=b2*F;
                        buc[sub[1]]=b2*F;
                        asub[i*n_topics+j]=sub;
                        aval[i*n_topics+j]=val;

                    }
                }
            }

        }
        //c为空
        double[] c = new double[numvar];

//

        mosek.Env
                env=null;
        mosek.Task
                task=null;
        try{
            env=new mosek.Env();
            task=new mosek.Task(env,numcon,numvar);
            task.set_Stream(
                    mosek.Env.streamtype.log,
                    new mosek.Stream(){
                        public void stream(String msg){System.out.print(msg);}
                    }
            );
            task.appendcons(numcon);
            task.appendvars(numvar);
            //输入变量的界限
            for(int j=0;j<numvar;j++){
                task.putcj(j,c[j]);
                task.putbound(mosek.Env.accmode.var,j,bkx[j],blx[j],bux[j]);
            }
           //输入A矩阵
            for(int j=0;j<numvar;j++){
                task.putacol(j,asub[j],aval[j]);

            }
            //输入Acon
            for(int i=0;i<numcon;i++){
                task.putbound(mosek.Env.accmode.con,i,bkc[i],blc[i],buc[i]);
            }

            //输入Q矩阵
            int[] qsubi=calq.getQsubi();
            int[] qsubj=calq.getQsubj();
            double[] qval=calq.getQval();
            task.putqobj(qsubi,qsubj,qval);
            mosek.Env.rescode r = task.optimize();
            System.out.println (" Mosek warning:" + r.toString());
            // Print a summary containing information
            //   about the solution for debugging purposes
            task.solutionsummary(mosek.Env.streamtype.msg);

            mosek.Env.solsta solsta[] = new mosek.Env.solsta[1];
      /* Get status information about the solution */
            task.getsolsta(mosek.Env.soltype.itr,solsta);

      /* Get the solution */
            task.getxx(mosek.Env.soltype.itr, // Interior solution.
                    xx);
            switch(solsta[0])
            {
                case optimal:
                case near_optimal:
                    System.out.println("Optimal primal solution\n");
                    for(int j = 0; j < numvar; ++j)
                        System.out.println ("x[" + j + "]:" + xx[j]);
                    break;
                case dual_infeas_cer:
                case prim_infeas_cer:
                case near_dual_infeas_cer:
                case near_prim_infeas_cer:
                    System.out.println("Primal or dual infeasibility\n");
                    break;
                case unknown:
                    System.out.println("Unknown solution status.\n");
                    break;
                default:
                    System.out.println("Other solution status");
                    break;
            }
        }
        catch (mosek.Exception e)
        {
            System.out.println ("An error/warning was encountered");
            System.out.println (e.toString());
            throw e;
        }
        finally
        {
            if (task != null) task.dispose ();
            if (env  != null)  env.dispose ();
        }




        double Coordinates[][]=getdrawdata.getDrawCoordinates(time_stamps,xx);
        System.out.println("Coordinates:");
        for(int j=0;j<n_topics;j++){
            System.out.print("[");
        for(int i=0;i<n_times;i++){
            System.out.print(Coordinates[j][i]);
            if(i!=n_times-1){
            System.out.print(",");}
        }
            System.out.println("],");}



        double Width[][]=getdrawdata.getDrawWidth(time_stamps,data,F);
        System.out.println("Width:");
        for(int j=0;j<n_topics;j++){
            System.out.print("[");
            for(int i=0;i<n_times;i++){
                System.out.print(Width[j][i]);
                if(i!=n_times-1){
                    System.out.print(",");}
            }
            System.out.println("],");}


    }

}

