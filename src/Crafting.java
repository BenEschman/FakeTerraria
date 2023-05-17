import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Locale;

public class Crafting extends GUI implements MouseListener {

    public final static ArrayList<char[]> RECIPIES4 = new ArrayList<>();
    public final static ArrayList<char[]> RECIPIES9 = new ArrayList<>();
    public final Slot[] table = new Slot[10];
    private final static char nul = '\u0000';
    public static void addRecipes(){
       add4Recipes();
       add9Recipes();
    }
    private static void add4Recipes(){
        RECIPIES4.add(new char[]{'F', 'F', 'F', 'F', 'c', '1', 'B'});
        RECIPIES4.add(new char[]{'A',nul, nul,nul, 'F', '4', '-', 'B'});
        RECIPIES4.add(new char[]{'F','D','D','F','p','1', 'I'});

    }
    private static void add9Recipes(){
        RECIPIES9.add(new char[]{'F','F',nul,'F','F',nul,'F','F',nul,'1', 'I'});
    }
    public Crafting(){
        Dimension d = Main.getWorldGen().getSize();
        setSize(new Dimension((int) (d.getHeight() * 0.8), (int) (d.getHeight() * 0.6)));
        setBounds((int)((d.getWidth() - d.getHeight()*0.8)/2), (int)(d.getHeight() * 0.2), getWidth(),getHeight());
        setTable();
        //no
    }
    private void setTable(){
        for(int i = 0; i < table.length; i++){
            table[i] = new Slot('\u0000', 0);
        }
    }
    protected void paintComponent(Graphics g){
       drawEdges(g);
    }
    private void drawEdges(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0, getWidth(), getHeight());
        g.setColor(new Color(138, 134, 134, 255));
        g.fillRect(0, 0, getWidth(), 10);
        g.fillRect(0, 0, 10, getHeight());
        g.fillRect(getWidth() - 10, 0, 10, getHeight());
        g.fillRect(0, getHeight() - 10, getWidth(), 10);
        drawCraft(g);
    }
    private void drawCraft(Graphics g){
        drawHorzLines(g, getHeight(), 0.2, 1, 4, (int) (getWidth() * 0.1), (int) (getWidth() * 0.375), 10);
        drawVertLines(g, getWidth(), 0.1, 0.6, 4, (int)( getHeight() * 0.2), (int) (getHeight() * 0.6) + 11, 10);
        drawBox(g, (int) (getWidth() * 0.7), (int) (getHeight() * 0.425), (int) (getWidth() * 0.15), (int) (getWidth() * 0.15), 10, new Color(138, 134, 134, 255));
    }
    private void drawArrow(Graphics g){

    }
    private Slot getClick(Point p){
        double x = p.getX();
        double y = p.getY();
        if(y >= getHeight() * 0.2 && y < getHeight() * (0.4)){
            if(x >= getWidth() * 0.1 && x < getWidth() * (0.225)){
                return table[0];
            }
            if(x >= getWidth() * (0.225) && x < getWidth() * (0.35)){
                return table[1];
            }
            if(x >= getWidth() * 0.35 && x < getWidth() * (0.475)){
                return table[2];
            }
        }
        if(y >= getHeight() * (0.4) && y < getHeight() * (0.6)){
            if(x >= getWidth() * 0.1 && x < getWidth() * (0.225)){
                return table[3];
            }
            if(x >= getWidth() * (0.225) && x < getWidth() * (0.35)){
                return table[4];
            }
            if(x >= getWidth() * 0.35 && x < getWidth() * (0.475)){
                return table[5];
            }
        }
        if(y >= getHeight() * (0.6) && y < getHeight() * (0.8)){
            if(x >= getWidth() * 0.1 && x < getWidth() * (0.225)){
                return table[6];
            }
            if(x >= getWidth() * (0.225) && x < getWidth() * (0.35)){
                return table[7];
            }
            if(x >= getWidth() * 0.35 && x < getWidth() * (0.475)){
                return table[8];
            }
        }
        return null;
    }
    private void setSlot(Slot s){
        if(Main.over.getBlock() != null) {
            s.changeType(Main.over.getBlock().getType());
            s.changeAmt(1);
            Main.over.changeAmt(-1);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(getClick(e.getPoint()) != null){
            setSlot(getClick(e.getPoint()));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
