import java.io.Serializable;
import java.util.ArrayList;

public final class Data implements Serializable {
    private final ArrayList<int[]> pigPos = new ArrayList<>();
    private final ArrayList<int[]> pigFrame = new ArrayList<>();
    private Slot[] blockOrder;
    private Item[] crafting;
    public void setData(ArrayList<Entity> entities, Slot[] blockOrder, Item[] crafting){
        pigPos.clear();
        pigFrame.clear();
        for(Entity e: entities){
            int[] temp = new int[2];
            temp[0] = e.getX();
            temp[1] = e.getY();
            pigPos.add(temp);
            pigFrame.add(((Pig)e).getFrame());
        }
        this.blockOrder = blockOrder;
        this.crafting = crafting;
    }
    public Slot[] getBlockOrder(){
        return blockOrder;
    }
    public Item[] getCrafting(){
        return crafting;
    }
    public ArrayList<int[]> getPigPos(){
        return pigPos;
    }
    public ArrayList<int[]> getPigFrame(){
        return pigFrame;
    }


}
