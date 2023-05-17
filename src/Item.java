import javax.swing.*;

public abstract class Item extends JLabel {

    private boolean placeable;

    public Item(boolean placeable){
        this.placeable = placeable;
    }
    public boolean getPlacsable(){
        return placeable;
    }
    public abstract ImageIcon getImage();
    public abstract char getType();
}
