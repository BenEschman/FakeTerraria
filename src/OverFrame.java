import javax.swing.*;
import java.awt.*;

public class OverFrame extends JPanel {
    private Item b;
    private Image img;
    private int amt;
    private boolean toggle = false;
    public OverFrame(Dimension d){
        setSize(d);
        setFocusable(false);
        setOpaque(false);
    }
    public void setImage(Item b, int amt){
        this.b = b;
        this.amt = amt;
        if(b == null || b.getImage() == null) img = null;
        else img = b.getImage().getImage();
    }
    public int getAmt(){
        return amt;
    }
    public void changeAmt(int change){
        amt += change;
        if(amt <= 0) setImage(null, 0);
    }
    public Image getImage(){
        return img;
    }
    public Item getBlock(){
        return b;
    }
    protected void paintComponent(Graphics g){
        if(img != null && getMousePosition() != null) {
            int x = (int) getMousePosition().getX() - img.getWidth(this)/2;
            int y = (int) getMousePosition().getY() - img.getHeight(this)/2;
            g.drawImage(img, x, y, this);
            if(toggle)
                g.drawString("" + amt, x + img.getWidth(this), y - img.getHeight(this)/2);
        }
        repaint();
    }
    public void Toggle(){
        toggle = !toggle;
    }
}
