
import java.awt.*;

public class PlayerGen extends Entity{
    private Image p;
    public boolean up = false;
    public boolean left = false;
    public boolean right = false;
    private WorldGen panel;
    private boolean stall = false;
    private Inventory inv;
    public static final int width = 48;
    public static final int height = 64;

    public PlayerGen(){
        super(Main.getWorldGen(), width, height);
        panel = Main.getWorldGen();
        setX(panel.getWidth()/2);
        setY(panel.getSpawnY(getX()) + 70);
        inv = new Inventory(new Dimension(panel.getWidth(), panel.getHeight()));
        setImage("Stand");
    }

    public PlayerGen(WorldGen panel){
        super(panel, 48, 64);
        setX(panel.getWidth()/2);
        setY(panel.getSpawnY(getX()) + 70);
        this.panel = panel;
        inv = new Inventory(new Dimension(panel.getWidth(), panel.getHeight()));
        setImage("Stand");
    }

    public Inventory getInv(){
        return inv;
    }
    public void move(){
        if(!up)
           fall();
        if(up){
            check();
            if(up) {
                stall = false;
                if(!jump(2)) up = false;
            }
        }
        if(right){
            check();
            if(right)
                super.move(2);
        }
        if(left){
            check();
            if(left)
                super.move(-2);
        }
    }
    public void setImage(String dir){
        super.setImage("Images/Player/" + dir + ".png");
    }
//    public void jump(){
//            if(!stall) {
//                if (counter < 10) {
//                    super.moveUp(-6);
//               } else if (counter < 20 && counter > 10)
//                    super.moveUp(-4);
//                else if (counter < 30 && counter > 20)
//                    super.moveUp(-2);
//                else if (counter < 50 && counter > 40)
//                    if(getBelow().size() >= 1){
//                        counter = 80;
//                    } else
//                        super.moveUp(2);
//                    else if (counter < 60 && counter > 50)
//                        if(getBelow().size() >= 1){
//                            counter = 80;
//                        } else
//                            super.moveUp(4);
//                        else if (counter < 70 && counter > 60)
//                            if(getBelow().size() >= 1){
//                                counter = 80;
//                            } else
//                                super.moveUp(6);
//                            else if (counter > 70) {
//                                counter = 0;
//                                up = false;
//                            }
//                            counter++;
//            } else {
//                if(counter > 0)
//                    super.moveUp(6);
//                counter = 0;
//                up = false;
//        }
//
//    }
    public void addItem(Block b){
        inv.addItem(b);
    }
    public void check(){
        if(up){
            int height = 64;
            if(getY() + height/2.0 <= 0){
                panel.changeUnder(1);;
                panel.switchFrame(-5);
                super.setY(panel.getHeight() - height);
            }
            if(!check(up, right, left)[0]){
                up = false;
                stall = true;
            }
        }
        int width = 64;
        if(right){
            if(super.getX() + width /2 >= panel.getWidth()){
                 super.setX(0);
                panel.changeScreen(1);
                panel.switchFrame(1);
            }
            if(!super.check(up, right, left)[1]){
                right = false;
            }

        }if(left){
            if(super.getX() + width /2 <= 0){
                super.setX(panel.getWidth() - width);
                panel.changeScreen(-1);
                panel.switchFrame(-1);
            }
            if(!super.check(up, right, left)[2]){
                left = false;
            }
        }
    }

    public void fall(){
         super.fall();
         if(super.getY() > panel.getHeight()){
             panel.changeUnder(-1);
             panel.switchFrame(-5);
             setY(-64);
         }
    }
}
