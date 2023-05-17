import java.io.Serializable;

public class Slot implements Serializable {

    private char type;
    private int amt;

    public Slot(char type, int amt){
        this.type = type;
        this.amt = amt;
    }
    public void changeAmt(int change){
        if(type != '\u0000')
            amt+= change;
        if(amt <= 0){
            type = '\u0000';
        }
    }
    public int getAmt(){
        return amt;
    }
    public char getType(){
        return type;
    }
    public void changeType(char type){
        this.type = type;
    }
    public void setAmt(int amt){
        this.amt = amt;
    }
}
