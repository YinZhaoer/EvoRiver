package Optimize;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class R_Time_stamp_comparator implements Comparator<Time_Stamp> {//正序timestamp比较器
    public int compare(Time_Stamp t1,Time_Stamp t2){
        if(t1.getorder()<t2.getorder()) return 1;
        else return -1;
    }

}
