import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WorldGen extends JPanel{
    private Block[][] blocks;
    private final PlayerGen p;
    private final ArrayList<Block> world = new ArrayList<>();
    private final WorldGen w = this;
    private int screen = 0;
    private int screenUpDown = 0;
    private final ArrayList<Entity> animals = new ArrayList<>();
    private int[] max = new int[1];
    private final int blocksy;
    private final int blocksx;
    private final ArrayList<Block> clickedBlocks = new ArrayList<>();

    public WorldGen(Dimension d){
        FileControl.initializeWriter();
        setBackground(Color.CYAN);
        setSize(new Dimension((int)d.getWidth(), (int)(d.getHeight() -120)));
        blocksx = getWidth() / 48;
        blocksy = (int)(d.getHeight())/ 48;
        blocks = new Block[blocksy][blocksx];
        setLayout(new GridLayout(blocksy, blocksx));
        switchFrame(0);
        setFocusable(true);
        addKeyListener(new java.awt.event.KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
                    p.setImage("RunR");
                    p.right = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
                    p.setImage("RunL");
                    p.left = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
                    if(p.getBelow().size() >= 1)
                        p.up = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    WorldFile.write(MainMenu.getWorldName(),FileControl.getFile());
                    Main.menu2();
                }
                if(e.getKeyCode() == KeyEvent.VK_1) Main.getT().setHighlight(0);
                if(e.getKeyCode() == KeyEvent.VK_2) Main.getT().setHighlight(1);
                if(e.getKeyCode() == KeyEvent.VK_3) Main.getT().setHighlight(2);
                if(e.getKeyCode() == KeyEvent.VK_4) Main.getT().setHighlight(3);
                if(e.getKeyCode() == KeyEvent.VK_5) Main.getT().setHighlight(4);
                if(e.getKeyCode() == KeyEvent.VK_6) Main.getT().setHighlight(5);
                if(e.getKeyCode() == KeyEvent.VK_7) Main.getT().setHighlight(6);
                if(e.getKeyCode() == KeyEvent.VK_8) Main.getT().setHighlight(7);
                if(e.getKeyCode() == KeyEvent.VK_E) Main.openInv();
                if(e.getKeyCode() == KeyEvent.VK_C) Main.openCraft();
            }

            @Override
            public void keyReleased(KeyEvent e) {

                if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
                    p.setImage("Stand");
                    p.right = false;
                }
                if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
                    p.setImage("Stand");
                    p.left = false;
                }

            }
        });
        addMouseListener(new MouseListener() {
            @Override

            public void mousePressed(MouseEvent e) {
                System.out.println(e.getButton());
                if(e.getButton() == MouseEvent.BUTTON1) clickBlock(e);
                if(e.getButton() == MouseEvent.BUTTON3);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                clickedBlocks.clear();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                if(e.getUnitsToScroll() >0)
                    Main.getT().scrollUp();
                else
                    Main.getT().scrollDown();
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) clickBlock(e);
                if(e.getButton() == MouseEvent.BUTTON2);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        p = new PlayerGen(this);
    }
    private void clickBlock(MouseEvent e){
            Point point = e.getPoint();
            if(findComponentAt(point) instanceof WorldGen) return;
            Block comp = (Block) findComponentAt(point);
            if(clickedBlocks.contains(comp))return;
            boolean dir = screenUpDown != 0;
            for (int i = 0; i < blocks.length; i++) {
                for (int ii = 0; ii < blocks[i].length; ii++)
                    if (blocks[i][ii] == comp) {
                        p.addItem(GameControls.breakBlock(comp, ii, i, p, w, dir));
                        clickedBlocks.add(blocks[i][ii]);
                    }
            }
    }
    public void setMax(int y, int x, int start){
        if(start == -5){
            return;
        }
        int temp = 0;
        int temp2 = 0;
        if(start != 0 ) {
             temp = max[0];
             temp2 = max[max.length - 1];
        }
        max = new int[x];
        if(start == 0){
            max[0] = (int)(Math.random() * (y/4)) + (int)(3 * y/4) ;
        } else if(start == -1){
            max[0] = temp;
        } else if(start == 1){
            max[0] = temp2;
        }
        for(int i = 1; i < x; i++){
            if((Math.random() < 0.25 || (max[i-1] < y - y/3) ) &&  !(max[i-1] > y - 3)){
                max[i] = max[i-1] + (int)(Math.random() * 3);
            } else if(Math.random() < 0.25){
                max[i] = max[i-1] - (int)(Math.random() * 3);
            } else{
                max[i] = max[i-1];
            }
        }
        if(start == -1)
            for(int i = 0; i < max.length/2; i++) {
                int itemp = max[i];
                max[i] = max[max.length - 1 - i];
                max[max.length - 1 - i] =itemp;
            }
    }
    public void saveData(){
        Main.getData().setData(animals, p.getInv().getBlockOrder(), p.getInv().getCrafting());
    }
    public void retrievedata(){
        ArrayList<int[]> pigPos = Main.getData().getPigPos();
        ArrayList<int[]> pigFrame = Main.getData().getPigFrame();
        for(int i = 0; i < pigPos.size(); i++){
            animals.add(new Pig(this, pigPos.get(i)[0], pigPos.get(i)[1],pigFrame.get(i)));
        }
        p.getInv().setData();
    }
    public void generate(){
        world.clear();
        for(int i = 0; i < blocks.length; i ++){
            for(int ii = 0; ii < blocks[i].length; ii ++){
                if(i < max[ii]){
                    blocks[i][ii] = new Block('0');
                }
                else if(i == max[ii]){
                    blocks[i][ii] = new Block('C');
                } else if (i == max[ii] - 1)
                    //blocks[i][ii] = new Block((int)(Math.random() * 2) + 1);
                    blocks[i][ii] = new Block('D');
                else if(i > max[ii] - 1)
                    if((int)(Math.random() * 2) + 4 == 4)
                        blocks[i][ii] = new Block('E');
                    else blocks[i][ii] = new Block('D');
                else{
                    blocks[i][ii] = new Block('D');
                }
            }
        }
        for(int i = 0; i < 4; i++) {
            int temp = (int) (Math.random() * ((getWidth() / 64) - 2)) + 1;
            buildTree(temp);
        }

    }
    public PlayerGen getPlayer(){
        return p;
    }
    public void buildWorld(boolean dir){
        world.clear();
        StringBuilder temp = new StringBuilder();
        for (Block[] block : blocks) {
            world.addAll(Arrays.asList(block));
           for(Block b : block){
               temp.append(b.getType());
           }
        }
            FileControl.write(temp.toString(), screen + "-" + screenUpDown);

        revalidate();
        removeAll();
        for(JComponent p: world){
            add(p);
        }
    }
    public void switchFrame(int start){
        for(Entity e: animals){
            e.moveUp(-2);
        }
        String temp = fileReview(screenUpDown != 0);
        if(temp.equals("")){
            setMax(blocksy, blocksx, start);
            generation();
            buildWorld(screenUpDown != 0);
            if(Math.random() > 0.5 && screenUpDown == 0)
                animals.add(new Pig(this, blocks.length - 4));
        } else{
            ArrayList<Character> blockTypes = FileControl.convert(temp);
            int counter = 0;
            for(int i = 0; i < blocks.length; i++){
                for(int j = 0; j < blocks[i].length; j++){
                        blocks[i][j] = new Block(blockTypes.get(counter));
                        counter++;
                }
            }
            buildWorld(screenUpDown != 0);
        }

    }
    public void generation(){
        if(screenUpDown == 0)
            generate();
        else if(screenUpDown > 0)
            generateOver();
        else
            generateUnder();
    }
    public String fileReview(boolean dir){
            return FileControl.scanFile(screen + "-" + screenUpDown);
    }
    public void generateUnder(){
        blocks = GenerationControl.generateUnder(blocks[blocks.length-1], blocks.length);
    }
    public int getSpawnY(int x){
        buildWorld(false);
        for(int i = 0; i < blocks[0].length; i++){
            Block b = world.get(73);
            if(b.getX() > x && x <= b.getX() + 96) {
                System.out.println("Check");
                for (Block[] block : blocks) {
                    if (block[i].getType() != '0' || block[i].getType() != '\u0000')
                        return block[i].getY();
                }
            }
        }
        return 200;
    }
    public void generateOver(){
        char[] temp = new char[blocks[0].length];
        int counter = 0;
        for(Block block: blocks[0]){
            temp[counter] = block.getType();
            counter++;
        }
        for(int i = 0 ; i < blocks.length-1; i ++){
            for(int ii = 0; ii < blocks[i].length; ii ++){
                blocks[i][ii] = new Block('0');
            }
        }
        for(int ii = 0; ii < blocks[0].length; ii ++){
            blocks[blocks.length-1][ii] = new Block(temp[ii]);
        }
    }
    public void changeScreen(int x){
        screen+=x;
    }
    public void changeUnder(int x){
        screenUpDown += x;
    }
    public int[] getScreen(){
        return new int[]{screen, screenUpDown};
    }
    public void buildTree(int temp){
        blocks[max[temp]-1][temp] = new Block('A');
        blocks[max[temp]-2][temp] = new Block('A');
        blocks[max[temp]-3][temp] = new Block('A');
        blocks[max[temp]-4][temp] = new Block('A');
        blocks[max[temp]-4][temp+1] = new Block('B');
        blocks[max[temp]-4][temp-1] = new Block('B');
        blocks[max[temp]-5][temp] = new Block('B');
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        p.draw(g);
        p.move();
        for(Entity a: animals){
            if(((Pig)a).getInScreen())
                a.draw(g);
            a.move();
        }
        try{
            Thread.sleep(10);
        }catch(Exception e){
            System.out.println("Error");
        }
        repaint();
    }
    public ArrayList<Block> getBlocks(){
        return world;
    }
    public void setBlock(Block comp, int x, int y){
        blocks[y][x] = comp;
    }
}
