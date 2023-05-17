import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ToolBar extends Inventory {
    private int highlight = 0;
    private Inventory inv = Main.getWorldGen().getPlayer().getInv();

    public ToolBar(Dimension d){
        super(d);
        Dimension dnew = new Dimension((int)(d.getWidth() * 0.5), 96);
        this.setSize(dnew);
        setBackground(new Color(225, 219, 219, 157));
        setBounds((int)(d.getWidth()*0.25), 0, getWidth(), getHeight());

    }
    public int getHighlight(){
        return highlight;
    }
    @Override
    protected void paintComponent(Graphics g){
        paintEdges(g);
    }
    public char getItem(){
        if(inv.blockOrder[highlight].getType() != '\u0000')
            return inv.blockOrder[highlight].getType();
        return '0';
    }
    public void setHighlight(int i){
        highlight = i;
    }
    public void scrollUp(){
        if(highlight == 7)
            highlight = 0;
        else highlight++;
    }
    public void scrollDown(){
        if(highlight == 0)
            highlight = 7;
        else highlight--;
    }
    private void paintEdges(Graphics g){
        g.setColor(new Color(225, 219, 219, 157));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(117, 115, 115, 157));
        g.fillRect(0, 0, getWidth(), 10);
        g.fillRect(0, getHeight()-10, getWidth(), 10);
        g.fillRect(0, 10, 10, getHeight()-20);
        g.fillRect(getWidth()-10, 10, 10, getHeight()-20);
        slots(g);
    }
    private void slots(Graphics g){
        int x = getWidth()/8;
        for(int i = 1; i < 9; i++){
            g.fillRect(x * i, 10, 10, getHeight()-20);
        }
        highlight(g, x);
    }
    private void highlight(Graphics g, int x){
        g.setColor(new Color(236, 233, 27, 165));
        g.fillRect(highlight * x + 10, 10, x-10, getHeight() -20);
        drawItems(g, x);
    }
    private void drawItems(Graphics g, int x){
        int count = 0;
        for(Slot i: inv.blockOrder){
            if(i.getType() != '\u0000')
                if(count < 8) {
                    Item b;
                    if(new Block(i.getType()).getImage() != null)
                        b = new Block(i.getType());
                    else
                        b = new Tool(i.getType(), 3);
                    if(b.getImage() != null) {
                        g.setColor(Color.BLACK);
                        g.drawImage(b.getImage().getImage(), count * x + 5 + x / 2 - b.getImage().getIconWidth() / 2, 5 + getHeight() / 2 - b.getImage().getIconHeight() / 2, this);
                        g.drawString("" + i.getAmt(), (count + 1) * (x) - x / 4, 25);
                    }
                }
            count++;
        }
        Main.over.setImage(new Block(getItem()), 0);
    }
}
