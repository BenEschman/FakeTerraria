public class GenerationControl {

    public static Block[][] generateUnder(Block[] fr, int height){
        Block[][] dev = new Block[height][fr.length];
        dev[0] = fr;
        for(int i = 1 ; i < dev.length; i ++){
            for(int ii = 0; ii < dev[i].length; ii ++){
                dev[i][ii] = new Block('D');
            }
        }
        if(Math.random() > 0.4)
            for(int i = 0; i < (int)(Math.random() * 1.5) + 1; i++)
                dev = oreGen(dev, 'G', 5);
        for(int i = 0; i < (int)(Math.random() * 3) + 1; i++)
            dev = oreGen(dev, 'K', 10);
        if(Math.random() > 0.95)
            dev = oreGen(dev, 'H', 2);
        if(Math.random() > 0.6)
            for(int i = 0; i < (int)(Math.random() * 1.4) + 1; i++)
                dev = oreGen(dev, 'J', 4);
        for(int i = 0; i < (int)(Math.random() * 3.5) + 1; i++)
            dev = oreGen(dev, 'I', 6);

        return dev;
    }
    private static Block[][] oreGen(Block[][] before, char id, int amt){
        int left = (int)(Math.random() * (amt/2)) + amt/2;
        int right = (int)(Math.random() * (amt/2)) + amt/2;
        int x = (int)(Math.random() * before[0].length);
        int y = (int)(Math.random() * (before.length-1)) +1;
        while(left>0){
                before[y][x] = new Block(id);
                int xy = (int)(Math.random() * 3) + 1;
                if(xy == 1 && x >0)
                    x--;
                else if(xy==2 && y>1)
                    y--;
                else if(x >0 && y >1){
                    x--;
                    y--;
                }
                left--;
        }
        while(right>0){
            before[y][x] = new Block(id);
            int xy = (int)(Math.random() * 3) + 1;
            if(xy == 1 && x <before[0].length -1)
                x++;
            else if(xy==2 && y<before.length-1)
                y++;
            else if(x < before[0].length -1   && y < before.length - 1){
                x++;
                y++;
            }
            right--;
        }
        return before;
    }


}
