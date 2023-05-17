import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainMenu extends JPanel {
    private final ImageIcon image;
    private static String worldName;
    public MainMenu(Dimension d){
        WorldFile.createFile();
        setSize(d);
        WorldFile.createFile();
        image = new ImageIcon("Images/World/MenuScreen.png");
        image.setImage(image.getImage().getScaledInstance(1488, 960, Image.SCALE_DEFAULT));
        //setLayout(new GridLayout(1, 1));
        JButton label = new JButton("Worlds");
        label.setPreferredSize(new Dimension(100, 50));
        label.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worlds();
                remove(label);
            }
        });
        add(label);
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image.getImage(), 0,0, this);
    }
    public void worlds(){
        ArrayList<String> worlds = WorldFile.getWorlds();
        removeAll();
        setLayout(new GridLayout(15, 1));
        for(String world: worlds){
            JButton l = new JButton(world);
            l.setMaximumSize(new Dimension(100, 50));
            add(l);
            l.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    FileControl.clear();
                    worldName = l.getText();
                    WorldFile.loadWorld(l.getText());
                    Main.world2(worldName);
                }
            });
        }
        for(int i = worlds.size(); i < 14; i ++){
            JLabel lable = new JLabel();
            lable.setOpaque(false);
            add(lable);
        }
        JTextField name = new JTextField(10);
        add(name);
        JButton b = new JButton("Create World");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileControl.clear();
                worldName = name.getText();
                if(WorldFile.getWorlds().contains(worldName)){
                    JOptionPane.showMessageDialog(null, "Name already used");
                } else
                    Main.world();
            }
        });
        add(b);
        revalidate();
    }
    public static String getWorldName(){
        return worldName;
    }

}
