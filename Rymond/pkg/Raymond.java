package pkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Raymond extends Thread{

    static ArrayList<Process> processes = new ArrayList<>();
    static ArrayList<Process> temp = new ArrayList<>();
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("Process.txt");
        boolean flag;
        int count = 0;
        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNextLine()){
                flag = true;
                String s = sc.next(); 
                Process p = new Process();
                temp.add(p);
                p.id = s;
                for(Process temp : processes){
                    if(temp.id.equalsIgnoreCase(p.id)){
                        flag = false;
                    }
                }
                if(flag){
                    processes.add(p);
                }

                count++;

                if(count==2){
                    count = 0;
                    Process cp = null;
                    Process pp = null;

                    for(Process tempp : processes){
                        if(tempp.id.equalsIgnoreCase(temp.get(0).id)){
                            pp = tempp;
                        }
                    }

                    
                    for(Process tempp : processes){
                        if(tempp.id.equalsIgnoreCase(temp.get(1).id)){
                            cp = tempp;
                        }
                    }
                    temp.clear();
                    cp.parentProcess = pp;
                }
            } 
        }

        for(Process p: processes){
            if(p.parentProcess == null){
                p.holder = true;
            }
        }



        Thread t1 = new RequestCriticalSection();
        Thread t2 = new RequestCriticalSection();
   
        t1.start();
        t2.start();

    }
}