import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WorldFile {
    private static File f;
    private static FileWriter write;
    private static ArrayList<String> file = new ArrayList<>();
    private static Scanner scan;
    private static FileOutputStream fos;
    private static ObjectOutputStream oos;
    private static ArrayList<ToolBar> toolBars = new ArrayList<>();
    public static void createFile(){
        f = new File("Worlds.txt");
        try{
            if(f.createNewFile())
                System.out.println("File Created");
        } catch(Exception e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public static void initializeWriter(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(f.getPath()));
            scan = new Scanner(reader);
            write = new FileWriter(f.getPath());
        } catch(Exception e){
            System.out.println("Writing Error");
            e.printStackTrace();
        }
    }
    public static void initializeScanner(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(f.getPath()));
            scan = new Scanner(reader);
        } catch(Exception e){
            System.out.println("Scanning Error");
            e.printStackTrace();
        }
    }
    public static void write(String worldName, ArrayList<String> array){
        edit(worldName, array);
        initializeWriter();
        try{
            for(String s: file){
                write.append(s).append("\n");
            }
            write.flush();
            write.close();
        } catch(Exception e){
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        //scanFile();
    }
    public static ArrayList<String> scan(String worldName){
        ArrayList<String> world = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f.getPath()));
            scan = new Scanner(reader);
        }catch(Exception e){
            e.printStackTrace();
        }
        while(scan.hasNext()){
            String line = scan.nextLine();
            if(line.contains(worldName)){
                line = scan.nextLine();
                if(line.trim().length() >1 )
                    world.add(line);
                while(!(line.contains("World")) && scan.hasNext()){
                    line = scan.nextLine();
                    if(line.trim().length() > 1 && !(line.contains("World")))
                        world.add(line);
                }
            }
        }
        return world;
    }
    public static void loadWorld(String worldName){
        //ArrayList<String> temp = scan(worldName);
        FileControl.setFile(scan(worldName));
        //FileControl.scan();
//        while(!temp.isEmpty()){
//            String one = temp.remove(0);
//            String two = temp.remove(0);
//            FileControl.write(two, one);
//        }
    }
    public static ArrayList<String> getWorlds() {
        initializeScanner();
        ArrayList<String> worlds = new ArrayList<>();
        try {
            while (scan.hasNext()) {
                String line = scan.nextLine();
                if (line.contains("World")) {
                    worlds.add(line.substring(7));
                }
            }
        } catch(Exception e){
            System.out.println("No Worlds");
            e.printStackTrace();
        }
        return worlds;
    }
    public static void initializeObjectWriter(){
        try{
            fos = new FileOutputStream("Inventories.txt");
            oos = new ObjectOutputStream(fos);
        } catch(Exception e){
            System.out.println("Writing Error");
            e.printStackTrace();
        }
    }
    public static void saveWorldFiles(String name, Data data){
        File f = new File(name + ".ser");
        try{
            FileOutputStream fileOut = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(data);
            out.close();
            fileOut.close();
            System.out.println("Successfully saved file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Data retrieveWorlds(String name){
        File f = new File(name + ".ser");
        try{
            FileInputStream fileIn = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Data d = (Data) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Successfully retrieved file");
            return d;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void readInv(){
        try {
            FileInputStream fis = new FileInputStream("Inventory.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ois.readObject();
        } catch(Exception e){
            System.out.println("Error reading objects");
        }
    }
    private static void addObj(ToolBar t){
        if(!toolBars.contains(t)) toolBars.add(t);
    }
    public static ArrayList<String> scanFile(){
        ArrayList<String> files = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f.getPath()));
            scan = new Scanner(reader);
        }catch(Exception e){
            e.printStackTrace();
        }
        while(scan.hasNext()){
            String line = scan.nextLine();
            files.add(line);
        }
        return files;
    }
    public static void edit(String worldName, ArrayList<String> array){
        ArrayList<String> temp = scanFile();
        if(temp.contains("World: "+ worldName)){
            for(int i = 0; i < temp.size(); i++){
                if(temp.get(i).contains(worldName)){
                    while(temp.size() > i + 1 && !temp.get(i+1).contains("World")){
                        temp.remove(i+1);
                    }
                    int j = i+1;
                    for(String s: array){
                        temp.add(j, s);
                        j++;
                    }
                }
            }
        } else{
            temp.add("World: " + worldName + "\n");
            temp.addAll(array);
        }
        file = temp;
    }

}
