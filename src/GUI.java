import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class GUI extends JPanel implements Serializable {

    public void drawHorzLines(Graphics g, int height, double start, double end, int number, int startx, int endx, int thickness){
        double interval = (end - start)/number;
        for(int i = 0; i < number; i ++){
            g.fillRect(startx, (int) (height * (start + interval * i) - thickness), endx, thickness);
        }
    }
    public void drawVertLines(Graphics g, int width, double start, double end, int number, int starty, int endy, int thickness){
        double interval = (end - start)/number;
        for(int i = 0; i < number; i ++){
            g.fillRect((int) (width * (start + interval * i) - thickness), (int) starty - thickness, thickness, endy);
        }
    }
    public void drawBox(Graphics g, int x, int y, int width, int height, int thickness, Color color){
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.white);
        g.fillRect(x + thickness , y + thickness, width - thickness * 2, height - thickness * 2);
        g.setColor(color);
    }

}
