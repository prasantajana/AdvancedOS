package pkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RingTopology extends Thread{
    static public ArrayList<Process> processes = new ArrayList<>();
    static public Process token = null;
    static public boolean cs = false;
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("Processes.txt");
        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNext()){
                String p = sc.next();
                Process process = new Process(p);
                processes.add(process);
            }
        }

        for(int i = 0;i<processes.size();i++){
            if(i == (processes.size()-1)){
                processes.get(i).hope = processes.get(0);
            }
            else{
                processes.get(i).hope = processes.get(i+1);
            }
        }

        int index = (int)(Math.random()*processes.size());
        token = processes.get(index);
        System.out.println("Token Holder Process "+token.id);

        Thread t1 = new RequestCriticalSection();
        t1.start();
        Thread t2 = new RequestCriticalSection();
        t2.start();
        // Thread t3 = new RequestCriticalSection();
        // t3.start();

    }
}
