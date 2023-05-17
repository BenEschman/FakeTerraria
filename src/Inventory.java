import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Inventory extends GUI implements KeyListener, MouseListener {
    private Item[] crafting = new Item[5];
    public Slot[] blockOrder = new Slot[40];
    public Inventory(Dimension d){
        setSize(new Dimension((int)(d.getWidth() * 0.5),(int)(d.getHeight() * 0.8)));
        setBounds((int)(d.getWidth()*0.25), (int)(d.getHeight() * 0.1), getWidth(),getHeight());
        addKeyListener(this);
        addMouseListener(this);
        setBlockOrder();
    }
    private void setBlockOrder(){
        for(int i = 0; i < blockOrder.length; i++){
            blockOrder[i] = new Slot('\u0000', 0);
        }
    }
    public Item[] getCrafting(){
        return crafting;
    }
    public Slot[] getBlockOrder(){
        return blockOrder;
    }
    public void setData(){
        this.blockOrder = Main.getData().getBlockOrder();
        this.crafting = Main.getData().getCrafting();
    }
    protected void paintComponent(Graphics g){
        paintEdges(g);
    }
    private void paintEdges(Graphics g){
        g.setColor(new Color(225, 219, 219, 255));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(117, 115, 115, 255));
        g.fillRect(0, 0, getWidth(), 10);
        g.fillRect(0, getHeight()-10, getWidth(), 10);
        g.fillRect(0, 10, 10, getHeight()-20);
        g.fillRect(getWidth()-10, 10, 10, getHeight()-20);
        paintLinesHorz(g);
    }
    private void paintLinesHorz(Graphics g){
        drawHorzLines(g, getHeight(), 0.4, 1, 5, 0, getWidth(), 10);
        paintLinesVert(g);
    }
    private void paintLinesVert(Graphics g){
        drawVertLines(g, getWidth(), 0.125,1, 7, (int)(getHeight() * 0.4), getHeight(), 10);
        paintCrafting(g);
    }
    private void paintCrafting(Graphics g){
        g.fillRect((int) (getWidth() * 0.4 - 10), (int) (getHeight() * 0.1), 10, (int) (getHeight() * 0.2));
        g.fillRect((int) (getWidth() * 0.6 - 10), (int) (getHeight() * 0.1), 10, (int) (getHeight() * 0.2));
        g.fillRect((int) (getWidth() * 0.4 ), (int) (getHeight() * 0.1), (int) (getWidth() * 0.2), 10);
        g.fillRect((int) (getWidth() * 0.4), (int) (getHeight() * 0.3 - 10), (int) (getWidth() * 0.2), 10);
        g.fillRect((int) (getWidth() * 0.4), (int) (getHeight() * 0.2 - 5), (int) (getWidth() * 0.2), 10);
        g.fillRect((int) (getWidth() * 0.5 - 10), (int) (getHeight() * 0.1), 10, (int) (getHeight() * 0.2));
        g.fillRect((int) (getWidth() * 0.65), (int) (getHeight() * 0.2 - 10), (int) (getWidth() * 0.1), 20);
        Polygon poly = new Polygon();
        poly.addPoint((int) (getWidth() * 0.75), (int) (getHeight() * 0.15));
        poly.addPoint((int) (getWidth() * 0.75), (int) (getHeight() * 0.25));
        poly.addPoint((int) (getWidth() * 0.8), (int) (getHeight() * 0.2));
        g.fillPolygon(poly);
        g.fillRect((int) (getWidth() * 0.85 - 10), (int) (getHeight() * 0.15), 10, (int) (getHeight() * 0.1));
        g.fillRect((int) (getWidth() * 0.95 - 10), (int) (getHeight() * 0.15), 10, (int) (getHeight() * 0.1));
        g.fillRect((int) (getWidth() * 0.85 ), (int) (getHeight() * 0.15), (int) (getWidth() * 0.1), 10);
        g.fillRect((int) (getWidth() * 0.85), (int) (getHeight() * 0.25 - 10), (int) (getWidth() * 0.1), 10);
        drawCharacter(g);
        if(!checkCrafted()) crafting[4] = null;
    }
    private boolean checkCrafted(){
        for(char[] chars: Crafting.RECIPIES4){
            char[] temp1 = new char[4];
            for(int i = 0; i < crafting.length - 1; i++){
                if(crafting[i] != null) temp1[i] = crafting[i].getType();
                else temp1[i] = '\u0000';
            }
            char[] temp2 = new char[4];
            System.arraycopy(chars, 0, temp2, 0, temp2.length);
            if(chars.length > 7) {
                Arrays.sort(temp2);
                Arrays.sort(temp1);
            }
            if (Arrays.equals(temp1, temp2)) {
                if(chars[chars.length-1] == 'B')
                    crafting[4] = new Block(chars[4]);
                else
                    crafting[4] = new Tool(chars[4], 3);
                return true;
            }
        }
        return false;
    }
    private void drawCharacter(Graphics g){
        g.fillRect((int) (getWidth() * 0.1 - 10), (int) (getHeight() * 0.05), 10, (int) (getHeight() * 0.3) + 10);
        g.fillRect((int) (getWidth() * 0.3 - 10), (int) (getHeight() * 0.05), 10, (int) (getHeight() * 0.3));
        g.fillRect((int) (getWidth() * 0.1 ), (int) (getHeight() * 0.05), (int) (getWidth() * 0.2), 10);
        g.fillRect((int) (getWidth() * 0.1 ), (int) (getHeight() * 0.35) - 1, (int) (getWidth() * 0.2), 10);
        Image img2 = Main.getWorldGen().getPlayer().getImage();
        ImageIcon img = new ImageIcon();
        img.setImage(img2.getScaledInstance((int) (getWidth() * 0.2), (int) (getHeight() * 0.25), Image.SCALE_DEFAULT));
        g.drawImage(img.getImage(), (int) (getWidth() * 0.1), (int)(getHeight() * 0.1), this);
        drawItems(g, (int) (getWidth() * 0.125));
    }
    private void drawItems(Graphics g, int x){
        int count = 0;
        int count2 = 1;
        for(Slot i: blockOrder){
            if(count < 8) {
                Item b;
                if(new Block(i.getType()).getImage() != null)
                    b = new Block(i.getType());
                else
                    b = new Tool(i.getType(), 3);
                if(b.getImage() != null) {
                    g.setColor(Color.BLACK);
                    g.drawImage(b.getImage().getImage(), count * x + x / 2 - b.getImage().getIconWidth() / 2, (int) (getHeight() * 0.94 - b.getImage().getIconHeight() / 2), this);
                    g.drawString("" + i.getAmt(), (count + 1) * (x) - x / 4 - 10, (int) (getHeight() *0.88 + 20));
                }
            } else if(count < 16){
                Item b;
                if(new Block(i.getType()).getImage() != null)
                    b = new Block(i.getType());
                else
                    b = new Tool(i.getType(), 3);
                if(b.getImage() != null) {
                    g.setColor(Color.BLACK);
                    g.drawImage(b.getImage().getImage(), (count - 8) * x + x / 2 - b.getImage().getIconWidth() / 2, (int) (getHeight() * 0.46 - b.getImage().getIconHeight() / 2), this);
                    g.drawString("" + i.getAmt(), (count - 7) * (x) - x / 4 - 10, (int) (getHeight() * 0.40 + 20));
                }
            } else if(count < 24) {
                Item b;
                if(new Block(i.getType()).getImage() != null)
                    b = new Block(i.getType());
                else
                    b = new Tool(i.getType(), 3);
                if (b.getImage() != null) {
                    g.setColor(Color.BLACK);
                    g.drawImage(b.getImage().getImage(), (count - 16) * x + x / 2 - b.getImage().getIconWidth() / 2, (int) (getHeight() * 0.58 - b.getImage().getIconHeight() / 2), this);
                    g.drawString("" + i.getAmt(), (count - 15) * (x) - x / 4 - 10, (int) (getHeight() * 0.52 + 20));
                }
            }else if(count < 32) {
                Item b;
                if(new Block(i.getType()).getImage() != null)
                    b = new Block(i.getType());
                else
                    b = new Tool(i.getType(), 3);
                if (b.getImage() != null) {
                    g.setColor(Color.BLACK);
                    g.drawImage(b.getImage().getImage(), (count - 24) * x + x / 2 - b.getImage().getIconWidth() / 2, (int) (getHeight() * 0.7 - b.getImage().getIconHeight() / 2), this);
                    g.drawString("" + i.getAmt(), (count - 23) * (x) - x / 4 - 10, (int) (getHeight() * 0.64 + 20));
                }
            }else if(count < 40) {
                Item b;
                if(new Block(i.getType()).getImage() != null)
                    b = new Block(i.getType());
                else
                    b = new Tool(i.getType(), 3);
                if (b.getImage() != null) {
                    g.setColor(Color.BLACK);
                    g.drawImage(b.getImage().getImage(), (count - 32) * x + x / 2 - b.getImage().getIconWidth() / 2, (int) (getHeight() * 0.82 - b.getImage().getIconHeight() / 2), this);
                    g.drawString("" + i.getAmt(), (count - 31) * (x) - x / 4 - 10, (int) (getHeight() * 0.76 + 20));
                }
            }
            count++;
        }
        for(Item b: crafting){
            if(b != null)
                if(count2 == 1) g.drawImage(b.getImage().getImage(), (int) (getWidth() * 0.45 - b.getImage().getIconWidth() / 2), (int) (getHeight() * 0.15 - b.getImage().getIconHeight() / 2), this);
                else if(count2 == 2) g.drawImage(b.getImage().getImage(), (int) (getWidth() * 0.55 - b.getImage().getIconWidth() / 2), (int) (getHeight() * 0.15 - b.getImage().getIconHeight() / 2), this);
                else if(count2 == 3) g.drawImage(b.getImage().getImage(), (int) (getWidth() * 0.45 - b.getImage().getIconWidth() / 2), (int) (getHeight() * 0.25 - b.getImage().getIconHeight() / 2), this);
                else if(count2 == 4) g.drawImage(b.getImage().getImage(), (int) (getWidth() * 0.55 - b.getImage().getIconWidth() / 2), (int) (getHeight() * 0.25 - b.getImage().getIconHeight() / 2), this);
                else if(count2 == 5) g.drawImage(b.getImage().getImage(), (int) (getWidth() * 0.9 - b.getImage().getIconWidth() / 2), (int) (getHeight() * 0.2 - b.getImage().getIconHeight() / 2), this);
            count2++;
        }
    }
    private void getClick(int x, int y){
        int add = 0;
        double per = ((double) y/getHeight());
        if(per < 0.4)  {
            inCraft(x, y);
            return;
        }
        else if(per > 0.4 && per < 0.52) add = 8;
        else if(per >= 0.52 && per < 0.64) add = 16;
        else if(per >= 0.64 && per < 0.76) add = 24;
        else if(per >= 0.76 && per < 0.88) add = 32;
        int xadd = (int) ((double) x/getWidth() * 8);
        if(blockOrder[xadd + add].getType() != '\u0000') {
            Item b;
            if(new Block(blockOrder[xadd + add].getType()).getImage() != null)
                 b = new Block(blockOrder[xadd + add].getType());
            else
                 b = new Tool(blockOrder[xadd + add].getType(), 3);
            int bo = blockOrder[xadd + add].getAmt();
            boolean stack = false;
            if(Main.over.getBlock() != null) {
                if(Main.over.getBlock().getType() == blockOrder[xadd+add].getType()){
                    blockOrder[xadd + add].changeAmt(Main.over.getAmt());
                    Main.over.changeAmt(-Main.over.getAmt());
                    stack = true;
                } else{
                    blockOrder[xadd + add].changeType(Main.over.getBlock().getType());
                    blockOrder[xadd + add].setAmt(Main.over.getAmt());
                }
            } else{
                blockOrder[xadd + add].changeType('\u0000');
                blockOrder[xadd + add].setAmt(0);
            }
            if(!stack)
                Main.over.setImage(b, bo);
            return;
        }
        if(Main.over.getBlock() != null) {
            blockOrder[xadd + add].changeType(Main.over.getBlock().getType());
            blockOrder[xadd + add].setAmt(Main.over.getAmt());
        }
        Main.over.setImage(null, 0);
    }
    private Block releaseBlock(int x, int y){
        int add = 0;
        double per = ((double) y/getHeight());
        if(per < 0.4)  inCraft(x, y);
        else if(per > 0.4 && per < 0.52) add = 8;
        else if(per >= 0.52 && per < 0.64) add = 16;
        else if(per >= 0.64 && per < 0.76) add = 24;
        else if(per >= 0.76 && per < 0.88) add = 32;
        int xadd = (int) ((double) x/getWidth() * 8);
        if(blockOrder[xadd + add].getType() != '\u0000') return null;
        return null;
    }
    public void inCraft(int x, int y){
        double pery = ((double) y/getHeight());
        double perx = ((double) x/getWidth());
        boolean ycheck = pery >= 0.1 && pery <= 0.3;
        boolean xcheck = perx >= 0.4 && perx <= 0.6;
        if(ycheck && xcheck)
            if(pery > 0.2)
                if(perx >= 0.5){
                    if(Main.over.getBlock() == null || crafting[3] == null) {
                        Item b = crafting[3];
                        crafting[3] = Main.over.getBlock();
                        if(Main.over.getBlock() == null)
                            Main.over.setImage(b, 1);
                        else Main.over.changeAmt(-1);
                    }
                } else{
                    if(Main.over.getBlock() == null || crafting[2] == null) {
                        Item b = crafting[2];
                        crafting[2] = Main.over.getBlock();
                        if(Main.over.getBlock() == null)
                            Main.over.setImage(b, 1);
                        else Main.over.changeAmt(-1);
                    }
                }
            else{
                if(perx >= 0.5){
                    if(Main.over.getBlock() == null || crafting[1] == null) {
                        Item b = crafting[1];
                        crafting[1] = Main.over.getBlock();
                        if(Main.over.getBlock() == null)
                            Main.over.setImage(b, 1);
                        else Main.over.changeAmt(-1);
                    }
                } else{
                    if(Main.over.getBlock() == null || crafting[0] == null) {
                        Item b = crafting[0];
                        crafting[0] = Main.over.getBlock();
                        if(Main.over.getBlock() == null)
                            Main.over.setImage(b, 1);
                        else Main.over.changeAmt(-1);
                    }
                }
            }
        else if(pery >= 0.15 && pery <= 0.25 && perx >= 0.85 && perx <= 0.95)
            if(Main.over.getBlock() == null && crafting[4] != null){
                Item b = crafting[4];
                Arrays.fill(crafting, null);
                Main.over.setImage(b, getRecipe(b));
            }
    }
    private int getRecipe(Item b){
        for(char[] rec: Crafting.RECIPIES4){
            if(rec[4] == b.getType()) return Integer.parseInt(rec[5] + "");
        }
        return 0;
    }
    public void addItem(Block b){
        if(b != null && b.getType() != '0'){
            boolean check = false;
            for (Slot slot : blockOrder) {
                if (slot.getType() == b.getType()) {
                    slot.changeAmt(1);
                    check = true;
                    break;
                }
            }
            if(!check){
                for(Slot slot: blockOrder){
                    if(slot.getType() == '\u0000'){
                        slot.changeType(b.getType());
                        slot.changeAmt(1);
                        break;
                    }
                }
            }
        }

        Main.getT().repaint();
    }
    public Block removeItem(Slot s, char c){
        s.changeAmt(-1);
        return new Block(c);
    }
    public Item getItem(int slot, char id){
        return removeItem(blockOrder[slot], id);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_E) Main.exitInv();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            getClick(e.getX(), e.getY());
        } else if(e.getButton() == MouseEvent.BUTTON2){

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
