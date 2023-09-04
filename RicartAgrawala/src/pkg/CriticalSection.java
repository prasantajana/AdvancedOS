package pkg;

public class CriticalSection extends RicartAgrawala{

		Process p_ID;
		public  CriticalSection(Process p) {
			p_ID = p;
			p.timeStamp = System.nanoTime();
			p.granted.clear();
		}
		public void run() {
			for(int i=0;i<no_of_process;i++) {
				if(p_ID.processId!=i) {
					sendRequest(p_ID,processes.get(i));
				}
			}
			
			while(!allGranted(p_ID)) {
				
			}

			inCriticalSection(p_ID);
		}
		
		private void sendRequest(Process fromProcess , Process toProcess) {
			fromProcess.requstSend = true;

			System.out.println("Process "+fromProcess.processId+" send request to Process "+toProcess.processId+" with timestamp "+fromProcess.timeStamp);
			if(!(toProcess.CS || toProcess.requstSend)) {
				sendReplay(toProcess , fromProcess);
			}
			else {
				if(toProcess.CS){
					System.out.println("Process "+toProcess.processId+" is already in Critical Section.(Send replay to "+fromProcess.processId+")");
					fromProcess.granted.add(false);
					toProcess.requested.add(fromProcess);
				}
				else{
					if(toProcess.requstSend){
						if(fromProcess.timeStamp < toProcess.timeStamp){
							sendReplay(toProcess, fromProcess);
						}
						else{
							System.out.println("Process "+(fromProcess.timeStamp > toProcess.timeStamp ?toProcess.processId:fromProcess.processId)+" is already requested for  Critical Section.(Send replay to "+fromProcess.processId+")");
							fromProcess.granted.add(false);
							toProcess.requested.add(fromProcess);
						}
					}
				}
			}
		}
		
		private void sendReplay(Process fromProcess , Process toProcess) {
			toProcess.granted.add(true);
			fromProcess.requstSend = false;
			System.out.println("Process "+fromProcess.processId+" send grant to Process "+toProcess.processId);
		}
		
		
		private void inCriticalSection(Process p){
			p_ID.CS = true;
			System.out.println("Process "+p.processId+" enter in Critical Section.");
			try{
				Thread.sleep(9000);	
			}
			catch(Exception e){
				
			}	
			releaseCriticalSection(p);
		}
		
		private void releaseCriticalSection(Process p) {
			p.CS = false;
			System.out.println("Process "+p.processId+" release the Critical Section.");
			if(!p.requested.isEmpty()) {
				for(Process P: p.requested) {
					P.granted.remove(P.granted.indexOf(false));
					sendReplay(p,P);
				}
			}
			p.requested.clear();

		}

		private boolean allGranted(Process p) {
			Thread.getAllStackTraces();
			for(boolean flag : p.granted) {
				if(!flag) {
					return false;
				}
			}
			return true;
		}
}
