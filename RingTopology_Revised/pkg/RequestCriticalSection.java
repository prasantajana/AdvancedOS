package pkg;


import java.util.Queue;

public class RequestCriticalSection extends RingTopology{
    private Process process;
    public RequestCriticalSection(){
        process = processes.get((int)(Math.random()*processes.size()));
        System.out.println("Process "+process.id+" wants to enter Critical Section at time "+System.nanoTime());
    }

    public void run(){
         if(process == token && !cs){
            criticalSection();
        }
        else{
            System.out.println("Process "+process.id+" send token Request to its neighbour hope "+process.hope.id+"("+process.id+")");
            requestToken(process.hope);
        }
    }

    public void requestToken(Process hope){
        if(hope == token){
            token.request_q.add(process);
            if(!cs){
                token.request_q.poll();
                System.out.println("Process "+hope.id+" send token to its neighbour hope "+hope.hope.id+"("+process.id+")");
                Queue<Process> queue = hope.request_q;
                hope.request_q.clear();
                sendToken(hope.hope,queue);
            }
            else{
                token.request_q.add(process);
            }
        }
        else{
            System.out.println("Process "+hope.id+" send token Request to its neighbour hope "+hope.hope.id+"("+process.id+")");
            requestToken(hope.hope);
        }
    }

    public void sendToken(Process hope,Queue<Process> rQueue){
        token = hope;
        hope.request_q = rQueue;
        if(hope == process && !cs){
            criticalSection();
        }
        else{
            System.out.println("Process "+hope.id+" send token to its neighbour hope "+hope.hope.id+"("+process.id+")");
            sendToken(hope.hope, hope.request_q);
        }
    }

    public void criticalSection(){
        cs = true;
        System.out.println("Process "+process.id+" enter in Critical Section......");
        releaseCriticalSection();
    }

    public void releaseCriticalSection(){
        cs = false;
        System.out.println("Process "+process.id+" release Critical Section......");
        if(!process.request_q.isEmpty()){
            Process temp = process;
            process = process.request_q.poll();
            sendToken(temp.hope , temp.request_q);
        }
    }
}
