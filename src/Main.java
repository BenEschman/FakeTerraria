import javax.swing.*;
import java.awt.*;

public class Main {
    private static Dimension d;
    private static Data data = new Data();
    private static Container pane;
    public static OverFrame over;
    private static WorldGen w;
    private static ToolBar t;
    public static void main(String[] args)  {
        Crafting.addRecipes();
        FileControl.createFile();
        JFrame frame = new JFrame();
        Dimension ogsize = Toolkit.getDefaultToolkit().getScreenSize();
        d = new Dimension(((int)ogsize.getWidth())/48 * 48, ((int)ogsize.getHeight())/48 * 48);
        frame.setSize(d);
        over = new OverFrame(d);
        pane = frame.getContentPane();
        menu();
        WorldFile.readInv();
        frame.setVisible(true);


    }
    public static Data getData(){
        return data;
    }
    public static void menu(){
        pane.removeAll();
        pane.add(new MainMenu(d));
        pane.revalidate();

    }
    public static void menu2(){
        String name = MainMenu.getWorldName();
        w.saveData();
        WorldFile.saveWorldFiles(name, data);
        pane.removeAll();
        pane.add(new MainMenu(d));
        pane.revalidate();

    }
    public static ToolBar getT(){
        return t;
    }
    public static WorldGen getWorldGen(){
        return w;
    }
    public static void world(){
        pane.removeAll();
        w = new WorldGen(d);
        JLayeredPane lp = new JLayeredPane();
        lp.add(w);
        lp.setLayer(w, 1);
        t = new ToolBar(d);
        lp.add(t);
        lp.setLayer(t, 2);
        lp.add(over);
        lp.setLayer(over, 3);
        //WorldFile.writeInv(t);

        pane.add(lp);
        w.requestFocusInWindow();
        pane.revalidate();
    }
    public static void world2(String name){
        pane.removeAll();
        w = new WorldGen(d);
        data = WorldFile.retrieveWorlds(name);
        w.retrievedata();
        JLayeredPane lp = new JLayeredPane();
        lp.add(w);
        lp.setLayer(w, 1);
        t = new ToolBar(d);
        lp.add(t);
        lp.setLayer(t, 2);
        lp.add(over);
        lp.setLayer(over, 3);

        pane.add(lp);
        w.requestFocusInWindow();
        pane.revalidate();
    }
    public static void openInv(){
        over.Toggle();
        pane.removeAll();
        JLayeredPane lp = new JLayeredPane();
        lp.add(w);
        lp.setLayer(w, 1);
        Inventory inv = w.getPlayer().getInv();
        lp.add(inv);
        lp.setLayer(inv, 2);
        lp.add(over);
        lp.setLayer(over, 3);
        pane.add(lp);
        inv.requestFocusInWindow();
        pane.revalidate();
        over.setImage(null, 0);
    }
    public static void exitInv(){
        over.Toggle();
        pane.removeAll();
        JLayeredPane lp = new JLayeredPane();
        lp.add(w);
        lp.setLayer(w, 1);
        lp.add(t);
        lp.setLayer(t, 2);
        lp.add(over);
        lp.setLayer(over, 3);
        pane.add(lp);
        w.requestFocusInWindow();
        pane.revalidate();
    }
    public static void openCraft(){
        over.Toggle();
        pane.removeAll();
        JLayeredPane lp = new JLayeredPane();
        lp.add(w);
        lp.setLayer(w, 1);
        Crafting craft = new Crafting();
        lp.add(craft);
        lp.setLayer(craft, 2);
        lp.add(over);
        lp.setLayer(over, 3);
        pane.add(lp);
        craft.requestFocusInWindow();
        pane.revalidate();
        over.setImage(null, 0);
    }
}