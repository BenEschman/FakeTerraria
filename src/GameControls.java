import javax.swing.*;

public class GameControls {
    public static Block breakBlock(Block comp, int x, int y, PlayerGen p, WorldGen w, boolean dir){
        if(check(comp, p)) {
            if (comp.getType() != '0') {
                w.setBlock(new Block('0'), x, y);
            } else {
                Item b = Main.getWorldGen().getPlayer().getInv().getItem(Main.getT().getHighlight(), Main.getT().getItem());
                if(b != null && Main.getT().getItem() != '\u0000' && b.getPlacsable()) {
                    w.setBlock((Block)b, x, y);
                }
            }
            w.buildWorld(dir);
            return comp;
        }
        return null;
    }
    private static boolean check(JComponent comp, PlayerGen p){
        int x = p.getX();
        double y = p.getY();
        boolean xch = x - 192 < comp.getX() && x + 192 > comp.getX();
        boolean ych = y - 192 < comp.getY() && y + 192 > comp.getY();
        return xch && ych;
    }
}
