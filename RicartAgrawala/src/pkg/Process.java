package pkg;

import java.util.ArrayList;

public class Process{
	int processId;
	long timeStamp;
	boolean CS = false;
	boolean requstSend = false;
	ArrayList<Process> requested = new ArrayList<Process>();
	ArrayList<Boolean> granted = new ArrayList<Boolean>();
}
