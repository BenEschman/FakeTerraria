public class Pig extends Entity{

    private int dirCounter = 0;
    private int direction;
    private final int[] frame;
    private WorldGen panel;
    public static final int width = 64;
    public static final int height = 32;


    public Pig(WorldGen panel, int x, int y, int[] frame){
        super(panel, width, height);
        this.frame = frame;
        this.panel = panel;
        setImage("Pig");
        setX(x);
        setY(y);
    }
    public Pig(WorldGen panel, int i){
           super(panel, 64, 32);
           frame = panel.getScreen();
           this.panel = panel;
           setImage("Pig");
           setX((int)(Math.random() * panel.getWidth()));
           setY(panel.getSpawnY(getX()) + 35);
    }
    public void setImage(String dir){
        super.setImage("Images/Creatures/Animals/" + dir + ".png");
    }
    public void move(){
        if(getInScreen())
            fall();
        if(dirCounter == 0){
            double ran = Math.random();
            if(ran > 0.7) {
                direction = 1;
                setImage("Pig");
            }
            else if(ran > 0.5) {
                direction = -1;
                setImage("PigL");
            }
            else direction = 0;
            dirCounter = (int)(Math.random() * 60) + 50;
        }
        if(check(false)) {
            if (panel.getWidth() < 1 + getX()){
                setX(0);
                frame[0] = frame[0] + 1 ;
            }
            if (0 > getX() - 1){
                setX(panel.getWidth());
                frame[0] = frame[0] - 1;
            }
            move(direction);
        } else{
            jump(1);
        }
        if(counter > 0) jump(3);
        dirCounter --;
    }
    public void fall(){
        super.fall();
        if(getY() > panel.getHeight()){
            setY(0);
            frame[1] = frame[1] - 1;
        }

    }
    public boolean getInScreen(){
        return panel.getScreen()[0] == frame[0] && panel.getScreen()[1] == frame[1];
    }
    public boolean check(boolean up){
        boolean right = direction > 0;
        if(right){
            return check(up, right, false)[1];
        }
        else {
            return check(up, right, true)[2];
        }
    }
    public int[] getFrame(){
        return frame;
    }
}
