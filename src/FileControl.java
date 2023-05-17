import javax.tools.Tool;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileControl {
    private static File f;
    private static FileWriter write;
    private static ArrayList<String> file = new ArrayList<>();
    public static void createFile(){
        f = new File("World.txt");
        try{
            if(f.createNewFile())
                System.out.println("File Created");
            else{
                System.out.println("File already exists");
            }
        } catch(Exception e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    public static int getSize(){
        return file.size();
    }

    public static void initializeWriter(){
        try{
            write = new FileWriter(f.getPath());
            //write.flush();
        } catch(Exception e){
            System.out.println("Writing Error");
            e.printStackTrace();
        }
    }
    public static ArrayList<String> getFile(){
        return file;
    }
    public static void write(String blockCode, String i){
        edit(i, blockCode);
        initializeWriter();
        try{
            for(String s: file){
                write.append(s).append("\n");
            }
            write.flush();
        } catch(Exception e){
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        //scanFile();
    }

    public static String scanFile(String s){
        for(int ii = 0; ii < file.size(); ii++){
            if(file.get(ii).contains(s + ":")) {
                return file.get(ii + 1);
            }
        }
        return "";
    }
    private static void edit(String i, String s){
        if(!file.contains(i + ":")){
            file.add(i + ":");
            file.add(s);
            return;
        }
        for(int ii = 0; ii < file.size(); ii++){
            if(file.get(ii).equals(i + ":")) {
                file.set(ii + 1, s);
                return;
            }
        }
    }
    public static ArrayList<Character> convert(String s){
        ArrayList<Character> temp = new ArrayList<>();
        for(int i = 0; i < s.length(); i++){
            try {
                temp.add(s.charAt(i));
            } catch(NumberFormatException e){
                e.printStackTrace();
            }
        }
        return temp;
    }
    public static void clear(){
        file.clear();
    }
    public static void setFile(ArrayList<String> file2){
        file = file2;
    }
}
