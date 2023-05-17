import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Entity {
    private Image img;
    private WorldGen panel = Main.getWorldGen();
    private int x;
    private int y;
    private int height = 64;
    private int width = 48;
    private double fall = 2;
    protected int counter = 0;
    public Entity(){
    }
    public Entity(WorldGen panel, int width, int height){
        this.panel = panel;
        this.height = height;
        this.width = width;
    }

    public void draw(Graphics g){
        g.drawImage(img, x, (int)y, width, height, panel);
    }
    public void move(int x){
        this.x += x;

    }
    public Image getImage(){
        return img;
    }
    public void fall(){
        if(getBelow().size() < 1){
            moveUp(0.98 * Math.pow(fall, 2));
            fall += 0.05;
        } else{
            fall = 2;
        }
    }
    public void moveUp(double y){
        if(!(getAbove().size() >= 1) || y > 0)
            this.y+=y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setX(int x){
        this.x = x;
    }
    public abstract void move();
    public ArrayList<Block> getNear(){
        ArrayList<Block> near = new ArrayList<>();
        panel.getBlocks().stream().filter(Objects::nonNull).forEach(comp -> {
            if(comp.getType() != '0' || comp.getImage() != null) {
                double xcheck = (comp).getLocation().getX();
                double ycheck = (comp).getLocation().getY();
                double fallcheck;
                if(0.98 * Math.pow(fall, 2) > 128) fallcheck = 0.98 * Math.pow(fall, 2);
                else fallcheck = 128;
                boolean x1 = x - 128 < xcheck && x + 128 > xcheck;
                boolean y1 = y - fallcheck < ycheck && y + fallcheck > ycheck;
                if (x1 && y1) near.add(comp);
            }
        });
        return near;
    }
    public void setImage(String dir){
        ImageIcon img = new ImageIcon(dir);
        img.setImage(img.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        this.img = img.getImage();
    }
    public ArrayList<Block> getBelow(){
        ArrayList<Block> near = getNear();
        ArrayList<Block> below = new ArrayList<>();
        for(Block b: near){
            double cc = 5;
            if(0.98 * Math.pow(fall, 2) > 2.5) cc = 0.98 * Math.pow(fall, 2);
            if(y + height - b.getY() >= -cc && b.getY() > y ){
                int tempx = x + width/2;
                if((tempx + width/3 >= b.getX()) && (tempx - width/3 <= b.getX() + 48))
                    below.add(b);
            }
        }
        return below;
    }
    public ArrayList<Block> getAbove(){
        ArrayList<Block> near = getNear();
        ArrayList<Block> above = new ArrayList<>();
        for(Block b: near){
            if(y  < b.getY() + 50 && b.getY() < y ){
                int tempx = x + width/2;
                if((tempx + width/3 >= b.getX()) && (tempx - width/3 <= b.getX() + 48))
                    above.add(b);
            }
        }
        return above;
    }
    public boolean[] check(boolean up, boolean right, boolean left){
        ArrayList<Block> near = getNear();
        boolean[] checks = new boolean[3];
        checks[0] = up;
        checks[1] = right;
        checks[2] = left;
        if(up){
            for(Block b: near){
                double ytemp = b.getLocation().getY() + 48;
                double xtemp = b.getLocation().getX();
                boolean xch = x + width >= xtemp && x <=xtemp + 48;
                if(y >= ytemp && y <= ytemp  && xch){
                    checks[0] = false;
                }
            }
        }
        if(right){
            for(Block b: near){
                double ytemp = b.getLocation().getY();
                double xtemp = b.getLocation().getX();
                boolean ych = y + height >  ytemp && y < ytemp + 48;
                if(x+ width >= xtemp && x + 10<= xtemp + 38 && ych){
                    checks[1] = false;
                }
            }

        }if(left){
            for(Block b: near){
                double xtemp = b.getLocation().getX() + 48;
                double ytemp = b.getLocation().getY();
                boolean ych = y + height > ytemp && y < ytemp + 48;
                if(x <= xtemp && x >= xtemp - 38 && ych){
                    checks[2] = false;
                }
            }
        }
        return checks;
    }
    public boolean jump(double amt){
            if (counter < 10) {
                moveUp(-amt * 3);
            } else if (counter < 20 && counter > 10)
                moveUp(-amt * 2);
            else if (counter < 30 && counter > 20)
                moveUp(-amt);
            else if (counter < 50 && counter > 40)
                if(getBelow().size() >= 1){
                    counter = 80;
                } else
                    moveUp(amt);
            else if (counter < 60 && counter > 50)
                if(getBelow().size() >= 1){
                    counter = 80;
                } else
                    moveUp(amt * 2);
            else if (counter < 70 && counter > 60)
                if(getBelow().size() >= 1){
                    counter = 80;
                } else
                    moveUp(amt * 3);
            else if (counter > 70) {
                counter = 0;
                return false;
            }
            counter++;
            return true;
        }

}
