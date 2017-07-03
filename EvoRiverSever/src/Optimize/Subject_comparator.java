package Optimize;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/4/21.
 */
public class Subject_comparator implements Comparator<Subject> {
    public int compare(Subject s1,Subject s2){
        if((s1.getSubi()==s2.getSubi())&&(s1.getSubj()==s2.getSubj())){return 0;}
        if(s1.getSubi()<s2.getSubi()) return -1;
        else{
            if(s1.getSubi()>s2.getSubi()) return 1;
            if(s1.getSubi()==s2.getSubi()){
                if(s1.getSubj()<s2.getSubj()) return -1;
                else return 1;
            }
        }
        return 0;
    }

}
