import java.util.*;

public class PCB {

	private Queue<Process> queue;
	private String state;
	
	public PCB() {
		state = "";
		queue = new LinkedList <Process>();
	}
	
	public String getState() {
		return this.state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public void add(Process p) {
		queue.add(p);
	}
	
	public void remove(Process p) {
		queue.remove(p);
	}
}
