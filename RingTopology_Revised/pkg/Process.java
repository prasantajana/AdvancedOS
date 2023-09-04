package pkg;

import java.util.LinkedList;
import java.util.Queue;

class Process{
    String id;
    Process hope;
    Queue<Process> request_q = new LinkedList<>();

    public Process(String id){
        this.id = id;
    }
}