package pkg;

public class RequestCriticalSection extends Raymond{

    Process p;
    public RequestCriticalSection(){

        int range = processes.size();
        p = processes.get((int) (Math.random()*range));

        /*for(Process p : processes){
            if(p.id.equalsIgnoreCase(s)){
                this.p = p;
            }
        }*/

        p.request_q.add(p);
    }

     public void run(){
        System.out.println("Process "+p.id+" wants to enter Critical Section.");
        if(!p.holder){
            requestToken(p);
        }
        else{
            criticalSection(p);
        }
    }

    public void requestToken(Process process){

        System.out.println("Process "+process.id+" send request for token to its parent process "+process.parentProcess.id);

        if(process.parentProcess.request_q.isEmpty()){            
            process.parentProcess.request_q.add(process);
            process = process.parentProcess;
            if(process.holder){
                sendToken(process);
            }
            else{
                requestToken(process);
            }
        }
        else{
            process.parentProcess.request_q.add(process);
        }
    }

    public void displyQueue(Process p){
        System.out.print("Request Queue of Process "+p.id+": ");
        for(Process process : p.request_q){
            System.out.print(process.id+" ");
        }
        System.out.println();
    }

    public void sendToken(Process process){
        if(process.request_q.peek()!=process){
            System.out.println("Process "+process.id+" send token to its child process "+process.request_q.peek().id);
            process.parentProcess = process.request_q.peek();
            process.holder = false;
            System.out.println("Process "+process.parentProcess.id+" now  parent of Process "+process.id);  
            sendToken(process.request_q.poll());
            if(!process.request_q.isEmpty()){
                requestToken(process);
            }   
        }
        else{
            criticalSection(process);
        }
    }

    public void criticalSection(Process process){
        int min =500;
        int max = 900;
        int range = (max-min);

        process.holder = true;
        process.parentProcess = null;
        System.out.println("Process "+process.id+" enter in critical section.");
        try{
            Thread.sleep((int)Math.random() * range + min);
        }
        catch(InterruptedException e){
          
        }
        releaseCriticalSection(process);
    }

    public void releaseCriticalSection(Process process)
    {
        System.out.println("Process "+process.id+" release  critical section.\t\t");
        process.request_q.poll();
        if(!process.request_q.isEmpty()){
            sendToken(process);
        }
    }
}

