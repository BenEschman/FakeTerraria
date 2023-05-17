import javax.swing.*;
import java.awt.*;

public class Block extends Item {
    private final char id;
    private final double breakSpeed = 1;
    public Block(char id){
        super(true);
        this.id = id;
        createImage();
        setSize(48, 48);
    }
    public void createImage() {
        ImageIcon image = null;
        switch(id){
            case 'A' -> image = new ImageIcon("Images/World/Log.png");
            case 'E' -> image = new ImageIcon("Images/World/Dirt2.png");
            case 'C' -> image = new ImageIcon("Images/World/Grass.png");
            case 'B' -> image = new ImageIcon("Images/World/Leaves.png");
            case 'D' -> image = new ImageIcon("Images/World/Stone.png");
            case 'F' -> image = new ImageIcon("Images/World/Plank.png");
            case 'G' -> image = new ImageIcon("Images/World/Copper.png");
            case 'H' -> image = new ImageIcon("Images/World/Dragonshit.png");
            case 'I' -> image = new ImageIcon("Images/World/Iron.png");
            case 'J' -> image = new ImageIcon("Images/World/Diamond.png");
            case 'K' -> image = new ImageIcon("Images/World/Coal.png");
            case 'c' -> image = new ImageIcon("Images/CraftedBlocks/CraftingTable.png");
            case '0' -> setOpaque(false);

            }
        if(image != null) {
            image.setImage(image.getImage().getScaledInstance(48, 48, Image.SCALE_DEFAULT));
            setIcon(image);
        }
    }
    public char getType(){
        return id;
    }

    public ImageIcon getImage(){
        switch(id) {
            case 'A' -> {
                return new ImageIcon("Images/World/Log.png");
            }
            case 'E' -> {
                return new ImageIcon("Images/World/Dirt2.png");
            }
            case 'C' -> {
                return new ImageIcon("Images/World/Grass.png");
            }
            case 'B' -> {
                return new ImageIcon("Images/World/Leaves.png");
            }
            case 'D' -> {
                return new ImageIcon("Images/World/Stone.png");
            }
            case 'F' -> {
                return new ImageIcon("Images/World/Plank.png");
            }
            case 'G' -> {
                return new ImageIcon("Images/World/Copper.png");
            }
            case 'H' -> {
                return new ImageIcon("Images/World/Dragonshit.png");
            }
            case 'I' -> {
                return new ImageIcon("Images/World/Iron.png");
            }
            case 'J' -> {
                return new ImageIcon("Images/World/Diamond.png");
            }
            case 'K' -> {
                return new ImageIcon("Images/World/Coal.png");
            }
            case 'c' -> {
                return new ImageIcon("Images/CraftedBlocks/CraftingTable.png");
            }
        }
        return null;
    }

}
