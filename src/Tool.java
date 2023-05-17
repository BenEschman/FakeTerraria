import javax.swing.*;
import java.awt.*;

public class Tool extends Item{
    private char id;
    private double speed;
    private ImageIcon image;
    public Tool(char id, double speed){
        super(false);
        this.id = id;
        this.speed = speed;
        createImage();
    }
    private void createImage() {
        ImageIcon image = null;
        switch(id){
            case 'p' -> image = new ImageIcon("Images/Items/StonePickaxe.png");
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
            case 'p' -> {
                return new ImageIcon("Images/Items/StonePickaxe.png");
            }
        }
        return null;
    }
}
