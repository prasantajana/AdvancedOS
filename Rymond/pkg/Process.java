package pkg;


import java.util.LinkedList;
import java.util.Queue;

public class Process {
    String id = null;
    Process parentProcess;
    Queue<Process> request_q = new LinkedList<Process>();  
    boolean holder = false;
}
