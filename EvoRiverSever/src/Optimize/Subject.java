package Optimize;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
//???
public class Subject {
    private int subi;
    private int subj;

    public Subject(){}

    public Subject(int i,int j){
        this.subi=i;
        this.subj=j;
    }

    public boolean equals(Subject s){
        if((s.getSubi()==this.subi)&&(s.getSubj()==this.subj)){return true;}
        else {return false;}
    }

    public int getSubi() {
        return subi;
    }

    public void setSubi(int subi) {
        this.subi = subi;
    }

    public int getSubj() {
        return subj;
    }

    public void setSubj(int subj) {
        this.subj = subj;
    }
}
