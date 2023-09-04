package pkg;

import java.util.ArrayList;

public class RicartAgrawala extends Thread{
	
	static public int no_of_process = 5;
	static public ArrayList<Process> processes= new ArrayList<Process>();

	public static void main(String[] args) {
		for(int i=0;i<no_of_process;i++) {
			Process p = new Process();
			p.processId = i;
			processes.add(p);
		}

		System.out.println();
		Thread T1 = new CriticalSection(processes.get(4));
		T1.start();
		Thread T2 = new CriticalSection(processes.get(2));
		T2.start();
		// Thread T3 = new CriticalSection(processes.get(1));
		// T3.start();
		// Thread T4 = new CriticalSection(processes.get(0));
		// T4.start();
	}
}
